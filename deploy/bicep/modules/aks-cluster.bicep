param name string
param nodeCount int = 5
param vmSize string = 'Standard_D4_v3'
param location string

resource aks 'Microsoft.ContainerService/managedClusters@2021-05-01' = {
  name: name
  location: location
  identity: {
    type: 'SystemAssigned'
  }
  properties: {
    dnsPrefix: name  
    enableRBAC: true
    agentPoolProfiles: [
      {
        name: 'agentpool1'
        count: nodeCount
        vmSize: vmSize
        osType: 'Linux'
        mode: 'System'
      }
    ]
  }
}

output name string = aks.name
