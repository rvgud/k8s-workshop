# Services Demo

A service is an abstraction of a logical set of pods defined by a policy.

There are two types of services that we can create in Minikube

Let's create a deployment

```
$ kubectl run api --image=shekhargulati/image-resolver-api
```

## ClusterIP

```
$ kubectl expose deployment api --port=8080 --type=ClusterIP
```

```
$ kubectl run -i --tty --rm --image=alpine --restart=Never -- sh
```

```
wget -qO - http://api:8080/api/v1/images\?url\=https://medium.com/@angelquicksey/service-design-for-policy-b0a9408dced1
```

Delete the service

```
$ kubectl delete svc api
```

### NodePort

```
$ kubectl expose deployment api --port=8080 --type=NodePort
```

```
$ kubectl get service api
```

```
$ minikube ip
```



## Full Demo

We wil have two demos in this

1. NodePort Service: Image-resolver service without cache
2. NodePort and CluserIP Services: Image-resolver service with cache

### NodePort Service: Image-resolver service without cache

Create a new deployment

```
$ kubectl create -f 01-image-resolver-api-depl.yaml
```

Create a service

```
$ kubectl create -f 01-image-resolver-api-svc.yaml
```

View the details of the service using `kubectl get svc`

Get the minikube ip `minikube -p natc ip`

If you access API it will fetch the HTML each time and then find the image.

Delete the deployment

```
$ kubectl delete deployment image-resolver-api-depl
```

### NodePort and CluserIP Services: Image-resolver service with cache

Now, we will add one more service in the picture. This is the service for Redis

```
$ kubectl create -f 02-redis-depl.yaml
```

Create a service

```
$ kubectl create -f 02-redis-svc.yaml
```

Now, we will create a new deployment for API

```
$ kubectl create -f 03-image-resolver-api-depl.yaml
```

View the details of the service using `kubectl get svc`

Get the minikube ip `minikube -p natc ip`

If you access API it will fetch the HTML each time and then find the image.

Now if you make repeated calls to the API, only first request for a URL will be served from the network rest will be served from the cache.

```
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
```

