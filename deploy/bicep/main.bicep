// Params
param uniqueServiceName string
param redisName string = 'redis${uniqueServiceName}'
param cosmosAccountName string = uniqueServiceName
param cosmosDatabaseName string = 'reddog'
param cosmosCollectionName string = 'reddogmakeline'
param storageAccountName string = replace(uniqueServiceName, '-', '')
param blobContainerName string = 'receipts'
param eventHubNamespaceName string = 'eh${uniqueServiceName}'
param mysqlservername string = 'sql${uniqueServiceName}'
param dbName string = 'reddog'
param adminLogin string = 'reddog'
@secure()
param adminPassword string

// Top Level Resources

module cosmos 'modules/cosmos.bicep' = {
  name: '${deployment().name}--cosmos'
  params: {
    cosmosAccountName: cosmosAccountName
    cosmosDatabaseName: cosmosDatabaseName
    cosmosCollectionName: cosmosCollectionName
    location: resourceGroup().location
  }
}

module redis 'modules/redis.bicep' = {
  name: '${deployment().name}--redis'
  params: {
    redisName: redisName
    location: resourceGroup().location
  }
}

module storage 'modules/storage.bicep' = {
  name: '${deployment().name}--storage'
  params: {
    storageAccountName: storageAccountName
    blobContainerName: blobContainerName
    location: resourceGroup().location
  }
}

module mySql 'modules/mysql.bicep' = {
  name: '${deployment().name}--mysql'
  params: {
    servername: mysqlservername
    adminLogin: adminLogin
    adminPassword: adminPassword
    location: resourceGroup().location
    dbName: dbName
  }
}

module eventHub 'modules/eventhub.bicep' = {
  name: '${deployment().name}--eventhub'
  params: {
    eventHubNamespaceName: eventHubNamespaceName
    eventHubName: 'reddog'
    location: resourceGroup().location
  }
}

// Outputs
output cosmosUri string = cosmos.outputs.cosmosUri
output cosmosAccountName string = cosmos.outputs.cosmosAccountName
output storageAccountName string = storage.outputs.storageAccountName
output storageAccountKey string = storage.outputs.accessKey
output redisHost string = redis.outputs.redisHost
output redisSslPort int = redis.outputs.redisSslPort
output redisPassword string = redis.outputs.redisPassword
output mySqlFQDN string = mySql.outputs.mySqlFQDN
