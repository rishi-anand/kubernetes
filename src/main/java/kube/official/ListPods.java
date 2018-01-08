package kube.official;

import com.google.gson.Gson;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.AppsV1beta1Deployment;
import io.kubernetes.client.models.AppsV1beta1DeploymentList;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;

import java.io.IOException;

/**
 * Created by rishianand on 11/10/17.
 */
public class ListPods {

    private static final String masterUrl = "http://104.198.100.48:8001/";
    private static final String namespace = "default";
    private static final String masterUrlLocalhost = "http://127.0.0.1:9999/";

    public static void main(String[] args) throws IOException, ApiException {
        ApiClient client = Config.fromUrl(masterUrlLocalhost);
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        Gson gson = new Gson();

        //Client for beta has deployments.. so with official SDK client creation will be like openstack v2/v3
        //With fabric8 same client should work...
        AppsV1beta1Api api1 = new AppsV1beta1Api();
        AppsV1beta1DeploymentList deploymentList = api1.listNamespacedDeployment("default", null, null, null, null, null, null);
        System.out.println(String.format("deploymentList : %s", gson.toJson(deploymentList)));

        AppsV1beta1Deployment deployment = api1.readNamespacedDeployment("nginx-deployment", "default", null, null, null);

        System.out.println(String.format("deployment : %s", gson.toJson(deployment)));

        listPodsInAllNamespaces(api);

        listPodsInNamespace(api, namespace);

    }

    public static void listPodsInAllNamespaces(CoreV1Api api){
        try {
            V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null);
            System.out.println(String.format("Listing %d pods in all namespaces...", podList.getItems().size()));

            for (V1Pod item : podList.getItems()) {
                System.out.println(item.getMetadata().getName());
            }

            System.out.println();
            System.out.println();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void listPodsInNamespace(CoreV1Api api, String namespace){
        try {

            V1PodList podList = api.listNamespacedPod(namespace, null, null, null, null, null, null);
            System.out.println(String.format("Listing %d pods in namespace '%s'...", podList.getItems().size(), namespace));

            for (V1Pod item : podList.getItems()) {
                System.out.println(item.getMetadata().getName());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
