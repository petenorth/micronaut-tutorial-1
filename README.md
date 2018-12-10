# micronaut-tutorial-1
Modifying https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link to run in an Openshift environment. 

When running in Openshift, Micronaut detects two environments 'k8s' and 'cloud' . The detection of 'k8s' means that Kubernetes based service discovery can be used.

To this end the Consul based service discovery is now not enabled by default and requires the enabling of a 'consul' environment' this activates the application-consul.yml files.

|Run/Deploy Style|Status|Service Discovery Method|Tracing Method|
|:---|:---|:---|:---|
|[Running Locally](README_local.md)||Consul||
|[Running in Openshift](README_openshift.md)||Kubernetes||
|[Running in Kubernetes](README_kubernetes.md)||Kubernetes||
|[Running in Kubernetes with Zipkin](README_zipkin.md)|TODO|Kubernetes|Zipkin|
|[Running in Kubernetes](README_kubernetes_consul.md)|TODO|Consul||
|[Deploy using Helm Charts](README_helm.md)|TODO|Kubernetes||
|[Running in Kubernetes with Istio](README_isto.md)|TODO|Istio|Istio|

