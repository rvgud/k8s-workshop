# DaemonSet Demo

A DaemonSet ensures that all (or some) Nodes run a copy of a Pod. As nodes are added to the cluster, Pods are added to them. As nodes are removed from the cluster, those Pods are garbage collected. Deleting a DaemonSet will clean up the Pods it created.
To create a deployment run the following command.

```
$ kubectl create -f 01-daemonSet.yaml
```
