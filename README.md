# micronaut-tutorial-1
Modifying https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link to run in an Openshift environment. 

When running in Openshift, Micronaut detects two environments 'k8s' and 'cloud' . The detection of 'k8s' means that Kubernetes based service discovery will be used.

This means that to enable Consul based service discovery now requires the enabling of a 'consul' environment' this activates the application-consul.yml files.

* [Running Locally](README_local.md)
* [Running in Openshift](README_openshift.md)

