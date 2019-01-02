## Running in Kubernetes installing via Helm

An Kubernetes environment is required, when running locally use minikube.

### Local build

Just make sure that everything has been built (kotlin seems to require this as the `gradle run` command doesn't build jars?)
 
    ./gradlew clean build

### Docker builds

    docker build books -t <YOUR NAMESPACE>/micronaut-books
    docker build inventory -t <YOUR NAMESPACE>/micronaut-inventory
    docker build gateway -t <YOUR NAMESPACE>/micronaut-gateway
    docker push <YOUR NAMESPACE>/micronaut-books
    docker push <YOUR NAMESPACE>/micronaut-inventory
    docker push <YOUR NAMESPACE>/micronaut-gateway

### Initialize Helm on the client and server

Rather than install helm via brew I use a docker executable, note how it mounts in read write mode the `pwd`

    alias helm="docker run -ti --rm -v $(pwd):/apps:rw -v ~/.kube:/root/.kube -v ~/.minikube:$(echo ~)/.minikube alpine/helm:2.11.0"

Use the `helm init` to install Tiller (the Helm server-side component) onto your Kubernetes Cluster and set up local configuration in $HELM_HOME (default ~/.helm/):

    helm init

### Enable ingress using the minikube addon

    minikube addons enable ingress

wait until it appears in 

    kubectl get pod -n kube-system -w

### Install via Helm

These are microservices so are intended to be independent with separate release cycles hence separate directories `helm/books`, `helm/inventory` and `helm/gateway`

    helm install helm/books --name books-dev
    helm install helm/inventory --name inventory-dev
    helm install helm/gateway --name gateway-dev

Note that the last installation has instructions on how to access the application. 

To list the releases created 

    helm list
 
### Test

Access using `minikube ip` .
    
    curl -L -k "http://$(minikube ip)/api/books"

### Background (what has changed!)

The Helm charts are based on the output of 

    helm create helm/books
    helm create helm/inventory
    helm create helm/gateway

To see all the change simply re-run these commands and do a `git diff`

The output of `helm create` includes readiness and liveness checks in the deployment.yaml, by default these use a path of `/`. The Micronaut Framework includes support for health check endpoints ( https://docs.micronaut.io/latest/guide/index.html#healthEndpoint ) via an endpoint `/health`. Therefore the `templates/deployment.yaml` has been changed accordingly . In a slightly quick and dirty approach the required addition to the `build.gradle` file in each micro service project was made

    compile "io.micronaut:micronaut-management"

which enables all builtin endpoints (beans, info, health etc.), we would normally enable selectively.

The `ingress.yaml` template seems to be rather more sophisticated than required in a qinikube setting. The version of this file used for the books, inventory and gateway chart is very much simplified. This also applies to the template/NOTES.txt which should be very informative in a proper production cluster setting.

### Delete a release from Kubernetes

    helm delete --purge books-dev
    helm delete --purge inventory-dev
    helm delete --purge gateway-dev 


