# micronaut-tutorial-1
A record of working through https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link

## But to get it working it should just be

Start Consul in one shell window

    docker run -p 8500:8500 consul
    
Run the different microservices via gradle in parallel in a separate shell

    ./gradlew -parallel run
    
And then to test in a separate shell

    curl http://localhost:8080/api/books
