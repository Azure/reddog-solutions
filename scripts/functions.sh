# Functions

deploy_azure_spring_apps () {

    # Bicep deploy ASA
    az deployment group create \
    --name spring-reddog-asa \
    --mode Incremental \
    --only-show-errors \
    --resource-group $RG \
    --template-file .././deploy/bicep/asa.bicep \
    --parameters uniqueServiceName=$UNIQUE_SERVICE_NAME 
    
}

# write_variables_to_keyvault () {


# }

# deploy_reddog_asa () {


# }

check_for_azure_login () {
    echo 'Checking to ensure logged into Azure CLI'
    AZURE_LOGIN=0 
    # run a command against Azure to check if we are logged in already.
    az group list -o table
    # save the return code from above. Anything different than 0 means we need to login
    AZURE_LOGIN=$?

    if [[ ${AZURE_LOGIN} -ne 0 ]]; then
    # not logged in. Initiate login process
        az login --use-device-code
        export AZURE_LOGIN
    fi
}

aks_get_credentials() {
    AKS_NAME=$(cat ./outputs/$RG_NAME-bicep-outputs.json | jq -r .aksName.value)
    az aks get-credentials \
        -n $AKS_NAME \
        -g $RG_NAME
}

