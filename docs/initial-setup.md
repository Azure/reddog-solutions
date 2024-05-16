## Demo Install & Setup

There are 2 ways to use this demo. You can deploy backing Azure services and run the Java microservices locally. This allows for local development and debugging. 

Additionally, you can deploy everything to Azure and the microservices will be fully deployed to Azure Spring Apps (Standard plan) or AKS.

Instructions for both are below.

### Prerequisites

* [Azure Account](https://azure.microsoft.com/en-us/free/)
* [az cli](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli)
* [jq](https://stedolan.github.io/jq/download/)
> Note: You may have to run `dos2unix` on the scripts if you are on a Mac/Linux machine, or `unix2dos` if you are on a Windows machine.

### Initial Setup Steps

In this step, you will provision the Azure resources needed to support Red Dog Java. This includes: Event-Hubs, Azure MySQL, Azure Redis, CosmosDB, Azure OpenAI Service, Azure Service Bus, Azure Key Vault, Storage Account, etc.

This script will also deploy ASA, AKS, ARO if needed.

* Update/create the `config.json` file (in the `./scripts` directory)
    * The `deploytarget` setting determines if you will run local or use one of the Azure options
    * Your file should look like this: 

        ```json
        {
            "location":"eastus",
            "username": "shortname",
            "adminpassword": "replaceSecurePassword",
            "_comment1": "for deploy target one of: local, asa, aks, aro",
            "deploytarget": "local"
        }
        ```
* Run intial deployment script

    ```bash
    cd scripts
    ./start.sh
    ```

* Jump to the section below that matches your deployment


### Local Deployment/Debugging

This deployment will require a bash shell of your choice. It will not work on Azure Cloud Shell.

* Pre-requisites
    * Java (JDK-17). https://learn.microsoft.com/en-us/java/openjdk/overview
        * `JAVA_HOME` set to ^ JDK location
    * Maven. https://maven.apache.org/download.cgi
    * Bash
    * Yarn

* Setup local Config Server and Registry Server
  - Start the application `local-eureka-server`
  - Start the application `local-config-service`
  - Start the application `local-gateway`

* Setup local env variables 
    * Script creates an output with the variables needed. Source the file in your `./outputs` directory

        ```bash
        export DEPLOY_UNIQUE_SUFFIX=reddog-xxx
        source ./outputs/var-$DEPLOY_UNIQUE_SUFFIX.sh
        ```

* Run microservices 
    * Start with the order-service

        ```bash
        cd order-service
        mvn clean package
        java -jar ./target/*.jar
        ```

    * Repeat for each of the other services:
        * accounting-service
        * makeline-service
        * loyalty-service
        * receipt-generation-service
        * virtual-worker
        * virtual-customer

* Start the UI web app
    * Clone the repo (submodule in the existing repo): https://github.com/appdevgbb/reddog-ui-nextjs
    * Set the .env.local file
    * Start via yarn

        ```bash
        yarn
        yarn dev
        ```

### Azure Kubernetes Service (AKS) Deployment

If you selected AKS as the deployment target, your terminal should have access to cluster and the apps were deployed via GitOps. 

Review the pods deployed in the cluster and validate the application UI is functional. 

> Note: The manual steps for deploying the application in AKS are noted here: [AKS Deployment Notes](/docs/aks-deploy.md)



### Azure Spring Apps Deployment

Follow the steps below to deploy Red Dog to your Azure Spring Apps instance deployed in the previous step. 

> Note: These manual steps will be replaced with the Bicep script going forward.

* Setup local env variables 
    * From the root directory of the repo 
    * Script creates an output with the variables needed. Source the file in your `./outputs` directory

    ```bash
    export DEPLOY_UNIQUE_SUFFIX=reddog-xxx
    source ./outputs/var-$DEPLOY_UNIQUE_SUFFIX.sh
    
    # Set variables as needed
    export RG=''
    export SPRING_CLUSTER=''
    ```

* Setup Config Server on Azure Spring Apps instance
  Set the default repository with below configuration:
    - URI: `https://github.com/Azure/reddog-solutions`
    - Label: `main`
    - Search Path: `config-server`

    ```bash
    az spring config-server git set --resource-group $RG -n $SPRING_CLUSTER --label "main" --search-paths "config-server" --uri https://github.com/Azure/reddog-solutions
    ```

* Store passwords to Key Vault

    ```bash
    # It's required to execute one time for Spring Apps service instance.
    CURRENT_USER_OBJECT_ID=$(az ad signed-in-user show --query id --output tsv)
    az keyvault set-policy --resource-group $RG --name $AZURE_KEY_VAULT_NAME --object-id $CURRENT_USER_OBJECT_ID --secret-permissions set list get
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "KAFKASASLJAASCONFIG" --value "$KAFKASASLJAASCONFIG"
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "AZURECOSMOSDBKEY" --value $AZURECOSMOSDBKEY
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "MYSQLUSER" --value $MYSQLUSER
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "MYSQLPASSWORD" --value $MYSQLPASSWORD
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "AZUREREDISACCESSKEY" --value $AZUREREDISACCESSKEY
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "AZURESTORAGEACCOUNTKEY" --value $AZURESTORAGEACCOUNTKEY
    az keyvault secret set --vault-name $AZURE_KEY_VAULT_NAME --name "SERVICEBUSCONNECTIONSTRING" --value $SERVICEBUSCONNECTIONSTRING
    ```

* Deploy gateway-service:

    ```bash
    # Create and deploy app, it's required to execute multiple times for each app instances.
    export SERVICE_NAME='gateway-service'
    az spring app create \
        -n $SERVICE_NAME \
        -s $SPRING_CLUSTER \
        -g $RG \
        --runtime-version Java_17 \
        --assign-endpoint true \
        --cpu 2 \
        --memory 2Gi \
        --instance-count 1 \
        --system-assigned true \
        --env AZURECOSMOSDBURI=$AZURECOSMOSDBURI AZURECOSMOSDBDATABASENAME='reddog' KAFKABOOTSTRAPSERVERS=$KAFKABOOTSTRAPSERVERS KAFKASECURITYPROTOCOL='SASL_SSL' KAFKASASLMECHANISM='PLAIN' KAFKATOPICNAME='reddog' MYSQLURL=$MYSQLURL AZUREREDISHOST=$AZUREREDISHOST AZUREREDISPORT=6380 AZURESTORAGEACCOUNTNAME=$AZURESTORAGEACCOUNTNAME AZURESTORAGEENDPOINT=$AZURESTORAGEENDPOINT KAFKATOPICGROUP=$SERVICE_NAME KAFKACONSUMERGROUPID=$SERVICE_NAME KAFKACOMPLETEDORDERSTOPIC='make-line-completed' AZURE_KEY_VAULT_ENDPOINT=$AZURE_KEY_VAULT_ENDPOINT
  
    # Set the access policy for this app
    APP_MANAGED_IDENTITY_OBJECT_ID=$(az spring app identity show \
        --resource-group $RG \
        --name $SERVICE_NAME \
        --service $SPRING_CLUSTER \
        --query principalId \
        --output tsv)
    az keyvault set-policy --resource-group $RG --name $AZURE_KEY_VAULT_NAME --object-id $APP_MANAGED_IDENTITY_OBJECT_ID --secret-permissions list get
  
    az spring app deploy \
        -s $SPRING_CLUSTER \
        -g $RG \
        --name $SERVICE_NAME \
        --source-path ./$SERVICE_NAME
  
    # Check apps and logs
    az spring app list -s $SPRING_CLUSTER -g $RG
    az spring app logs -n $SERVICE_NAME -s $SPRING_CLUSTER -g $RG --lines 1000
    az spring app logs -s $SPRING_CLUSTER -g $RG --lines 1000 -n order-service
    az spring app logs -n $SERVICE_NAME -s $SPRING_CLUSTER -g $RG -f # tail logs live

    # Delete if needed
    az spring app delete -n $SERVICE_NAME -s $SPRING_CLUSTER -g $RG
    ```

* Deploy remaining microservices using the commands above. For each service, set the variable below and run the create/set-policy/deploy commands.

    ```bash
    export SERVICE_NAME='order-service'
    export SERVICE_NAME='accounting-service'
    export SERVICE_NAME='makeline-service'
    export SERVICE_NAME='loyalty-service'
    export SERVICE_NAME='receipt-generation-service'
    export SERVICE_NAME='virtual-worker'
    export SERVICE_NAME='virtual-customers'

    # for virtual customers, add this ENV VAR
    ORDER_SVC_URL=''

    # for virtual worker, add this ENV VAR 
    MAKELINE_SVC_URL=''
    ```

* Deploy Dashboard UI (Custom Container) https://github.com/appdevgbb/reddog-ui-nextjs

    ```bash
    export SERVICE_NAME='dashboard'

    # set variables to URL for each service before running command
    az spring app create \
        -n $SERVICE_NAME \
        -s $SPRING_CLUSTER \
        -g $RG \
        --assign-endpoint true \
        --cpu 2 \
        --memory 1Gi \
        --instance-count 1 \
        --env PORT='1025' VIRTUAL_CUSTOMERS_URL='' ORDERS_URL='' ACCOUNTING_URL='' OPENAI_URL='' WORKER_URL=''

    az spring app deploy \
        -s $SPRING_CLUSTER \
        -g $RG \
        -n $SERVICE_NAME \
    --container-image chzbrgr71/reddog-dashboard:v1
    ```

* Deploy OpenAI Neural Network service
    > OpenAI Docs: https://learn.microsoft.com/en-us/azure/cognitive-services/create-account-bicep?tabs=CLI 

    ```bash
    export SERVICE_NAME='openai-svc'

    az spring app create \
        -n $SERVICE_NAME \
        -s $SPRING_CLUSTER \
        -g $RG \
        --assign-endpoint true \
        --cpu 2 \
        --memory 1Gi \
        --instance-count 1 \
        --env OPENAI_API_BASE='' OPENAI_API_KEY=''

    az spring app deploy \
        -s $SPRING_CLUSTER \
        -g $RG \
        -n $SERVICE_NAME \
    --container-image chzbrgr71/reddog-openai-svc:v1

    ```
