## Notes


```bash

# Ensure Java is setup (JDK-17). https://learn.microsoft.com/en-us/java/openjdk/overview

# Maven install. https://maven.apache.org/download.cgi

# Parallel tools
brew install parallel

# Deploy Azure resources
source ./outputs/var-reddog-spring-13836.sh

export EH_CONNECT_STRING=$(az eventhubs namespace authorization-rule keys list --resource-group reddog-spring-7314 --namespace-name ehreddog8223briar7314 --name RootManageSharedAccessKey -o json | jq -r '.primaryConnectionString')

echo 'Brian: ' 

export EH_CONFIG='org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="'$EH_CONNECT_STRING'";'

export EH_CONFIG='org.apache.kafka.common.security.plain.PlainLoginModule required username="$ConnectionString" password="Endpoint=sb://ehreddog2655briar.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=nfH5o6bgfipFK6lzF1SWcYuV4l/gDmmZ56osE5fFx2w=";'

VARIABLES_FILE="./outputs/var-reddog-spring-9146.sh"

export SQL_FQDN=sqlreddog2655briar.mysql.database.azure.com

printf "export MYSQL_URL='jdbc:mysql://%s/reddog'\n" $SQL_FQDN >> $VARIABLES_FILE


```