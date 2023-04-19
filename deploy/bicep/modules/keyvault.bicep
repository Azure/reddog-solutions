param keyVaultName string
param location string

resource keyVault 'Microsoft.KeyVault/vaults@2022-07-01' = {
  name: keyVaultName
  location: location
  properties: {
    accessPolicies: [ ]
    createMode: 'default'
    enablePurgeProtection: false
    enableSoftDelete: true
    networkAcls: {
      bypass: 'AzureServices'
      defaultAction: 'Allow'
    }
    provisioningState: 'Succeeded'
    publicNetworkAccess: 'enabled'
    sku: {
      family: 'A'
      name: 'standard'
    }
    softDeleteRetentionInDays: 90
    tenantId: tenantId: subscription().tenantId
  }
}

output keyVaultName string = keyVault.properties.name
output keyVaultUri string = keyVault.properties.vaultUri
