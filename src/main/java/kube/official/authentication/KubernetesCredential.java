package kube.official.authentication;

import io.kubernetes.client.ApiClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


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

    public ApiClient rishiGCEContainerK8(){
        url = (String) jsonObject.get("URL_RISHI_GCE_CONATINER");
        username = (String) jsonObject.get("USERNAME_RISHI_GCE_CONATINER");
        password = (String) jsonObject.get("PASSWORD_RISHI_GCE_CONATINER");
        return io.kubernetes.client.util.Config.fromUserPassword(url, username, password, false);
    }

    public ApiClient shubhamAWSK8(){
        url = (String) jsonObject.get("URL_SHUBHAM_AWS_CONATINER");
        username = (String) jsonObject.get("USERNAME_SHUBHAM_AWS_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SHUBHAM_AWS_CONATINER");

        return io.kubernetes.client.util.Config.fromUserPassword(url, username, password, false);
    }

    public ApiClient subbuGCEContainerXpnServiceNPBetaK8(){
        url = (String) jsonObject.get("URL_SUBBU_GCE_XPN_NP_BETA_CONATINER");
        username = (String) jsonObject.get("USERNAME_SUBBU_GCE_XPN_NP_BETA_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SUBBU_GCE_XPN_NP_BETA_CONATINER");

        return io.kubernetes.client.util.Config.fromUserPassword(url, username, password, false);
    }

    public ApiClient subbuGCEContainerK8(){
        url = (String) jsonObject.get("URL_SUBBU_GCE_CONATINER");
        username = (String) jsonObject.get("USERNAME_SUBBU_GCE_CONATINER");
        password = (String) jsonObject.get("PASSWORD_SUBBU_GCE_CONATINER");

        return io.kubernetes.client.util.Config.fromUserPassword(url, username, password, false);
    }

    public ApiClient kubernetesHAGCE() {
        try {
            url = (String) jsonObject.get("URL_HA_GCE");
            oathToken = (String) jsonObject.get("TOKEN_HA_GCE");

            return io.kubernetes.client.util.Config.fromToken(url, oathToken, false);
        } catch (Exception e){

        }
        return null;
    }
}
