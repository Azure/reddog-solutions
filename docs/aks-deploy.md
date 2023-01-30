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

kubectl apply -f ./manifests/make-line-service.yaml
kubectl apply -f ./manifests/test-pod.yaml

kubectl create configmap special-config --from-literal=special.how=very --from-literal=special.type=charm

kubectl create configmap brian --from-file=./outputs/var-reddog-spring-25730.sh

```

https://www.geeksforgeeks.org/kubernetes-create-config-map-from-files 

https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap 
https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/#generate-configmaps-from-files
