package fabric8.service;

import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import fabric8.util.InputStreamUtil;
import fabric8.util.KubernetesErrorUtil;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.ReplicaSet;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;


public class ListPods {


    private static final Logger logger = LoggerFactory.getLogger(ListPods.class);


    public static void main(String[] args) throws Exception{

        try {
            KubernetesClient client = new AuthenticationService(new KubernetesCredential().rishiGCEContainerK8())
                    .authenticate();

            editReplicaSet(client);

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
                        .inNamespace("default")
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

        PersistentVolumeClaim claim = client.persistentVolumeClaims().inNamespace(namespace).withName("slzc").get();
        System.out.println(claim);

    }


}