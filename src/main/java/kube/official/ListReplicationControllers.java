package kube.official;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1ReplicationController;
import io.kubernetes.client.models.V1ReplicationControllerList;
import io.kubernetes.client.util.Config;

import java.io.IOException;

/**
 * Created by rishianand on 11/10/17.
 */
public class ListReplicationControllers {

    private static final String masterUrl = "http://104.198.100.48:8001/";

    public static void main(String[] args) throws IOException, ApiException {
        ApiClient client = Config.fromUrl(masterUrl);
        Configuration.setDefaultApiClient(client);


        CoreV1Api api = new CoreV1Api();


        V1ReplicationControllerList replicationControllerList = api.listReplicationControllerForAllNamespaces(null, null, null, null, null, null);

        System.out.println(String.format("Listing %d replication controllers... ", replicationControllerList.getItems().size()));

        for (V1ReplicationController controller : replicationControllerList.getItems()) {
            System.out.println(controller.getMetadata().getName());
        }
    }
}
