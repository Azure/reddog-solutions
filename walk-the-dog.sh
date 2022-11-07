export RG_NAME=$1
export LOCATION=$2

# create RG
echo "Creating Azure Resource Group"
az group create --name $RG_NAME --location $LOCATION