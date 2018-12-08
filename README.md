# micronaut-tutorial-1
Modifying https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link to run in an Openshift environment. 

When running in Openshift, Micronaut detects two environments 'k8s' and 'cloud' . The detection of 'k8s' means that Kubernetes based service discovery will be used.

This means that to enable Consul based service discovery now requires the enabling of a 'consul' environment' this activates the application-consul.yml files.

## Running Locally (as per original tutorial)

Start Consul in one shell window

    docker run -p 8500:8500 consul
    
Run the different microservices via gradle in parallel in a separate shell (but enabling 'consul' environment', not needed in original tutorial)

    export MICRONAUT_ENVIRONMENTS=consul
    ./gradlew -parallel run
    
And then to test in a separate shell

    curl http://localhost:8080/api/books

## Running in Openshift

An Openshift environment is required, when running locally use minishift.

### Local build

Just make sure that everything has been built (kotlin seems to require this as the `gradle run` command doesn't build jars?)
 
    ./gradlew clean build

### Create and start remote Docker builds within Openshift

    oc new-build --context-dir=books --name=books --binary=true
    oc start-build books --from-dir=books
    oc new-build --context-dir=inventory --name=inventory --binary=true
    oc start-build inventory --from-dir=inventory
    oc new-build --context-dir=gateway --name=gateway --binary=true
    oc start-build gateway --from-dir=gateway

### Create applications

The following commands create applications from image streams (these are created by the `oc new-build` command in the previous step)

    oc new-app -i books
    oc new-app -i inventory
    oc new-app -i gateway

### Expose the resulting deployment configurations as services

    oc expose dc books --port=8082
    oc expose dc gateway --port=8080
    oc expose dc inventory --port=8081

### Expose gateway service 

Expose gateway service as a route to allow ingress from outside the cluster

    oc expose service gateway

### Test

The follow should 'just work' but uses a fancy golang template

    curl http://$(oc get route gateway --template='{{ .spec.host }}')/api/books

Otherwise just do 

    oc get route gateway

and cut and paste to form the desired URL.

