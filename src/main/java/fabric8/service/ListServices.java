package fabric8.service;

import fabric8.authentication.AuthenticationService;
import fabric8.authentication.KubernetesCredential;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.extensions.NetworkPolicy;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class ListServices {

    public static void main(String[] args) {

        try {
            final KubernetesClient client = new AuthenticationService(new KubernetesCredential().shubhamAWSK8())
                    .authenticate();

            service(client);

        } catch (KubernetesClientException e) {
            e.printStackTrace();
        }
    }

    private static void service(KubernetesClient client){
        Service service = client.services().inNamespace("default").withName("wordpress-rishi").get();
        System.out.println(service);
    }

}
