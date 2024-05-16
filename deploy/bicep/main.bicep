// Params
param uniqueServiceName string
param keyVaultName string = 'kv${uniqueServiceName}'
param redisName string = 'redis${uniqueServiceName}'
param cosmosAccountName string = uniqueServiceName
param cosmosDatabaseName string = 'reddog'
param cosmosCollectionName string = 'reddogmakeline'
param storageAccountName string = replace(uniqueServiceName, '-', '')
param blobContainerName string = 'receipts'
param eventHubNamespaceName string = 'eh${uniqueServiceName}'
param serviceBusName string = 'sbus${uniqueServiceName}'
param mysqlservername string = 'sql${uniqueServiceName}'
param includeOpenAI string = 'false'
param openAIAccountName string = 'openai${uniqueServiceName}'
param dbName string = 'reddog'
param adminLogin string = 'reddog'
param deployLocation string = resourceGroup().location
@secure()
param adminPassword string

// Top Level Resources
module keyVault 'modules/keyvault.bicep' = {
  name: '${deployment().name}--keyvault'
  params: {
    keyVaultName: keyVaultName
    location: deployLocation
  }
}

module cosmos 'modules/cosmos.bicep' = {
  name: '${deployment().name}--cosmos'
  params: {
    cosmosAccountName: cosmosAccountName
    cosmosDatabaseName: cosmosDatabaseName
    cosmosCollectionName: cosmosCollectionName
    location: deployLocation
  }
}

module redis 'modules/redis.bicep' = {
  name: '${deployment().name}--redis'
  params: {
    redisName: redisName
    location: deployLocation
  }
}

module storage 'modules/storage.bicep' = {
  name: '${deployment().name}--storage'
  params: {
    storageAccountName: storageAccountName
    blobContainerName: blobContainerName
    location: deployLocation
  }
}

module mySql 'modules/mysql.bicep' = {
  name: '${deployment().name}--mysql'
  params: {
    servername: mysqlservername
    adminLogin: adminLogin
    adminPassword: adminPassword
    location: deployLocation
    dbName: dbName
  }
}

module eventHub 'modules/eventhub.bicep' = {
  name: '${deployment().name}--eventhub'
  params: {
    eventHubNamespaceName: eventHubNamespaceName
    eventHubName: 'reddog'
    location: deployLocation
  }
}

module serviceBus 'modules/servicebus.bicep' = {
  name: '${deployment().name}--servicebus'
  params: {
    serviceBusNamespaceName: serviceBusName
    location: deployLocation
  }
}

module openAI 'modules/openai.bicep' = if (includeOpenAI == 'true') {
  name: '${deployment().name}--openai'
  params: {
    openAIServiceName: openAIAccountName
    location: deployLocation
  }
}

// Outputs
output keyVaultName string = keyVaultName
output keyVaultUri string = keyVault.outputs.keyVaultUri
output cosmosUri string = cosmos.outputs.cosmosUri
output cosmosAccountName string = cosmos.outputs.cosmosAccountName
output storageAccountName string = storage.outputs.storageAccountName
output storageAccountKey string = storage.outputs.accessKey
output redisHost string = redis.outputs.redisHost
output redisPassword string = redis.outputs.redisPassword
output mySqlFQDN string = mySql.outputs.mySqlFQDN
output eventHubEndPoint string = eventHub.outputs.eventHubEndPoint
output eventHubNamespaceName string = eventHub.outputs.eventHubNamespaceName
output sbConnectionString string = serviceBus.outputs.rootConnectionString
output openAIName string = includeOpenAI == 'true' ? openAI.outputs.openAIName : ''
