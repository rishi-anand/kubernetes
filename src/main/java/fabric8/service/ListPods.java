package fabric8.service;

import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import fabric8.util.InputStreamUtil;
import fabric8.util.KubernetesErrorUtil;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.ReplicaSet;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ListPods {


    private static final Logger logger = LoggerFactory.getLogger(ListPods.class);


    public static void main(String[] args) throws Exception{

        try {

            KubernetesClient client = new AuthenticationService(new KubernetesCredential().rishiGCEContainerK8_rishi_namespace())
                    .authenticate();

            //editReplicaSet(client);

            listPods(client);

        } catch (KubernetesClientException kce) {
            logger.error("rishi KubernetesClientException : {}, {}", KubernetesErrorUtil.getErrorMsg(kce), kce);
        } catch (Exception e){
            logger.error("Exception :");
            e.printStackTrace();
        }
    }

    private static void listPods(KubernetesClient client){
        System.out.println(
                "PODS : " + client.pods()
                        .inNamespace("suman")
                        .list()
        );
    }

    private static void updatePod(KubernetesClient client){
        InputStream inputStream = ListPods.class.getResourceAsStream("/yaml/deployment-nginx.yml");
        client.load(inputStream).createOrReplaceAnd();
        System.out.println("Updated pod.json.");
    }

    private static void testObj(KubernetesClient client){
        try {
            String fileLocation = "/yaml/deployment/deployment-with-env-volumes-test.yaml";
            InputStream inputStream = ListPods.class.getResourceAsStream(fileLocation);
            Deployment deployment = client.extensions().deployments().load(inputStream).get();
            logger.info("deployment :", deployment);

            //update in deployment is not working from fabric8 sdk
            Deployment deployment1 = client.extensions().deployments().inNamespace("default").withName("nginx-deployment").get();
            deployment.getSpec().setReplicas(1);


            //client.extensions().deployments().createOrReplace(deployment);
            logger.info("updated deployment OK");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void editReplicaSet(KubernetesClient client) {
        try {
            String namespace = "default";
//        ReplicaSet replicaSet = client.extensions().replicaSets().inNamespace(namespace)
//                .withLabel("test", "21nov").list().getItems().get(0);
//
//        System.out.println(replicaSet);
//
//        replicaSet.getSpec().setReplicas(2);
//
//        ReplicaSet replicaSet1 = client.extensions().replicaSets().createOrReplace(replicaSet);
//        System.out.println(replicaSet1);

//        Deployment deployment = client.extensions().deployments().inNamespace(namespace)
//                .withName("nginx-deployment").scale(-1, true);
//
//
//        System.out.println(deployment);

            String abc = "abc";
            String def = null;

            Pod pod = client.pods().inNamespace(namespace)
                    .withName("wordpress-mysql-e40172-77c654b57f-j7p46").get();

            System.out.println(pod);
            URL url = new URL("https://raw.githubusercontent.com/rishi-anand/kubernetes/master/src/main/resources/yaml/deployment-nginx.yml");

            List<String> test = new ArrayList<String>();
            test.add("wordpress-mysql-e40172-77c654b57f-j7p46");

            Deployment pod1 = client.extensions().deployments().load(url).get();
            System.out.println(pod1);
            PersistentVolumeClaim claim = client.persistentVolumeClaims().inNamespace(namespace).withName("mysql-pv-claim").get();
            System.out.println(claim);
        } catch (Exception e){

        }

    }


}