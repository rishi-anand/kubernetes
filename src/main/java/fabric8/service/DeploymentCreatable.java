package fabric8.service;

import com.google.common.collect.Iterators;
import com.google.gson.Gson;
import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import fabric8.util.KubernetesErrorUtil;
import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class DeploymentCreatable {

    private static final String DEFAULT_NAMESPACE = "default";

    private static final Logger _logger = LoggerFactory.getLogger(ListPods.class);

    public static void main(String[] args) throws Exception{

        try {
            KubernetesClient client = new AuthenticationService(new KubernetesCredential().rishiGCEContainerK8())
                    .authenticate();

            testObj(client);

        } catch (KubernetesClientException kce) {
            _logger.error("rishi KubernetesClientException : {}, {}", KubernetesErrorUtil.getErrorMsg(kce), kce);
        } catch (Exception e){
            _logger.error("Exception :");
            e.printStackTrace();
        }
    }


    private static void testObj(KubernetesClient client){
        try {
            String fileLocation = "/yaml/deployment/deployment-with-env-volumes-test.yaml";
            fileLocation = "/yaml/deployment-nginx.yml";
            InputStream inputStream = ListPods.class.getResourceAsStream(fileLocation);
            Deployment deploymentCreatable = client.extensions().deployments().load(inputStream).get();
            _logger.info("deployment :", deploymentCreatable);

            subscribe(client);

            Deployment deployment = client.extensions().deployments().createOrReplace(deploymentCreatable);
            _logger.info("updated deployment OK" + deployment.getApiVersion());


            PodList podList = client.pods().inNamespace(DEFAULT_NAMESPACE).withLabel("rishi").list();
            List<Pod> pods = podList.getItems();
            List<Pod> tempPods = podList.getItems();
            _logger.info("pods length" + pods.size());

            Iterator<Pod> cyclingInstanceFutures = Iterators.cycle(tempPods);

            while (cyclingInstanceFutures.hasNext()) {
                podList = client.pods().inNamespace(DEFAULT_NAMESPACE).withLabel("rishi").list();
                tempPods = podList.getItems();
                cyclingInstanceFutures = Iterators.cycle(tempPods);
                Pod pod = cyclingInstanceFutures.next();
                try {
                    String name = pod.getMetadata().getName();
                    String status = pod.getStatus().getPhase();
                    _logger.info("#*# name : {}, status : {} ", name, status);
                    if (status.equals("Running")) {
                        cyclingInstanceFutures.remove();
                    }
                }catch (Exception e){
                    _logger.error("Failed to get instance", e);
                    cyclingInstanceFutures.remove();
                }
                Thread.sleep(10);
            }




        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void subscribe(KubernetesClient client){
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
