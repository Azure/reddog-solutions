param servername string
param location string
param skuname string = 'Standard_D2ds_v4'
param tier string = 'GeneralPurpose'
param dbName string
param adminLogin string
@secure()
param adminPassword string

resource mySqlServer 'Microsoft.DBforMySQL/flexibleServers@2021-12-01-preview' = {
  name: servername
  location: location
  
 sku: {
    name: skuname
    tier: tier
 }

 properties: {
   createMode: 'Default'
   administratorLoginPassword: adminPassword
   administratorLogin: adminLogin
   version: '8.0.21'
 }
}

resource mySqlServerFirewallRules 'Microsoft.DBforMySQL/flexibleServers/firewallRules@2021-12-01-preview' = {
  parent: mySqlServer
  name: 'AllowAzureIPs'
  properties: {
    startIpAddress: '0.0.0.0'
    endIpAddress: '255.255.255.255'
  }
}

resource mySqlServerDatabase 'Microsoft.DBforMySQL/flexibleServers/databases@2021-12-01-preview' = {  
  parent: mySqlServer
  name: dbName
  properties: {
    charset: 'utf8'
    collation: 'utf8_general_ci'
  }
}

output mySqlFQDN string = mySqlServer.properties.fullyQualifiedDomainName
