package docker.swarm.authentication;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

public class AuthenticationService {

    public static DockerClient getClinet(){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("172.16.38.139")
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .build();

        return dockerClient;
    }
}
