source functions.sh

export RG=$1
export LOCATION=$2
export SUFFIX=$3
export USERNAME=$4
export ADMIN_PASSWORD=$5
export DEPLOY_TARGET=$6
export INCLUDE_OPENAI=$7
export UNIQUE_SERVICE_NAME=reddog$RANDOM$USERNAME$SUFFIX
export AKS_NAME=aks$UNIQUE_SERVICE_NAME

# show all params
echo '****************************************************'
echo 'Starting Red Dog Spring deployment'
echo ''
echo 'Parameters:'
echo 'RG: ' $RG
echo 'LOCATION: ' $LOCATION
echo 'UNIQUE NAME: ' $UNIQUE_SERVICE_NAME
echo 'DEPLOY_TARGET: ' $DEPLOY_TARGET
echo 'INCLUDE_OPENAI: ' $INCLUDE_OPENAI
echo '****************************************************'
echo ''

# check for Azure login
check_for_azure_login

# create RG
echo ''
echo "Creating Azure Resource Group"
az group create --name $RG --location $LOCATION -o table

# Bicep deployment
echo ''
echo '****************************************************'
echo 'Starting Bicep deployment of resources'
echo '****************************************************'

az deployment group create \
    --name reddog-backing-services \
    --mode Incremental \
    --only-show-errors \
    --resource-group $RG \
    --template-file .././deploy/bicep/main.bicep \
    --parameters uniqueServiceName=$UNIQUE_SERVICE_NAME \
    --parameters includeOpenAI=$INCLUDE_OPENAI \
    --parameters adminPassword=$ADMIN_PASSWORD -o table

# need error handling here

echo ''
echo 'Azure bicep deployment complete'

# Save deployment outputs
echo ''
echo '****************************************************'
echo "Collecting deployment outputs"
echo '****************************************************'  
az deployment group show -g $RG -n reddog-backing-services -o json --query properties.outputs > ".././outputs/$RG-bicep-outputs.json"

export KEY_VAULT_NAME=$(jq -r .keyVaultName.value .././outputs/$RG-bicep-outputs.json)
export KEY_VAULT_URI=$(jq -r .keyVaultUri.value .././outputs/$RG-bicep-outputs.json)
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
export SB_CONNECT_STRING=$(jq -r .sbConnectionString.value .././outputs/$RG-bicep-outputs.json)

if [ "$INCLUDE_OPENAI" = "true" ]
then
    export OPENAI_NAME=$(jq -r .openAIName.value .././outputs/$RG-bicep-outputs.json)
    export OPENAI_API_BASE=$(az cognitiveservices account show -n $OPENAI_NAME -g $RG -o json | jq -r .properties.endpoint)
    export OPENAI_API_KEY=$(az cognitiveservices account keys list -n $OPENAI_NAME -g $RG -o json | jq -r .key1)
else
    export OPENAI_NAME=''
    export OPENAI_API_BASE=''
    export OPENAI_API_KEY=''
fi

# Write variables to files
export VARIABLES_FILE=".././outputs/var-$RG.sh"
CONFIGMAP_FILE=".././outputs/config-map-$RG.yaml"

escape_jaas_config() {
    local input=$1
    input="${input//\"/\\\"}"
    input="${input//$/\\$}"
    echo "$input"
}

kafkasasljaasconfig_escape=$(escape_jaas_config "$EH_CONFIG")
KAFKASASLJAASCONFIG_KV="KAFKASASLJAASCONFIG=\"$kafkasasljaasconfig_escape\""

printf "export AZURECOSMOSDBURI='%s'\n" $COSMOS_URI > $VARIABLES_FILE
printf "export AZURECOSMOSDBKEY='%s'\n" $COSMOS_PRIMARY_RW_KEY >> $VARIABLES_FILE
printf "export AZURECOSMOSDBDATABASENAME='reddog' \n" >> $VARIABLES_FILE
echo "export $KAFKASASLJAASCONFIG_KV" >> $VARIABLES_FILE
printf "export KAFKABOOTSTRAPSERVERS='%s'\n" $EH_ENDPOINT >> $VARIABLES_FILE
printf "export KAFKASECURITYPROTOCOL='SASL_SSL'\n" >> $VARIABLES_FILE
printf "export KAFKASASLMECHANISM='PLAIN'\n" >> $VARIABLES_FILE
printf "export KAFKATOPICNAME='reddog'\n" >> $VARIABLES_FILE
printf "export KAFKATOPICGROUP='order-service'\n" >> $VARIABLES_FILE
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
printf "export SERVICEBUSCONNECTIONSTRING='%s'\n" $SB_CONNECT_STRING >> $VARIABLES_FILE
printf "export OPENAI_API_BASE='%s'\n" $OPENAI_API_BASE >> $VARIABLES_FILE
printf "export OPENAI_API_KEY='%s'\n" $OPENAI_API_KEY >> $VARIABLES_FILE
printf "export AZURE_KEY_VAULT_NAME='%s'\n" $KEY_VAULT_NAME >> $VARIABLES_FILE
printf "export AZURE_KEY_VAULT_ENDPOINT='%s'\n" $KEY_VAULT_URI >> $VARIABLES_FILE

