Spring Boot Kubernetes Example App
===

## Build
```
mvn clean package docker:build
```

## Run in docker
```$
docker run -it --rm --name spring-boot-k8s-app -p 8080:8080 thomasdarimont/demos/spring-boot-k8s-app:latest
```

# Minikube

## Start Minikube
```
minikube start --memory 8192 --disk-size 40g --cpus 4
minikube addons enable ingress

kubectl get pods -n kube-system

# wait for ingress pods to come up...
```

## Upload image to Minikube
```
docker save thomasdarimont/demos/spring-boot-k8s-app:latest \
  | ssh -i ~/.minikube/machines/minikube/id_rsa docker@$(minikube ip) docker load
```

## Deploy app, ingress, tools-container
```
kubectl apply -f k8s 
```

## Access the app from within Minikube
```
kubectl exec -it busybox /bin/sh
wget -q -O- http://spring-boot-k8s-app-svc.default.svc.cluster.local

wget -q -O- http://spring-boot-k8s-app-svc.default

kubectl scale --replicas=4  -f k8s/replicaset.yaml
wget -q -O- http://spring-boot-k8s-app-svc.default

kubectl scale --replicas=1  -f k8s/replicaset.yaml
wget -q -O- http://spring-boot-k8s-app-svc.default
```

## Access the app from the Host
```
kubectl scale --replicas=2  -f k8s/replicaset.yaml

curl --resolve "k8s.tdlabs.local:$(minikube ip)" http://k8s.tdlabs.local/app
```

## Undeploy everything
```
kubectl delete -f k8s/*.yaml
```