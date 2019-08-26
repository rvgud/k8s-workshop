```
echo -n 'admin' | base64
```
```
echo -n '1f2d1e2e67df' | base64
```

Create the secret, deployment, and service.

You can check secrets are injected by SSHing into the container and running env command.

```
$ kubectl exec -it container_id sh
```

```
env
```