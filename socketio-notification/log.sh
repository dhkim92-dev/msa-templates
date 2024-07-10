#!/bin/bash

pods=$(kubectl get pods -l app=socket -o name)

for pod in $pods; do
  echo "Logs for $pod:"
  kubectl logs $pod --tail 50
  echo ""
done
