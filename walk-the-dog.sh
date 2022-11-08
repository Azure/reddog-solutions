export RG=$1
export LOCATION=$2
export SUFFIX=$3
export ADMIN_PASSWORD=$5
export UNIQUE_SERVICE_NAME=reddog$RANDOM$USERNAME$SUFFIX

# show all params
echo '****************************************************'
echo 'Starting Red Dog Spring deployment'
echo ''
echo 'Parameters:'
echo 'LOCATION: ' $LOCATION
echo 'RG: ' $RG
echo 'UNIQUE NAME: ' $UNIQUE_SERVICE_NAME
echo '****************************************************'
echo ''

# Check for Azure login
echo 'Checking to ensure logged into Azure CLI'
AZURE_LOGIN=0 
# run a command against Azure to check if we are logged in already.
az group list -o table
# save the return code from above. Anything different than 0 means we need to login
AZURE_LOGIN=$?

if [[ ${AZURE_LOGIN} -ne 0 ]]; then
# not logged in. Initiate login process
    az login --use-device-code
    export AZURE_LOGIN
fi

# create RG
echo ''
echo "Creating Azure Resource Group"
az group create --name $RG --location $LOCATION

# Bicep deployment
echo ''
echo '****************************************************'
echo 'Starting Bicep deployment of resources'
echo '****************************************************'

az deployment group create \
    --name spring-reddog \
    --mode Incremental \
    --only-show-errors \
    --resource-group $RG \
    --template-file ./deploy/bicep/main.bicep \
    --parameters uniqueServiceName=$UNIQUE_SERVICE_NAME \
    --parameters adminPassword=$ADMIN_PASSWORD

echo ''
echo '****************************************************'
echo 'Base infra deployed. Starting config/app deployment'
echo '****************************************************'    

# Save deployment outputs
az deployment group show -g $RG -n spring-reddog -o json --query properties.outputs > "./outputs/$RG-bicep-outputs.json"

export COSMOS_URI=$(jq -r .cosmosUri.value ./outputs/$RG-bicep-outputs.json)
export COSMOS_ACCOUNT=$(jq -r .cosmosAccountName.value ./outputs/$RG-bicep-outputs.json)
export COSMOS_PRIMARY_RW_KEY=$(az cosmosdb keys list -n $COSMOS_ACCOUNT  -g $RG -o json | jq -r '.primaryMasterKey')
export EH_NAME=$(jq -r .eventHubNamespaceName.value ./outputs/$RG-bicep-outputs.json)
export EH_ENDPOINT=$(jq -r .eventHubEndPoint.value ./outputs/$RG-bicep-outputs.json)
export EH_CONNECT_STRING=$(az eventhubs namespace authorization-rule keys list --resource-group $RG --namespace-name $EH_NAME --name RootManageSharedAccessKey -o json | jq -r '.primaryConnectionString')
export EH_CONFIG='org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="'$EH_CONNECT_STRING'";'
export SQL_FQDN=$(jq -r .mySqlFQDN.value ./outputs/$RG-bicep-outputs.json)
export REDIS_HOST=$(jq -r .redisHost.value ./outputs/$RG-bicep-outputs.json)
export REDIS_PWD=$(jq -r .redisPassword.value ./outputs/$RG-bicep-outputs.json)
export STORAGE_ACCOUNT=$(jq -r .storageAccountName.value ./outputs/$RG-bicep-outputs.json)
export STORAGE_ACCOUNT_KEY=$(jq -r .storageAccountKey.value ./outputs/$RG-bicep-outputs.json)

VARIABLES_FILE="./outputs/var-$RG.sh"

# Write variables to file
printf "export AZURE_COSMOSDB_URI='%s'\n" $COSMOS_URI >> $VARIABLES_FILE
printf "export AZURE_COSMOSDB_KEY='%s'\n" $COSMOS_PRIMARY_RW_KEY >> $VARIABLES_FILE
printf "export AZURE_COSMOSDB_DATABASE_NAME='reddog' \n" >> $VARIABLES_FILE
printf "export KAFKA_SASL_JAAS_CONFIG='${EH_CONFIG}'\n" >> $VARIABLES_FILE
printf "export KAFKA_BOOTSTRAP_SERVERS='%s'\n" $EH_ENDPOINT >> $VARIABLES_FILE
printf "export KAFKA_SECURITY_PROTOCOL='SASL_SSL'\n" >> $VARIABLES_FILE
printf "export KAFKA_SASL_MECHANISM='PLAIN'\n" >> $VARIABLES_FILE

printf "export MYSQL_URL='jdbc:mysql://%s/reddog'\n" $SQL_FQDN >> $VARIABLES_FILE
printf "export MYSQL_USER='reddog'\n" >> $VARIABLES_FILE
printf "export MYSQL_PASSWORD='%s'\n" $ADMIN_PASSWORD >> $VARIABLES_FILE

printf "export AZURE_REDIS_HOST='%s'\n" $REDIS_HOST >> $VARIABLES_FILE
printf "export AZURE_REDIS_PORT='6380'\n" >> $VARIABLES_FILE
printf "export AZURE_REDIS_ACCESS_KEY='%s'\n" $REDIS_PWD >> $VARIABLES_FILE

printf "export AZURE_STORAGE_ACCOUNT_NAME='%s'\n" $STORAGE_ACCOUNT >> $VARIABLES_FILE
printf "export AZURE_STORAGE_ACCOUNT_KEY='%s'\n" $STORAGE_ACCOUNT_KEY >> $VARIABLES_FILE
printf "export AZURE_STORAGE_ENDPOINT='https://%s.blob.core.windows.net'\n" $STORAGE_ACCOUNT >> $VARIABLES_FILE

