package fabric8.service;

import fabric8.authentication.KubernetesCredential;
import fabric8.authentication.AuthenticationService;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentSpec;
import io.fabric8.kubernetes.api.model.extensions.DeploymentSpecBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class ListServices {

    public static void main(String[] args) {

        try {
            final KubernetesClient client = new AuthenticationService(new KubernetesCredential().kubernetesHAGCE())
                    .authenticate();

            System.out.println(client.services().list());

        } catch (KubernetesClientException e) {
            e.printStackTrace();
        }
    }
}
