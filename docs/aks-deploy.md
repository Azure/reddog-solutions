## AKS Deployment

### Post deployment steps

1. Deploy ConfigMap
2. Test yaml manually
3. Deploy GitOps extension
4. GitOps config
5. Ingress

```bash
kubectl create ns reddog
kubectl apply -f ./manifests/config-map.yaml
kubectl apply -f ./outputs/config-map-reddog-spring-30548.yaml

kubectl apply -f ./manifests/make-line-service.yaml

```
