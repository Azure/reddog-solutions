// Params
param uniqueServiceName string
param nodeCount int = 5
param aksName string = 'aks${uniqueServiceName}'

module aks 'modules/aks.bicep' = {
  name: 'aks-deployment'
  params: {
    name: aksName
    nodeCount: nodeCount
  }
}

// Outputs
