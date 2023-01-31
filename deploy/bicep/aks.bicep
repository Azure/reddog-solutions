// Params
param uniqueServiceName string
param nodeCount int = 5
param aksName string = 'aks${uniqueServiceName}'

module aks 'modules/aks.bicep' = {
  //name: 'reddog-aks-cluster'
  name: '${deployment().name}--aks'
  params: {
    name: aksName
    nodeCount: nodeCount
  }
}

// Outputs
