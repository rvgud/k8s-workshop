## Command based liveness and readiness probes
    kubectl create -f 01-command.yaml
    # cleanup
    kubectl delete -f 01-command.yaml

## HTTP request based liveness and readiness probes
    kubectl create -f 02-httpGet.yaml
    # cleanup
    kubectl delete -f 02-httpGet.yaml

## TCP Socket based liveness and readiness probes
    kubectl create -f 03-tcpSocket.yaml
    # cleanup
    kubectl delete -f 03-tcpSocket.yaml