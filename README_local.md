## Running Locally (as per original tutorial)

Start Consul in one shell window

    docker run -p 8500:8500 consul
    
Run the different microservices via gradle in parallel in a separate shell (but enabling 'consul' environment', not needed in original tutorial)

    export MICRONAUT_ENVIRONMENTS=consul
    ./gradlew -parallel run
    
And then to test in a separate shell

    curl http://localhost:8080/api/books

