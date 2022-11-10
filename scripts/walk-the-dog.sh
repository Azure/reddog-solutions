source functions.sh

export RG=$1
export LOCATION=$2
export SUFFIX=$3
export ADMIN_PASSWORD=$5
export DEPLOY_TARGET=$6
export UNIQUE_SERVICE_NAME=reddog$RANDOM$USERNAME$SUFFIX

# show all params
echo '****************************************************'
echo 'Starting Red Dog Spring deployment'
echo ''
echo 'Parameters:'
echo 'LOCATION: ' $LOCATION
echo 'RG: ' $RG
echo 'UNIQUE NAME: ' $UNIQUE_SERVICE_NAME
echo 'DEPLOY_TARGET: ' $DEPLOY_TARGET
echo '****************************************************'
echo ''

# check for Azure login
check_for_azure_login

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
    --template-file .././deploy/bicep/main.bicep \
    --parameters uniqueServiceName=$UNIQUE_SERVICE_NAME \
    --parameters adminPassword=$ADMIN_PASSWORD

echo ''
echo 'Azure bicep deployment complete'

# Save deployment outputs
echo ''
echo "Collecting deployment outputs"
az deployment group show -g $RG -n spring-reddog -o json --query properties.outputs > ".././outputs/$RG-bicep-outputs.json"

export COSMOS_URI=$(jq -r .cosmosUri.value .././outputs/$RG-bicep-outputs.json)
export COSMOS_ACCOUNT=$(jq -r .cosmosAccountName.value .././outputs/$RG-bicep-outputs.json)
export COSMOS_PRIMARY_RW_KEY=$(az cosmosdb keys list -n $COSMOS_ACCOUNT  -g $RG -o json | jq -r '.primaryMasterKey')
export EH_NAME=$(jq -r .eventHubNamespaceName.value .././outputs/$RG-bicep-outputs.json)
export EH_ENDPOINT=$(jq -r .eventHubEndPoint.value .././outputs/$RG-bicep-outputs.json)
export EH_ENDPOINT=$EH_NAME'.servicebus.windows.net:9093'
export EH_CONNECT_STRING=$(az eventhubs namespace authorization-rule keys list --resource-group $RG --namespace-name $EH_NAME --name RootManageSharedAccessKey -o json | jq -r '.primaryConnectionString')
export EH_CONFIG='org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="'$EH_CONNECT_STRING'";'
export SQL_FQDN=$(jq -r .mySqlFQDN.value .././outputs/$RG-bicep-outputs.json)
export REDIS_HOST=$(jq -r .redisHost.value .././outputs/$RG-bicep-outputs.json)
export REDIS_PWD=$(jq -r .redisPassword.value .././outputs/$RG-bicep-outputs.json)
export STORAGE_ACCOUNT=$(jq -r .storageAccountName.value .././outputs/$RG-bicep-outputs.json)
export STORAGE_ACCOUNT_KEY=$(jq -r .storageAccountKey.value .././outputs/$RG-bicep-outputs.json)

# Write variables to file
VARIABLES_FILE=".././outputs/var-$RG.sh"

printf "export AZURECOSMOSDBURI='%s'\n" $COSMOS_URI >> $VARIABLES_FILE
printf "export AZURECOSMOSDBKEY='%s'\n" $COSMOS_PRIMARY_RW_KEY >> $VARIABLES_FILE
printf "export AZURECOSMOSDBDATABASENAME='reddog' \n" >> $VARIABLES_FILE
printf "export KAFKASASLJAASCONFIG='${EH_CONFIG}'\n" >> $VARIABLES_FILE
printf "export KAFKABOOTSTRAPSERVERS='%s'\n" $EH_ENDPOINT >> $VARIABLES_FILE
printf "export KAFKASECURITYPROTOCOL='SASL_SSL'\n" >> $VARIABLES_FILE
printf "export KAFKASASLMECHANISM='PLAIN'\n" >> $VARIABLES_FILE
printf "export KAFKATOPICNAME='reddog'\n" >> $VARIABLES_FILE
printf "export MYSQLURL='jdbc:mysql://%s/reddog'\n" $SQL_FQDN >> $VARIABLES_FILE
printf "export MYSQLUSER='reddog'\n" >> $VARIABLES_FILE
printf "export MYSQLPASSWORD='%s'\n" $ADMIN_PASSWORD >> $VARIABLES_FILE
printf "export AZUREREDISHOST='%s'\n" $REDIS_HOST >> $VARIABLES_FILE
printf "export AZUREREDISPORT='6380'\n" >> $VARIABLES_FILE
printf "export AZUREREDISACCESSKEY='%s'\n" $REDIS_PWD >> $VARIABLES_FILE
printf "export AZURESTORAGEACCOUNTNAME='%s'\n" $STORAGE_ACCOUNT >> $VARIABLES_FILE
printf "export AZURESTORAGEACCOUNTKEY='%s'\n" $STORAGE_ACCOUNT_KEY >> $VARIABLES_FILE
printf "export AZURESTORAGEENDPOINT='https://%s.blob.core.windows.net'\n" $STORAGE_ACCOUNT >> $VARIABLES_FILE

echo ''
echo 'Local variables file created: ' $VARIABLES_FILE
echo ''

echo '****************************************************'
echo 'Base Azure services deployed'
echo '****************************************************'  

# check deployment target and proceed
if [ "$DEPLOY_TARGET" = "local" ]
then
    echo ''
    echo '****************************************************'
    echo 'Script complete'
    echo 'Source the variables file to start local debugging'
    echo '****************************************************'
    exit 0
elif [ "$DEPLOY_TARGET" = "asa" ]
then
    echo ''
    echo 'Deploying Azure Spring Apps'

    deploy_azure_spring_apps
    #write_variables_to_keyvault
    #deploy_reddog_asa
    echo ''
    echo '****************************************************'
    echo 'Script complete'
    echo '****************************************************'
    exit 0    
elif [ "$DEPLOY_TARGET" = "aks" ]
then
    echo ''
    echo 'Deploying AKS'

    #deploy_azure_kubernetes_service
    echo ''
    echo '****************************************************'
    echo 'Script complete'
    echo '****************************************************'
    exit 0   
else
    echo 'ERROR: Value in config.json is not correct. Exiting'
    exit 0
fi


