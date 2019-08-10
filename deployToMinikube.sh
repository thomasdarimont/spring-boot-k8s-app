#!/usr/bin/env bash

mvn package docker:build \
  && kubectl delete -f k8s/replicaset.yaml || echo "Deploying the for first-time..." \
  && docker save thomasdarimont/demos/spring-boot-k8s-app:latest \
  | ssh -i ~/.minikube/machines/minikube/id_rsa docker@$(minikube ip) docker load
kubectl apply -f k8s/replicaset.yaml
