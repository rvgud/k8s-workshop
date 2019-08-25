# Pod Demo

This is a cheatsheet for pods.

We have three demos:

1. A pod with single container
2. A pod with a main container and sidecar container
3. A pod with init container

## A pod with single container

Change directory to `03-pod`.

```
$ kubectl create -f 01-pod.yaml
```

View pod information

```
$ kubectl get pod image-resolver-api
```

You can run this in watch mode

```
$ kubectl get pod image-resolver-api --watch
```

To access the pod you have to do port-forwarding

```
$ kubectl port-forward image-resolver-api 8080:8080
```

You can view logs using following

```
$ kubectl logs -f image-resolver-api
```

You can access application

```
$ curl http://localhost:8080/api/v1/images?url=https://medium.com/@angelquicksey/service-design-for-policy-b0a9408dced1
```

Each time you will make a call you will hit the internet and download the HTML of the page. On each request, you will see following line in the log. If you make 4 calls for the same URL you will get see four lines.

```
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
```

## A pod with a main container and sidecar container

Ensure you are `03-pod` directory.

```
$ kubectl create -f 02-pod-sidecar.yaml
```

View pod information

```
$ kubectl get pod image-resolver-api
```

You can run this in watch mode

```
$ kubectl get pod image-resolver-api --watch
```

This time you will have two containers inside the pod.

To access the pod you have to do port-forwarding

```
$ kubectl port-forward image-resolver-api 8080:8080
```

You can view logs using following

```
$ kubectl logs -f image-resolver-api -c image-resolver-api
```

You can access application

```
$ curl http://localhost:8080/api/v1/images?url=https://medium.com/@angelquicksey/service-design-for-policy-b0a9408dced1
```

Now if you make repeated calls to the API, only first request for a URL will be served from the network rest will be served from the cache.

```
c.s.i.r.MainImageResolverResource        : Fetching fresh image...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
c.s.i.r.MainImageResolverResource        : Serving from cache...
```

## A pod with init container

Ensure you are `03-pod` directory.

```
$ kubectl create -f 03-pod-initc.yaml
```

View pod information

```
$ kubectl get pod image-resolver-api
```

You can run this in watch mode

```
$ kubectl get pod image-resolver-api --watch
```

This time you will see that main pod will get created after 30 seconds as init container sleep for 30 seconds.

To access the pod you have to do port-forwarding

```
$ kubectl port-forward image-resolver-api 8080:8080
```

You can access the application

```
$ curl http://localhost:8080/api/v1/images?url=https://medium.com/@angelquicksey/service-design-for-policy-b0a9408dced1
```

