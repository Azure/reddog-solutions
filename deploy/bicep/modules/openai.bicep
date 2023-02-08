param openAIServiceName string
param location string = resourceGroup().location
param sku string = 'S0'

resource cognitiveService 'Microsoft.CognitiveServices/accounts@2022-12-01' = {
  name: openAIServiceName
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

output openAIName string = openAIServiceName
