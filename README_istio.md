## Minimal Install of Istio

    minikube start --vm-driver=xhyve --memory 5140
    #IMPORTANT version 2.12.0 of helm doesn't work
    alias helm="docker run -ti --rm -v $(pwd):/apps:rw -v ~/.kube:/root/.kube -v ~/.minikube:$(echo ~)/.minikube alpine/helm:2.11.0"
    #is the service account needed etc. when the default minikube install doesn't have RBAC enabled?
    kubectl apply -f install/kubernetes/helm/helm-service-account.yaml
    helm init --service-account tiller
    helm install install/kubernetes/helm/istio --name istio-minimal --namespace istio-system \
      --set security.enabled=false \
      --set ingress.enabled=false \
      --set gateways.istio-ingressgateway.enabled=false \
      --set gateways.istio-egressgateway.enabled=false \
      --set galley.enabled=false \
      --set sidecarInjectorWebhook.enabled=false \
      --set mixer.enabled=false \
      --set prometheus.enabled=false \
      --set global.proxy.envoyStatsd.enabled=false \
      --set pilot.sidecar=false
    kubectl get pods -n istio-system -w

## Creating different docker images for the different versions

The CI/CD pipelines will have managed the following in a real life situation, note that these steps have already taken place:

### Create a release branch

    git checkout -b 1.0.x

### Edit the source code to return something clearly identifying the version (1.0.152) of code, build, commit, push, docker build, docker push and git tag

    vi books/src/main/groovy/example/micronaut/BooksRepositoryImpl.groovy 
    cd books
    ./gradlew clean build
    cd ..
    git add books/src/main/groovy/example/micronaut/BooksRepositoryImpl.groovy   
    git commit -m 'changes for v1.0.152'
    git push --set-upstream origin 1.0.x
    docker build books -t petenorth/micronaut-books:1.0.152
    docker push petenorth/micronaut-books:1.0.152
    git tag -a 1.0.152 -m "a release that will be used for the existing version of code in a canary release"
    git push origin 1.0.152

### Edit the source code to return something clearly identifying the version (1.0.156) of code, build, commit, push, docker build, docker push and git tag

    vi books/src/main/groovy/example/micronaut/BooksRepositoryImpl.groovy 
    cd books
    ./gradlew clean build
    cd ..
    git add books/src/main/groovy/example/micronaut/BooksRepositoryImpl.groovy 
    git commit -m 'changes for v1.0.156'
    git push
    docker build books -t petenorth/micronaut-books:1.0.156
    docker push petenorth/micronaut-books:1.0.156
    git tag -a 1.0.156 -m "a release that will be used for the new version of code in a canary release"
    git push origin 1.0.156

### Deploy as current and canary as two separate helm releases

Make sure you are in the root directory of where ever you have clone this project to.

    alias helm="docker run -ti --rm -v $(pwd):/apps:rw -v ~/.kube:/root/.kube -v ~/.minikube:$(echo ~)/.minikube alpine/helm:2.11.0"

Install a 'current' version

    helm install helm/books --name books-current --set image.tag=1.0.152 --set fullnameOverride=books-current
    kubectl get deployment books-current --export=true -o yaml > books-current.yaml
    ~/istio-1.0.5/bin/istioctl kube-inject -f books-current.yaml | kubectl apply -f -

Install a 'canary' version

    helm install helm/books --name books-canary --set image.tag=1.0.156 --set fullnameOverride=books-canary
    kubectl get deployment books-canary --export=true -o yaml > books-canary.yaml
    ~/istio-1.0.5/bin/istioctl kube-inject -f books-canary.yaml | kubectl apply -f -

Wait for everything to deploy

    kubectl get pod -w
    
Should get to point where you have pods running

    books-current-7545c86bf6-7bdrz  2/2       Running   0          14h
    books-canary-589b867d7d-6t8sk   2/2       Running   0          14h
