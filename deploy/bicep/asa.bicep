// Params
param uniqueServiceName string
param springCloudName string = 'asa${uniqueServiceName}'
param logAnalyticsName string = 'la${uniqueServiceName}'
param appInsightsName string = 'appins${uniqueServiceName}'

module springApps 'modules/spring-apps.bicep' = {
  name: '${deployment().name}--asa'
  params: {
    springCloudName: springCloudName
    logAnalyticsName: logAnalyticsName
    appInsightsName: appInsightsName
    location: resourceGroup().location
  }
}

// Outputs
output logAnalyticsWorkspaceId string = springApps.outputs.workspaceId
output appInsightsInstrumentationKey string = springApps.outputs.appInsightsInstrumentationKey
