## Deploy Spring Apps (TESTING)

This section is in progress.

```bash
# deploy via Bicep 
export RG='spring-automation'
export LOCATION='eastus'
export SUFFIX=$RANDOM
export USERNAME=''
export UNIQUE_SERVICE_NAME=reddog$RANDOM$USERNAME$SUFFIX

az group create --name $RG --location $LOCATION

az deployment group create \
    --name spring-reddog-asa \
    --mode Incremental \
    --only-show-errors \
    --resource-group $RG \
    --template-file ./deploy/bicep/asa.bicep \
    --parameters uniqueServiceName=$UNIQUE_SERVICE_NAME 

```
