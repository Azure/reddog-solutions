## AKS Deployment

### Manual Deployment Steps

Start these steps from the root directory of the repo.

1. Create namespace

    ```bash
    kubectl create ns reddog
    ```

2. Create ConfigMap

    ```bash
    export DEPLOY_UNIQUE_SUFFIX=12345
    kubectl apply -f ./outputs/config-map-reddog-java-spring-$DEPLOY_UNIQUE_SUFFIX.yaml
    ```

3. Deploy Services

    ```bash
    kubectl apply -f ./manifests/

    # or
    kubectl apply -f ./manifests/accounting-service.yaml
    kubectl apply -f ./manifests/loyalty-service.yaml
    kubectl apply -f ./manifests/make-line-service.yaml
    kubectl apply -f ./manifests/order-service.yaml
    kubectl apply -f ./manifests/receipt-generation-service.yaml
    kubectl apply -f ./manifests/virtual-customers.yaml
    kubectl apply -f ./manifests/virtual-worker.yaml
    kubectl apply -f ./manifests/ui.yaml
    kubectl apply -f ./manifests/openai-service.yaml
    ```

4. Deploy GitOps and Config

    ```bash
    export RG='reddog-java-spring-12345'
    export AKS_NAME='aksreddog12345briar12345'

    az k8s-configuration flux create \
    --resource-group $RG \
    --cluster-name $AKS_NAME \
    --cluster-type managedClusters \
    --scope cluster \
    --name reddog-java-apps \
    --namespace flux-system \
    --url https://github.com/appdevgbb/reddog-code-spring.git \
    --branch main \
    --kustomization name=kustomize path=./manifests/ prune=true 
    ```

5. Ingress







