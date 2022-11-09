## Demo Install & Setup

There are 2 ways to use this demo. You can deploy backing Azure services and run the Java microservices locally. This allows for local development and debugging. 

Additionally, you can deploy everything to Azure and the microservices will be fully deployed to Azure Spring Apps or AKS.

Instructions for both are below.

### Pre-Setup Steps

* Update/create the `config.json` file.
    * The `deploytarget` setting determines if you will run local or use one of the Azure options
    * Your file should look like this: 

        ```json
        {
            "location":"eastus",
            "username": "shortname",
            "adminpassword": "replaceSecurePassword",
            "_comment1": "for deploy target one of: local, azure-spring-apps, azure-aks",
            "deploytarget": "local"
        }
        ```

### Local Deployment/Debugging

This deployment will require a bash shell of your choice. It will not work on Azure Cloud Shell.

* Pre-requisites
    * Java (JDK-17). https://learn.microsoft.com/en-us/java/openjdk/overview
    * Maven. https://maven.apache.org/download.cgi
    * Bash
    * Yarn

* Deploy Azure resources
    * Ensure the `config.json` is in the root directory and it's updated
    * Run the start script: 

        ```bash
        ./start.sh
        ```

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



### Azure Kubernetes Service (AKS) Deployment






