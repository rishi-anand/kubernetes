package kube.official.resources;

import com.google.gson.Gson;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.NetworkingV1Api;
import io.kubernetes.client.models.AppsV1beta1DeploymentList;
import io.kubernetes.client.models.V1NetworkPolicy;
import io.kubernetes.client.models.V1NetworkPolicyList;
import kube.official.authentication.KubernetesCredential;

import java.io.IOException;

public class K8NetworkPolicy {

    //NetworkingV1Api api2 for network policy

    public static void main(String[] args) throws IOException, ApiException {
        Configuration.setDefaultApiClient(new KubernetesCredential().shubhamAWSK8());
        CoreV1Api api = new CoreV1Api();
        Gson gson = new Gson();

        //Client for beta has deployments.. so with official SDK client creation will be like openstack v2/v3
        //With fabric8 same client should work...
        AppsV1beta1Api api1 = new AppsV1beta1Api();
        AppsV1beta1DeploymentList deploymentList = api1.listNamespacedDeployment("default", null, null, null, null, null, null, null, null, null);
        System.out.println(String.format("deploymentList : %s", gson.toJson(deploymentList)));

        listPolicyInAllNamespaces(api, api1);
    }

    public static void listPolicyInAllNamespaces(CoreV1Api api, AppsV1beta1Api api1){
        try {
            NetworkingV1Api api2 = new NetworkingV1Api();
            V1NetworkPolicyList networkPolicyList = api2.listNetworkPolicyForAllNamespaces(null, null, null, null, null, null, null, null, null);
            System.out.println(String.format("Listing %d policy in all namespaces...", networkPolicyList.getItems().size()));

            for (V1NetworkPolicy item : networkPolicyList.getItems()) {
                System.out.println(item.getMetadata().getName());
            }

            System.out.println();
            System.out.println();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
