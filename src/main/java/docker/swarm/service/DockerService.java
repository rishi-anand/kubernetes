package docker.swarm.service;

import com.github.dockerjava.api.DockerClient;
import docker.swarm.authentication.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerService {

    private static final Logger logger = LoggerFactory.getLogger(DockerService.class);

    private static void test(){
        DockerClient client = AuthenticationService.getClinet();
        System.out.println("client : " + client);
        client.infoCmd().exec().getContainers();
        System.out.print(client.infoCmd().exec().getContainers());
    }


    public static void main(String[] args) throws Exception{
        try {
            test();
        } catch (Exception e) {
            logger.error("rishi KubernetesClientException", e);
        }
    }

}
