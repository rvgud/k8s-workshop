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
image-resolver-api-depl-795778f9bf-qnfcn   1/1     Running   0          15s
image-resolver-api-depl-795778f9bf-xk85j   1/1     Running   0          23m
```

If you delete one of the pods then you will see that deployment controller will create a new pod.

```
kubectl delete pod image-resolver-api-depl-795778f9bf-55knq
pod "image-resolver-api-depl-795778f9bf-55knq" deleted
```

You will see as shown below that count went below by one but soon it came back to three.

```
image-resolver-api-depl   2/3     2            2           28m
image-resolver-api-depl   2/3     3            2           28m
image-resolver-api-depl   3/3     3            3           28m
```

You can expose a service from a deployment easily using the `expose` command as shown below.  We will cover services in the next section.

```
$ kubectl expose deployment image-resolver-api-depl --type=NodePort
```

You should view the port on which your service is listening by running following command

```
kubectl get svc image-resolver-api-depl
NAME                      TYPE       CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
image-resolver-api-depl   NodePort   10.108.80.18   <none>        8080:32737/TCP   21m
```

As shown above, service is exposed at `32737` port.

You can get IP of the minikube node using following command.

```
$ minikube -p natc ip
```

You can access the application at http://$(minikube -p natc ip):32737

You can delete the deployment using the following command.

```
$ kubectl delete deployment image-resolver-api-depl
```

