param openAIServiceName string
param location string = resourceGroup().location
param sku string = 'S0'

resource openAIService 'Microsoft.CognitiveServices/accounts@2022-12-01' = {
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

resource openAIADAModel 'Microsoft.CognitiveServices/accounts/deployments@2022-12-01' = {
  name: 'generate_description'
  parent: openAIService
  properties: {
    model: {
      format: 'OpenAI'
      name: 'text-ada-001'
      version: '1'
    }
    scaleSettings: {
      scaleType: 'Standard'
    }
  }
}

resource openAIDavinciModel 'Microsoft.CognitiveServices/accounts/deployments@2022-12-01' = {
  name: 'text-davinci-002'
  parent: openAIService
  dependsOn: [
    openAIADAModel
  ]  
  properties: {
    model: {
      format: 'OpenAI'
      name: 'text-davinci-002'
      version: '1'
    }
    scaleSettings: {
      scaleType: 'Standard'
    }
  }
}

output openAIName string = openAIServiceName
