# RBAC Demo

Create a Service account ,Role and Role Binding

```
kubectl create -f 01-service-account.yaml
kubectl create -f 02-role.yaml
kubectl create -f 03-role-binding.yaml

```

Check permissons of created servce account:-
```
kubectl auth can-i get pod --as=system:serviceaccount:default:ravindra
yes
kubectl auth can-i delete  pod --as=system:serviceaccount:default:ravindra
no
```

Create a ClusterRole and Cluster Role Binding
kubectl apply -f ./


Check permissons of created servce account:-

kubectl auth can-i get  secret  --as=system:serviceaccount:default:ravindra
yes
kubectl auth can-i delete  secret  --as=system:serviceaccount:default:ravindra
no