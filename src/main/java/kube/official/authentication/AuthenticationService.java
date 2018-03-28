package kube.official.authentication;

import fabric8.authentication.KubernetesCredential;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;


public class AuthenticationService {

    private KubernetesCredential credential;
    private Config config;

    public KubernetesCredential getCredential() {
        return credential;
    }

    public void setCredential(KubernetesCredential credential) {
        this.credential = credential;
    }

    public AuthenticationService(KubernetesCredential credential){
        this.credential = credential;
    }

    public AuthenticationService(Config config){
        this.config = config;
    }

    public KubernetesClient authenticate(){
        try {
            if(credential != null) {
                return new DefaultKubernetesClient(credential.kubernetesHAGCE());
            } else {
                return new DefaultKubernetesClient(config);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
