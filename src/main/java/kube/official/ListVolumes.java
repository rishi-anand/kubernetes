package kube.official;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1PersistentVolume;
import io.kubernetes.client.models.V1PersistentVolumeList;
import io.kubernetes.client.util.Config;

import java.io.IOException;

/**
 * Created by rishianand on 11/10/17.
 */
public class ListVolumes {

    private static final String masterUrl = "http://104.198.100.48:8001/";

    public static void main(String[] args) throws IOException, ApiException {
        ApiClient client = Config.fromUrl(masterUrl);
        Configuration.setDefaultApiClient(client);


        CoreV1Api api = new CoreV1Api();

        V1PersistentVolumeList volumeList = api.listPersistentVolume(null, null, null, null, null, null, null, null, null);

        System.out.println(String.format("Listing %d volumes... ", volumeList.getItems().size()));

        for (V1PersistentVolume volume : volumeList.getItems()) {
            System.out.println(volume.getMetadata().getName());
        }
    }
}
