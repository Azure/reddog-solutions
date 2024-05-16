param redisName string
param location string

resource redis 'Microsoft.Cache/redis@2020-12-01' = {
  name: redisName
  location: location
  properties: {
    sku: {
      name: 'Premium'
      family: 'P'
      capacity: 2
    }
    enableNonSslPort: false
  }
}

output redisHost string = redis.properties.hostName
output redisSslPort int = redis.properties.sslPort
output redisPassword string = listKeys(redis.id, redis.apiVersion).primaryKey
