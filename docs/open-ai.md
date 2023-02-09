## OpenAI

### Manual Deployment Steps

1. Bicep

https://learn.microsoft.com/en-us/azure/cognitive-services/create-account-bicep?tabs=CLI

```bicep
param cognitiveServiceName string = 'briar-openai-testing'
param location string = resourceGroup().location
param sku string = 'S0'

resource cognitiveService 'Microsoft.CognitiveServices/accounts@2021-12-01' = {
  name: cognitiveServiceName
  location: location
  sku: {
    name: sku
  }
  kind: 'OpenAI'
  properties: {
    publicNetworkAccess: 'Enabled'
    apiProperties: {
      statisticsEnabled: false
    }
  }
}
```

```bash
export RG='briar-openai'
export LOCATION='eastus'
export OPENAI_NAME='reddog-openai'

az group create --name $RG --location $LOCATION

az deployment group create \
    --name reddog-openai \
    --mode Incremental \
    --only-show-errors \
    --resource-group $RG \
    --template-file ./deploy/bicep/modules/openai.bicep \
    --parameters cognitiveServiceName=$OPENAI_NAME

az deployment group show -g $RG -n reddog-openai -o json

# example values
export OPENAI_API_BASE=''
export OPENAI_API_KEY=''

export OPENAI_API_BASE=$(az cognitiveservices account show -n $OPENAI_NAME -g $RG -o json | jq -r .properties.endpoint)
echo $OPENAI_API_BASE

export OPENAI_API_KEY=$(az cognitiveservices account keys list -n $OPENAI_NAME -g $RG -o json | jq -r .key1)
echo $OPENAI_API_KEY


```    

2. CLI

```bash
export RG=''
export LOCATION=''
export OPENAI_NAME=''

az group create --name $RG --location $LOCATION

az cognitiveservices account create \
-n $OPENAI_NAME \
-g $RG \
-l $LOCATION \
--kind OpenAI \
--sku s0 \
--subscription ''

az cognitiveservices account show \
-n $OPENAI_NAME \
-g $RG -o json | jq -r .properties.endpoint

az cognitiveservices account keys list \
-n $OPENAI_NAME \
-g $RG -o json | jq -r .key1

az cognitiveservices account deployment create \
   -g $RG \
   -n $OPENAI_NAME \
   --deployment-name text-davinci-002 \
   --model-name text-davinci-002 \
   --model-version "1"  \
   --model-format OpenAI \
   --scale-settings-scale-type "Standard"

az cognitiveservices account deployment create \
   -g $RG \
   -n $OPENAI_NAME \
   --deployment-name generate_description \
   --model-name text-ada-001 \
   --model-version "1"  \
   --model-format OpenAI \
   --scale-settings-scale-type "Standard"




```