param location string
param eventHubSku string = 'Standard'
param eventHubNamespaceName string
param eventHubName string

resource eventHubNamespace 'Microsoft.EventHub/namespaces@2021-11-01' = {
  name: eventHubNamespaceName
  location: location
  sku: {
    name: eventHubSku
    tier: eventHubSku
    capacity: 1
  }
  properties: {
    isAutoInflateEnabled: false
    maximumThroughputUnits: 0
    kafkaEnabled: true
  }
}

resource eventHub 'Microsoft.EventHub/namespaces/eventhubs@2021-11-01' = {
  parent: eventHubNamespace
  name: eventHubName
  properties: {
    messageRetentionInDays: 7
    partitionCount: 3
  }
}

output eventHubEndPoint string = eventHubNamespace.properties.serviceBusEndpoint
output eventHubNamespaceName string = eventHubNamespaceName
