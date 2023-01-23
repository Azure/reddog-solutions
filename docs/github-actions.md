## GitHub Actions Notes

```bash
RG='reddog-spring-20554'
ACR_NAME='reddogjavaacr'
LOCATION='eastus'

az acr create --resource-group $RG \
--name $ACR_NAME --sku Standard \
--admin-enabled false \
--location $LOCATION

docker build -t chzbrgr71/reddog-java-order-service:v1 .

az acr build -r $ACR_NAME -t reddog-demo/reddog-java-order-service:v1 .
 
az acr build -r $ACR_NAME -t reddog-demo/reddog-java-order-service:v1 -f ./order-service/Dockerfile ./order-service


```
