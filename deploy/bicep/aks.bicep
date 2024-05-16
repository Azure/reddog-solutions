// Params
param nodeCount int = 5
param aksName string
param rgLocation string = resourceGroup().location

module aks 'modules/aks-cluster.bicep' = {
  name: '${deployment().name}--aks'
  params: {
    name: aksName
    nodeCount: nodeCount
    location: rgLocation
  }
}

// Outputs
output name string = aks.name
