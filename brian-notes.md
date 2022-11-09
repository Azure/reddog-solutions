## Demo Install & Setup

There are 2 ways to use this demo. You can deploy backing Azure services and run the Java microservices locally. This allows for local development and debugging. 

Additionally, you can deploy everything to Azure and the microservices will be fully deployed to Azure Spring Apps.

Instructions for both are below.

### Local Deployment/Debugging

This deployment will require a bash shell of your choice. It will not work on Azure Cloud Shell.

* Pre-requisites

    ```bash
    # Ensure Java is setup (JDK-17). https://learn.microsoft.com/en-us/java/openjdk/overview

    # Maven install. https://maven.apache.org/download.cgi

    # Parallel tools
    brew install parallel
    ```

* Azure resources

    ```bash
    ./start.sh
    ```

* Run services locally

    ```bash
    # Setup local variables
    source ./outputs/var-reddog-spring-22226.sh

    # Start each service
    mvn clean package

    java -jar ./order-service/target/*.jar

    ```


### Full Azure Deployment

This deployment 