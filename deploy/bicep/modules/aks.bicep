param name string
//param adminUsername string
//param adminPublicKey string
param nodeCount int = 5
param vmSize string = 'Standard_D4_v3'
param logAnalyticsID string
param monitoringTool string

var logAnalyticsEnabled = monitoringTool == 'loganalytics'

var addonProfiles = {
  omsagent: {
    enabled: logAnalyticsEnabled
    config: {
      logAnalyticsWorkspaceResourceID: logAnalyticsID
    }
  }
}

resource aks 'Microsoft.ContainerService/managedClusters@2021-05-01' = {
  name: name
  location: resourceGroup().location  
  identity: {
    type: 'SystemAssigned'
  }
  properties: {
    dnsPrefix: name
    // linuxProfile: {
    //   adminUsername: adminUsername
    //   ssh: {
    //     publicKeys: [
    //       {
    //         keyData: adminPublicKey
    //       }
    //     ]
    //   }
    // }    
    enableRBAC: true
    addonProfiles: monitoringTool == 'loganalytics' ? addonProfiles : json('null')
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
