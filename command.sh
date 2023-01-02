# minikube en route
# strimzi téléchargé
minikube addons enable ingress

# création du cluster
kubectl create ns kafka
kubectl config set-context --current --namespace=kafka
alias k="kubectl "
kubectl -n kafka apply -f cluster-operator

# déploiement de kafka
kubectl -n kafka apply -f kafka-1.yaml

kubectl -n kafka apply -f kafka-2.yaml
kubectl -n kafka apply -f user-admin.yaml


set bootstrap.servers="http://localhost:9093"
set TOPIC=mytopic
java -cp test-1.jar com.zenika.lba.strimzi.MyProducer



# delete
kubectl -n kafka delete -f kafka-2.yaml
kubectl -n kafka delete -f cluster-operator

kubectl delete ns kafka




kubectl get secret user-admin --template='{{ index .data "user.p12"}}' | base64 -d > /tmp/keystore.p12
kubectl get secret user-admin --template='{{ index .data "user.password"}}' | base64 -d 
kubectl get secret my-cluster-cluster-ca-cert --template='{{ index .data "ca.p12"}}' | base64 -d > /tmp/truststore.p12
kubectl get secret my-cluster-cluster-ca-cert --template='{{ index .data "ca.password"}}' | base64 -d 

keytool -import -alias local -file ca-certificates.crt -keystore /tmp/truststore.p12