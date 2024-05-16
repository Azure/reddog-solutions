param location string
param springCloudName string
param logAnalyticsName string
param appInsightsName string

resource logAnalyticsWorkspace 'Microsoft.OperationalInsights/workspaces@2020-08-01' = {
  name: logAnalyticsName
  location: location
  properties: {
    sku: {
      name: 'PerGB2018'
    }
    retentionInDays: 30
  }
}

resource appInsights 'Microsoft.Insights/components@2020-02-02-preview' = {
  name: appInsightsName
  location: location
  kind: 'web'
  properties: {
    Application_Type: 'web'
    Flow_Type: 'Bluefield'
    Request_Source: 'rest'
    WorkspaceResourceId: logAnalyticsWorkspace.id
  }
}

resource springCloudService 'Microsoft.AppPlatform/Spring@2022-09-01-preview' = {
  name: springCloudName
  location: location
  sku: {
    name: 'S0'
    tier: 'Standard'
  }
}

resource reddogOrderServiceApp 'Microsoft.AppPlatform/Spring/apps@2022-09-01-preview' = {
  name: 'order-service'
  parent: springCloudService
  properties: {
    public: true
  }
} 

resource reddogOrderServiceDeploy 'Microsoft.AppPlatform/Spring/apps/deployments@2022-09-01-preview' = {
  name: 'order-service'
  parent: reddogOrderServiceApp
  sku: {
    capacity: 1
    name: 'S0'
    tier: 'Standard'
  }  
  properties: { 
    deploymentSettings: {
      environmentVariables: {
        AZURECOSMOSDBURI: 'https://reddog29383briar5276.documents.azure.com:443/' 
      }
      resourceRequests: {
        cpu: '2'
        memory: '1Gi'
      }
    }
    source: {
      type: 'Source'
      artifactSelector: 'pom.xml'
      relativePath: './order-service'
      runtimeVersion: 'Java_17'
    }
  }
}  

resource springCloudMonitoringSettings 'Microsoft.AppPlatform/Spring/monitoringSettings@2020-07-01' = {
  name: '${springCloudService.name}/default' // The only supported value is 'default'
  properties: {
    traceEnabled: true
    appInsightsInstrumentationKey: appInsights.properties.InstrumentationKey
  }
}

resource springCloudDiagnostics 'microsoft.insights/diagnosticSettings@2017-05-01-preview' = {
  name: 'monitoring'
  scope: springCloudService
  properties: {
    workspaceId: logAnalyticsWorkspace.id
    logs: [
      {
        category: 'ApplicationConsole'
        enabled: true
        retentionPolicy: {
          days: 30
          enabled: false
        }
      }
    ]
  }
}

output workspaceId string = logAnalyticsWorkspace.id
output appInsightsInstrumentationKey string = appInsights.properties.InstrumentationKey
