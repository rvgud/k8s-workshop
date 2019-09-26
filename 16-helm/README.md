### Install Helm client

```
curl -LO https://git.io/get_helm.sh
chmod 700 get_helm.sh
./get_helm.sh

```

### Install Helm Server

```
helm init --service-account=tiller

```

### check helm version

```
helm version

```

## Deployment using helm
```
    helm upgrade --install  hello-service -f hello-service/src/main/resources/helm/hello-service/values.yaml  hello-service/src/main/resources/helm/hello-service --namespace=demo
    helm upgrade --install  world-service -f world-service/src/main/resources/helm/world-service/values.yaml  world-service/src/main/resources/helm/world-service --namespace=demo

```