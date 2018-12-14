# micronaut-tutorial-1
Modifying https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link to run in an Openshift environment. 

When running in Openshift, Micronaut detects two environments 'k8s' and 'cloud' . The detection of 'k8s' means that Kubernetes based service discovery can be used.

To this end the Consul based service discovery is now not enabled by default and requires the enabling of a 'consul' environment' this activates the application-consul.yml files.

|Run/Deploy Style|Status|Service Discovery Method|Tracing Method|
|:---|:---|:---|:---|
|[Running Locally](README_local.md)||Consul||
|[Running in Openshift](README_openshift.md)||Kubernetes||
|[Running in Kubernetes](README_kubernetes.md)||Kubernetes||
|[Deploy using Helm Charts](README_helm.md)||Kubernetes||
|[Running in Kubernetes with Zipkin](README_zipkin.md)|TODO|Kubernetes|Zipkin|
|[Running in Kubernetes with Consul](README_kubernetes_consul.md)|Not Working (see note below)|Consul||
|[Running in Kubernetes with Istio](README_isto.md)|TODO|Istio|Istio|

It arguable that running Consul on Kubernetes as a way of supporting service discovery is misguided. Having investigated the technical overhead (running stateful sets, the presence of Zookeeper and the addition of ConsulDNS), coupled with not managing to get it to run successfully leads to think 'why do this?'. The Kubernetes Service Discovery is not dynamic in the sense that the pods must be created after the services have been defined otherwise the environment variables used by the micronaut's (and most other frameworks) Kubernetes service discovery mechanism will not be defined, but other than this it is simple and reliable. The problem seems to be that the service is registered with Consul using the pod's host name which is random and not resolvable when a client attempts to use the service.
