## AKS Deployment

### Manual Deployment Steps

1. Create namespace

    ```bash
    kubectl create ns reddog
    ```

2. Create ConfigMap

    ```bash
    export DEPLOY_UNIQUE_SUFFIX=7962
    kubectl apply -f ./outputs/config-map-reddog-java-$DEPLOY_UNIQUE_SUFFIX.yaml
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
    ```

4. Deploy GitOps and Config

5. Ingress







