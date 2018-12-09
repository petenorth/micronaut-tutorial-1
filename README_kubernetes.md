## Running in Kubernetes

An Kubernetes environment is required, when running locally use minikube.

### Local build

Just make sure that everything has been built (kotlin seems to require this as the `gradle run` command doesn't build jars?)
 
    ./gradlew clean build

### Docker builds

    docker build books -t petenorth/micronaut-books
    docker build inventory -t petenorth/micronaut-inventory
    docker build gateway -t petenorth/micronaut-gateway
    docker push petenorth/micronaut-books
    docker push petenorth/micronaut-inventory
    docker push petenorth/micronaut-gateway

### Create deployments in Kubernetes

    kubectl run books --image=petenorth/micronaut-books --port=8082
    kubectl run inventory --image=petenorth/micronaut-inventory --port=8081
    kubectl run gateway --image=petenorth/micronaut-gateway --port=8080

### Create service definitions for the deployments

    kubectl expose deployment books
    kubectl expose deployment inventory
    kubectl expose deployment gateway

### Enable ingress using the minikube addon

    minikube addons enable ingress

wait until it appears in 

    kubectl get pod -n kube-system -w

### Scale down the deployments and then scale up to pick up the environment variables (MUST BE A BETTER WAY OF DOING THIS)

    kubectl scale --replicas=0 deployment/inventory
    kubectl scale --replicas=0 deployment/gateway
    kubectl scale --replicas=0 deployment/books

Wait until terminated

    kubectl scale --replicas=1 deployment/inventory
    kubectl scale --replicas=1 deployment/gateway
    kubectl scale --replicas=1 deployment/books

### Apply ingress resource definition

    kubectl apply -f kubernetes/ingress.yml 

Wait until it has an address

    kubectl get ing -w

### Test

Access using `minikube ip` .
    
    curl -L -k "http://$(minikube ip)/api/books"

### TODO

* Create declarative yml/json for the resource definitions (one click creation)
* Implement via Helm 

