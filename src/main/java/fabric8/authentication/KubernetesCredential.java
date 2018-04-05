package fabric8.authentication;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Properties;


public class KubernetesCredential {

    private static final String AUTH_FILE = "/Users/risanand/work/kubernetes/src/main/resources/auth.properties";
    private String url;
    private String caCertData;
    private String username;
    private String password;
    private String oathToken;

    private JSONObject jsonObject;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaCertData() {
        return caCertData;
    }

    public void setCaCertData(String caCertData) {
        this.caCertData = caCertData;
    }

    public KubernetesCredential(){
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(AUTH_FILE);
            Object obj = parser.parse(reader);
            jsonObject = (JSONObject) obj;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Config rishiGCEContainerK8(){
        url = (String) jsonObject.get("URL_RISHI_GCE_CONATINER");
        username = (String) jsonObject.get("USERNAME_RISHI_GCE_CONATINER");
        password = (String) jsonObject.get("PASSWORD_RISHI_GCE_CONATINER");

        return new ConfigBuilder().withMasterUrl(url)
                .withUsername(username)
                .withPassword(password)
                .withTrustCerts(true)
                .build();
    }

    public Config shubhamAWSK8(){
        url = (String) jsonObject.get("URL_SHUBHAM_AWS_CONATINER");
        username = (String) jsonObject.get("USERNAME_SHUBHAM_AWS_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SHUBHAM_AWS_CONATINER");

        return new ConfigBuilder().withMasterUrl(url)
                .withUsername(username)
                .withPassword(password)
                .withTrustCerts(true)
                .build();
    }

    public Config subbuGCEContainerXpnServiceNPBetaK8(){
        url = (String) jsonObject.get("URL_SUBBU_GCE_XPN_NP_BETA_CONATINER");
        username = (String) jsonObject.get("USERNAME_SUBBU_GCE_XPN_NP_BETA_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SUBBU_GCE_XPN_NP_BETA_CONATINER");

        return new ConfigBuilder().withMasterUrl(url)
                .withUsername(username)
                .withPassword(password + "jndjk")
                .withTrustCerts(true)
                .build();
    }

    public Config subbuGCEContainerK8(){
        url = (String) jsonObject.get("URL_SUBBU_GCE_CONATINER");
        username = (String) jsonObject.get("USERNAME_SUBBU_GCE_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SUBBU_GCE_CONATINER");

        return new ConfigBuilder().withMasterUrl(url)
                .withUsername(username)
                .withPassword(password + "token")
                .withTrustCerts(true)
                .build();
    }

    public Config kubernetesHAGCE() {
        try {
            url = (String) jsonObject.get("URL_HA_GCE");
            oathToken = (String) jsonObject.get("TOKEN_HA_GCE");

            return new ConfigBuilder()
                    .withMasterUrl(url)
                    .withTrustCerts(true)
                    .withOauthToken(oathToken.replaceAll("(\\r|\\n)", ""))
                    .build();
        } catch (Exception e){

        }
        return null;
    }

    public Config rishiGCEContainerK8_rishi_namespace() {
        try {
            url = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI");
            oathToken = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI_TOKEN");

            return new ConfigBuilder()
                    .withMasterUrl(url)
                    .withTrustCerts(true)
                    .withOauthToken(oathToken.replaceAll("(\\r|\\n)", ""))
                    .build();
        } catch (Exception e){

        }
        return null;
    }
}
