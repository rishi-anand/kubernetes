package fabric8.service;

import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import fabric8.util.InputStreamUtil;
import fabric8.util.KubernetesErrorUtil;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;


public class ListPods {


    private static final Logger logger = LoggerFactory.getLogger(ListPods.class);


    public static void main(String[] args) throws Exception{

        try {
            KubernetesClient client = new AuthenticationService(new KubernetesCredential().kubernetesHAGCE())
                    .authenticate();

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
                        .inNamespace("default")
                        .list()
        );
    }

    private static void updatePod(KubernetesClient client){
        InputStream inputStream = ListPods.class.getResourceAsStream("/yaml/deployment-nginx.yml");
        client.load(inputStream).createOrReplaceAnd();
        System.out.println("Updated pod.");
    }

    private static void testObj(KubernetesClient client){
        try {
            String fileLocation = "/yaml/deployment/deployment-with-env-volumes-test.yaml";
            InputStream inputStream = ListPods.class.getResourceAsStream(fileLocation);
            Deployment podTemplate = client.extensions().deployments().load(inputStream).get();
            logger.info("podTemplate :", podTemplate);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}