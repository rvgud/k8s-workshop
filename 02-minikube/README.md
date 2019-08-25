# Minkube Demo

> You don't have to type `$`. `$` signifies command prompt.

You start by creating a local cluster

```
$ minikube start -p natc --cpus=2 --memory='4g' --vm-driver='virtualbox'
```

It takes a few minute to create a local cluster.

We can check status of our cluster using the below mentioned command.

```
$ minikube -p natc status
```

```
host: Running
kubelet: Running
apiserver: Running
kubectl: Correctly Configured: pointing to minikube-vm at 192.168.99.112
```

We can get IP address of our Minikube instance by running following command.

```
$ minikube -p natc ip
192.168.99.112
```

To view the dashboard we can use following command.

```
$ minikube -p natc dashboard
```

![](images/miniube-dashboard.png)

## Check context of kubectl

```
$ kubectl config current-context
```

## Configure environment for Docker

```
$ minikube -p natc docker-env
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.112:2376"
export DOCKER_CERT_PATH="/Users/shekhargulati/.minikube/certs"
# Run this command to configure your shell:
# eval $(minikube docker-env)
```

```
$ eval $(minikube -p natc docker-env)
```

> If you do `docker ps` now, you will see docker containers started by Kubernetes. These are all system containers you don't have to mess with them.

## SSH into minikube VM

```
$ minikube -p natc ssh
```

Now, you can run any command

```
free -h
df -h
exit
```

