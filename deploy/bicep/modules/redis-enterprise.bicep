param redisName string
param location string

resource redis 'Microsoft.Cache/redisEnterprise@2022-01-01' = {
  name: redisName
  location: location
  sku: {
    capacity: 2
    name: 'Enterprise_E10'
  }
}

output redisHost string = redis.properties.hostName
//output redisSslPort int = redis.properties.sslPort
output redisPassword string = listKeys(redis.id, redis.apiVersion).primaryKey
