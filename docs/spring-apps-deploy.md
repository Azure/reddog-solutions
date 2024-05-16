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

export RG=''
export SPRING_CLUSTER=''
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
    --env PORT='1025' VIRTUAL_CUSTOMERS_URL='' ORDERS_URL='' ACCOUNTING_URL='' OPENAI_URL=''

az spring app deploy \
    -s $SPRING_CLUSTER \
    -g $RG \
    -n $SERVICE_NAME \
    --container-image reddog/reddog-dashboard:v2 \
    --container-registry abcdef.azurecr.io \
    --registry-username abcdef \
    --registry-password <password>


```
