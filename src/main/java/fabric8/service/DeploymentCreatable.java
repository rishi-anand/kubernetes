package fabric8.service;

import com.google.gson.Gson;
import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;


/*
Total time in deployment for 100 containers : 13 secs (including below time)
Total time taken to initialize 100 pods to 'Pending status' : 3 secs

NOTE: Even if resource is not available then also 'replica' numbers of pod will get created but it may be in
Running status or Pending status (depends on resource availability)

If resource is not available then also pod will be in 'Pending' state.. So, in monitor apart from state also check message.
 */
public class DeploymentCreatable {

    private static final String DEFAULT_NAMESPACE = "default";

    private static final Logger _logger = LoggerFactory.getLogger(ListPods.class);

    public static void main(String[] args) throws Exception{

        try {
            KubernetesClient client = new AuthenticationService(new KubernetesCredential().rishiGCEContainerK8())
                    .authenticate();

            deployment(client);

        } catch (KubernetesClientException kce) {
            _logger.error("rishi KubernetesClientException : {}, {}", kce.getMessage(), kce);
        } catch (Exception e){
            _logger.error("Exception :");
            e.printStackTrace();
        }
    }


    private static void deployment(KubernetesClient client){
        try {
            String fileLocation = "/yaml/deployment/deployment-with-env-volumes-test.yaml";
            fileLocation = "/yaml/deployment-nginx.yml";
            InputStream inputStream = ListPods.class.getResourceAsStream(fileLocation);
            Deployment deploymentCreatable = client.extensions().deployments().load(inputStream).get();
            _logger.info("deployment :", deploymentCreatable);

            deleteIfExist(client, deploymentCreatable.getMetadata().getName());
            //subscribeForEvent(client);
            create(client, deploymentCreatable);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void create(KubernetesClient client, Deployment deploymentCreatable){

        try {
            Integer replica = 1;
            Instant start = Instant.now();
            String instantEpoch = String.valueOf(Instant.now().toEpochMilli());
            deploymentCreatable.getSpec().setReplicas(replica);

            //setting labels in deployment
            deploymentCreatable.getMetadata().getLabels().put(instantEpoch, instantEpoch);

            //setting labels in pod
            deploymentCreatable.getSpec().getTemplate().getMetadata().getLabels().put(instantEpoch, instantEpoch);
            Deployment deployment = client.extensions().deployments().createOrReplace(deploymentCreatable);
            _logger.info("Created deployment '{}' with identifier label '{}'", deployment.getMetadata().getName(), instantEpoch);
            PodList podList = client.pods().inNamespace(DEFAULT_NAMESPACE).withLabel(instantEpoch).list();

            while(podList.getItems().size() != replica){
                Thread.sleep(10);
                podList = client.pods().inNamespace(DEFAULT_NAMESPACE).withLabel(instantEpoch).list();
                _logger.info("Waiting for all pods . Current count : {}", podList.getItems().size());
            }
            printStatusParallel(client, podList.getItems());

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);

            _logger.info("Total time taken to create {} pods is {} seconds ", podList.getItems().size(), duration.getSeconds());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void printStatusParallel(KubernetesClient client, List<Pod> pods){
        _logger.info("==================================================================================================");
        _logger.info("=================================   MONITORING  STATUS OF PODS  ==================================");
        _logger.info("==================================================================================================");


        long waitTime = Long.valueOf(pods.size()) * 5;
        Observable.fromArray(pods.toArray())
                .flatMap(pod -> Observable.just(pod).subscribeOn(Schedulers.io())
                        .map(pod1 -> {
                            return ((Pod) pod1).getMetadata().getName();
                        })
                        .map(name -> {

                            Pod pod2 = client.pods().inNamespace(DEFAULT_NAMESPACE)
                                    .withName(name).get();

                            //get monitor to get the phase
                            while (pod2 == null || pod2.getStatus() == null){
                                Thread.sleep(2);
                                pod2 = client.pods().inNamespace(DEFAULT_NAMESPACE)
                                        .withName(name).get();
                            }

                            String status = pod2.getStatus().getPhase();
                            _logger.info("Pod '{}' initial status : {}", name, status);

                            int retryCount = 0;
                            while(!status.equals("Running") && retryCount < 1000 ){
                                //_logger.info("************************ pod2 : {}", pod2);
                                if(pod2 != null && pod2.getStatus() != null) {
                                    status = pod2.getStatus().getPhase();
                                    _logger.info("Pod '{}' current status '{}', retrying ... ({})", name, status, retryCount);
                                }

                                Thread.sleep(waitTime);
                                retryCount++;

                                pod2 = client.pods().inNamespace(DEFAULT_NAMESPACE)
                                        .withName(name).get();
                            }

                            _logger.info(" ###### RUNNING ###### Pod '{}' current status : {}", name, "Running");
                            return true;
                        })
                )
                .blockingSubscribe();
    }

    private static void deleteIfExist(KubernetesClient client, String name){
        Deployment deployment = client.extensions().deployments().inNamespace("default").withName(name).get();

        if(deployment != null){
            boolean isDeleted = client.extensions().deployments().inNamespace("default").withName(name).delete();
            _logger.info("DELETE Deployment '{}' is deleted - {} ", name, isDeleted);
        }

    }

    private static void subscribeForEvent(KubernetesClient client){
        try {
            final Gson gson = new Gson();
            client.events().inAnyNamespace().withLabel("rishi").watch(new Watcher<Event>() {

                @Override
                public void eventReceived(Action action, Event resource) {
                    System.out.println("event " + action.name() + " " + gson.toJson(resource));
                }

                @Override
                public void onClose(KubernetesClientException cause) {
                    System.out.println("Watcher close due to " + cause);
                }

            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
