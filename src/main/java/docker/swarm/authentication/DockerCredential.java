package docker.swarm.authentication;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;

public class DockerCredential {

    public static Config rishiGCEContainerK8(){
        String url = "https://104.154.224.241";
        return new ConfigBuilder().withMasterUrl(url)
                .withUsername("admin")
                .withPassword("aCxhBbtolu9ft4mk")
                .withTrustCerts(true)
                .build();
    }
}
