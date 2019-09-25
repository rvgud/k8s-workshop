# Deployment Demo

You usually don't create pods directly you let higher level construct like `Deployment` take care of this.

The main reason for this is that Deployment controller ensures pods can self heal i.e if pod will go down then deployment controller will restart it.

To create a deployment run the following command.

```
$ kubectl create -f 01-deployment.yaml
```

This will create a deployment object and start three pods with each pod having one container. 

You can view the pods by running following command

```
$ kubectl get pod
```

```
NAME                                       READY   STATUS    RESTARTS   AGE
image-resolver-api-depl-795778f9bf-55knq   1/1     Running   0          23m
```
kubectl run -i --tty load-generator --image=busybox /bin/sh
while true; do wget -q -O- http://image-resolver-api-svc.default.svc.cluster.local/api/v1/images/?url=https://medium.com/@angelquicksey/service-design-for-policy-
b0a9408dced1; done