printf "apiVersion: v1\n" > $CONFIGMAP_FILE
printf "kind: ConfigMap\n" >> $CONFIGMAP_FILE
printf "metadata:\n" >> $CONFIGMAP_FILE
printf "  name: reddog-env-vars\n" >> $CONFIGMAP_FILE
printf "  namespace: reddog\n" >> $CONFIGMAP_FILE
printf "data:\n" >> $CONFIGMAP_FILE
printf "  AZURECOSMOSDBURI: '%s'\n" $COSMOS_URI >> $CONFIGMAP_FILE
printf "  AZURECOSMOSDBKEY: '%s'\n" $COSMOS_PRIMARY_RW_KEY >> $CONFIGMAP_FILE
printf "  AZURECOSMOSDBDATABASENAME: 'reddog'\n" >> $CONFIGMAP_FILE
printf "  KAFKASASLJAASCONFIG: '${EH_CONFIG}'\n" >> $CONFIGMAP_FILE
printf "  KAFKABOOTSTRAPSERVERS: '%s'\n" $EH_ENDPOINT >> $CONFIGMAP_FILE
printf "  KAFKASECURITYPROTOCOL: 'SASL_SSL'\n" >> $CONFIGMAP_FILE
printf "  KAFKASASLMECHANISM: 'PLAIN'\n" >> $CONFIGMAP_FILE
printf "  KAFKATOPICNAME: 'reddog'\n" >> $CONFIGMAP_FILE
printf "  KAFKATOPICGROUP: 'order-service'\n" >> $CONFIGMAP_FILE
printf "  KAFKATOPICNAME: 'reddog'\n" >> $CONFIGMAP_FILE
printf "  MYSQLURL: 'jdbc:mysql://%s/reddog'\n" $SQL_FQDN >> $CONFIGMAP_FILE
printf "  MYSQLUSER: 'reddog'\n" >> $CONFIGMAP_FILE
printf "  MYSQLPASSWORD: '%s'\n" $ADMIN_PASSWORD >> $CONFIGMAP_FILE
printf "  AZUREREDISHOST: '%s'\n" $REDIS_HOST >> $CONFIGMAP_FILE
printf "  AZUREREDISPORT: '6380'\n" >> $CONFIGMAP_FILE
printf "  AZUREREDISACCESSKEY: '%s'\n" $REDIS_PWD >> $CONFIGMAP_FILE
printf "  AZURESTORAGEACCOUNTNAME: '%s'\n" $STORAGE_ACCOUNT >> $CONFIGMAP_FILE
printf "  AZURESTORAGEACCOUNTKEY: '%s'\n" $STORAGE_ACCOUNT_KEY >> $CONFIGMAP_FILE
printf "  AZURESTORAGEENDPOINT: 'https://%s.blob.core.windows.net'\n" $STORAGE_ACCOUNT >> $CONFIGMAP_FILE
printf "  SERVICEBUSCONNECTIONSTRING: '%s'\n" $SB_CONNECT_STRING >> $CONFIGMAP_FILE
printf "  ORDER_SVC_URL: 'http://order-service.reddog.svc.cluster.local:8702'\n" >> $CONFIGMAP_FILE
printf "  OPENAI_API_BASE: '%s'\n" $OPENAI_API_BASE >> $CONFIGMAP_FILE
printf "  OPENAI_API_KEY: '%s'\n" $OPENAI_API_KEY >> $CONFIGMAP_FILE
printf "  AZURE_KEY_VAULT_NAME: '%s'\n" $KEY_VAULT_NAME >> $CONFIGMAP_FILE
printf "  AZURE_KEY_VAULT_ENDPOINT: '%s'\n" $KEY_VAULT_URI >> $CONFIGMAP_FILE

echo ''
echo 'Local variables file created: ' $VARIABLES_FILE
echo 'ConfigMap YAML created: ' $CONFIGMAP_FILE
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
    echo '****************************************************'
    echo 'Deploying Azure Spring Apps'
    echo '****************************************************'

    deploy_azure_spring_apps # from functions.sh

    echo ''
    echo '****************************************************'
    echo 'Script complete'
    echo '****************************************************'
    exit 0    
elif [ "$DEPLOY_TARGET" = "aks" ]
then
    echo ''
    echo '****************************************************'
    echo 'Deploying AKS'
    echo '****************************************************'

    deploy_azure_kubernetes_service # from functions.sh

    # connect to AKS cluster and deploy namespace, configmap
    echo ''
    echo "connect to AKS cluster and deploy namespace, configmap"
    az aks get-credentials --resource-group $RG --name $AKS_NAME
    kubectl create ns reddog
    kubectl apply -f $CONFIGMAP_FILE

    # deploy Flux configuration with AKS extension
    echo ''
    echo "deploy Flux configuration with AKS extension"
    az k8s-configuration flux create \
        --resource-group $RG \
        --cluster-name $AKS_NAME \
        --cluster-type managedClusters \
        --scope cluster \
        --name reddog-java-apps \
        --namespace flux-system \
        --url https://github.com/appdevgbb/reddog-code-spring.git \
        --branch main \
        --kustomization name=kustomize path=./manifests/ prune=true \
         -o table

    echo ''
    echo '****************************************************'
    echo 'Script complete'
    echo '****************************************************'
    exit 0   
else
    echo 'ERROR: Value in config.json is not correct. Exiting'
    exit 0
fi


