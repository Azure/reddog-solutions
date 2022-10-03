# create az backing services, mentioned in README pre-reqs
# set the following env variables after above step, and replace <> placeholders
export KAFKA_BOOTSTRAP_SERVERS=<event-hubs-domain>.servicebus.windows.net:9093
export KAFKA_SECURITY_PROTOCOL=SASL_SSL
export KAFKA_SASL_MECHANISM=PLAIN
export KAFKA_SASL_JAAS_CONFIG=org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="Endpoint=sb://<event-hubs-domain>.servicebus.windows.net/;SharedAccessKeyName=<shared-access-key-name>;SharedAccessKey=<shared-access-key>";
export MYSQL_URL=jdbc:mysql://<host>.mysql.database.azure.com/<db_name>
export MYSQL_USER=<mysql_user>
export MYSQL_PASSWORD=<mysql_password>
export AZURE_REDIS_HOST=<redis_host>.redis.cache.windows.net
export AZURE_REDIS_PORT=<port>
export AZURE_REDIS_ACCESS_KEY=<redis_access_key>
export AZURE_COSMOSDB_URI=https://<cosmos_domain>.documents.azure.com:443/
export AZURE_COSMOSDB_KEY=<cosmos_key>
export AZURE_COSMOSDB_SECONDARY_KEY=<secondary_key>
export AZURE_COSMOSDB_DATABASE_NAME=<db_name>
export AZURE_STORAGE_ACCOUNT_NAME=<storageaccountname>
export AZURE_STORAGE_ACCOUNT_KEY=<storage_account_key>
export AZURE_STORAGE_ENDPOINT=https://<domain>.blob.core.windows.net

# builds and runs the services in parallel - requires gnu-parallel
find . -name "pom.xml" | parallel nohup mvn spring-boot:run -f