## This is not in use

```bash
# variables needed to run local. provided scripts will auto-create these
export KAFKA_BOOTSTRAP_SERVERS=replaceme.servicebus.windows.net:9093
export KAFKA_SECURITY_PROTOCOL=SASL_SSL
export KAFKA_SASL_MECHANISM=PLAIN
export KAFKA_SASL_JAAS_CONFIG='org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="Endpoint=sb://replaceme.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=replaceme";'
export MYSQL_URL=jdbc:mysql://replace
export MYSQL_USER=reddog
export MYSQL_PASSWORD='passowrd'
export AZURE_REDIS_HOST=
export AZURE_REDIS_PORT=6380
export AZURE_REDIS_ACCESS_KEY=
export AZURE_COSMOSDB_URI=https://replaceme.documents.azure.com:443/
export AZURE_COSMOSDB_KEY=
export AZURE_COSMOSDB_SECONDARY_KEY=
export AZURE_COSMOSDB_DATABASE_NAME=reddog
export AZURE_STORAGE_ACCOUNT_NAME=
export AZURE_STORAGE_ACCOUNT_KEY=
export AZURE_STORAGE_ENDPOINT=https://replaceme.blob.core.windows.net

# builds and runs the services in parallel - requires gnu-parallel
# find . -name "pom.xml" | parallel nohup mvn spring-boot:run -f
```