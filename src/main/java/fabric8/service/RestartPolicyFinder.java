package fabric8.service;

import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class RestartPolicyFinder {


    public static void main(String[] args) {

        try {
            final KubernetesClient client = new AuthenticationService(new KubernetesCredential().rishiGCEContainerK8())
                    .authenticate();

            //client.pods().inNamespace("default").withName("redis-django").delete();
            service(client);

        } catch (KubernetesClientException e) {
            e.printStackTrace();
        }
    }

    private static void service(KubernetesClient client){
        String fileLocation = "/yaml/deployment/deployment-with-env-volumes-test.yaml";
        fileLocation = "/yaml/pod/reddis-django.yaml";
        InputStream inputStream = RestartPolicyFinder.class.getResourceAsStream(fileLocation);
        Pod podCreatable = client.pods().load(inputStream).get();
        Pod pod = client.pods().create(podCreatable);

        monitor(client);
    }

    private static void monitor(KubernetesClient client){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        int i = 1;
        boolean isValid = true;
        while (isValid){
            try {
                Thread.sleep(2000);
                Date date = new Date();
                StringBuilder sb = new StringBuilder().append(i).append("  ");
                sb.append(formatter.format(date)).append(" ");
                Pod pod = client.pods().inNamespace("default").withName("redis-django").get();
                List<ContainerStatus>  containerStatuses = pod.getStatus().getContainerStatuses();
                for (ContainerStatus status : containerStatuses){
                    sb.append(status.getName()).append(":").append(status.getRestartCount()).append(" - ");

                    if(status.getRestartCount() > 25){
                        isValid = false;
                    }
                }

                System.out.println(sb.toString());
            } catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }
    }
}
