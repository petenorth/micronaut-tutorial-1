# micronaut-tutorial-1
Modifying https://www.infoq.com/articles/micronaut-tutorial-microservices-jvm?utm_source=infoq&amp;utm_campaign=user_page&amp;utm_medium=link to run in an Openshift environment. 

When running in Openshift, Micronaut detects two environments 'k8s' and 'cloud' . The detection of 'k8s' means that Kubernetes based service discovery can be used.

To this end the Consul based service discovery now requires the enabling of a 'consul' environment' this activates the application-consul.yml files.

* [Running Locally](README_local.md)
* [Running in Openshift](README_openshift.md)
* [Running in Kubernetes](README_kubernetes.md) (using Kubernetes based service discovery)
* [Running in Kubernetes with Zipkin](README_zipkin.md) (using Kubernetes based service discovery)
* TODO [Running in Kubernetes](README_kubernetes_consul.md) (using Consul based service discovery)
* TODO [Deploy using Helm Charts](README_helm.md) (using Kubernetes based service discovery)
* TODO [Running in Kubernetes with Istio](README_helm.md) (using Istio based service discovery and tracing)

