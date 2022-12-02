## Demo Install & Setup

There are 2 ways to use this demo. You can deploy backing Azure services and run the Java microservices locally. This allows for local development and debugging. 

Additionally, you can deploy everything to Azure and the microservices will be fully deployed to Azure Spring Apps or AKS.

Instructions for both are below.

### Initial Setup Steps

In this step, you will provision the Azure resources needed to support Red Dog Java. This includes: Event-Hubs, Azure MySQL, Azure Redis, CosmosDB, Azure OpenAI Service, Azure Service Bus, Azure Key Vault, Storage Account, etc.

This script will also deploy ASA, AKS, etc. if needed.

* Update/create the `config.json` file (in the `./scripts` directory)
    * The `deploytarget` setting determines if you will run local or use one of the Azure options
    * Your file should look like this: 

        ```json
        {
            "location":"eastus",
            "username": "shortname",
            "adminpassword": "replaceSecurePassword",
            "_comment1": "for deploy target one of: local, asa, aks",
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

* Setup local env variables 
    * Script creates an output with the variables needed. Source the file in your `./outputs` directory

        ```bash
        export DEPLOY_UNIQUE_SUFFIX=21904
        source ./outputs/var-reddog-spring-$DEPLOY_UNIQUE_SUFFIX.sh
        ```

* Run microservices 
    * Start with the order-service

        ```bash
        cd order-service
        mvn clean package
        java -jar ./target/*.jar
        ```

    * Repeat for each of the other services:
        * makeline-service
        * loyalty-service
        * accounting-service
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

### Azure Spring Apps Deployment

Follow the steps below to deploy Red Dog to your Azure Spring Apps instance deployed in the previous step. 

> Note: These manual steps will be replaced with the Bicep script going forward.

* Setup local env variables 
    * From the root directory of the repo 
    * Script creates an output with the variables needed. Source the file in your `./outputs` directory

        ```bash
        export DEPLOY_UNIQUE_SUFFIX=13912
        source ./outputs/var-reddog-spring-$DEPLOY_UNIQUE_SUFFIX.sh
        ```

* Deploy order-service:

    ```bash
    # Set variables as needed
    export RG=''
    export SPRING_CLUSTER=''
    export SERVICE_NAME='order-service'

    az spring app create \
        -n $SERVICE_NAME \
        -s $SPRING_CLUSTER \
        -g $RG \
        --runtime-version Java_17 \
        --assign-endpoint true \
        --cpu 2 \
        --memory 1Gi \
        --instance-count 1 \
        --env AZURECOSMOSDBURI=$AZURECOSMOSDBURI AZURECOSMOSDBKEY=$AZURECOSMOSDBKEY AZURECOSMOSDBDATABASENAME='reddog' KAFKASASLJAASCONFIG=$KAFKASASLJAASCONFIG KAFKABOOTSTRAPSERVERS=$KAFKABOOTSTRAPSERVERS KAFKASECURITYPROTOCOL='SASL_SSL' KAFKASASLMECHANISM='PLAIN' KAFKATOPICNAME='reddog' MYSQLURL=$MYSQLURL MYSQLUSER='reddog' MYSQLPASSWORD=$MYSQLPASSWORD AZUREREDISHOST=$AZUREREDISHOST AZUREREDISPORT='6380' AZUREREDISACCESSKEY=$AZUREREDISACCESSKEY AZURESTORAGEACCOUNTNAME=$AZURESTORAGEACCOUNTNAME AZURESTORAGEACCOUNTKEY=$AZURESTORAGEACCOUNTKEY AZURESTORAGEENDPOINT=$AZURESTORAGEENDPOINT KAFKATOPICGROUP=$SERVICE_NAME KAFKA_CONSUMER_GROUP_ID=$SERVICE_NAME KAFKA_COMPLETED_ORDERS_TOPIC='make-line-completed'

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

* Deploy remaining microservices using the commands above. For each service, set the variable below and run the create/deploy commands.

    ```bash
    export SERVICE_NAME='loyalty-service'
    export SERVICE_NAME='makeline-service'
    export SERVICE_NAME='accounting-service'
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

### Azure Kubernetes Service (AKS) Deployment

Coming soon...




