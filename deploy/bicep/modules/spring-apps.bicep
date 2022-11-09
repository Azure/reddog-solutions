param location string
//https://learn.microsoft.com/en-us/azure/templates/microsoft.appplatform/spring/apps?pivots=deployment-language-bicep

resource symbolicname 'Microsoft.AppPlatform/Spring/apps@2022-09-01-preview' = {
  name: 'string'
  location: location
  identity: {
    principalId: 'string'
    tenantId: 'string'
    type: 'string'
    userAssignedIdentities: {}
  }
  properties: {
    addonConfigs: {}
    customPersistentDisks: [
      {
        customPersistentDiskProperties: {
          mountOptions: [
            'string'
          ]
          mountPath: 'string'
          readOnly: bool
          type: 'string'
          // For remaining properties, see CustomPersistentDiskProperties objects
        }
        storageId: 'string'
      }
    ]
    enableEndToEndTLS: bool
    httpsOnly: bool
    ingressSettings: {
      backendProtocol: 'string'
      clientAuth: {
        certificates: [
          'string'
        ]
      }
      readTimeoutInSeconds: int
      sendTimeoutInSeconds: int
      sessionAffinity: 'string'
      sessionCookieMaxAge: int
    }
    loadedCertificates: [
      {
        loadTrustStore: bool
        resourceId: 'string'
      }
    ]
    persistentDisk: {
      mountPath: 'string'
      sizeInGB: int
    }
    public: bool
    temporaryDisk: {
      mountPath: 'string'
      sizeInGB: int
    }
    vnetAddons: {
      publicEndpoint: bool
    }
  }
}
