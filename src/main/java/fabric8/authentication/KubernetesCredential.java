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

    public Config rishi_delete() {
        try {
            url = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI");
            oathToken = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI_TOKEN");
            oathToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJyaXNoaSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJ0ZXN0LWlzc3VlLXRva2VuLTk1cWIyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InRlc3QtaXNzdWUiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJjOTg4ZDljMi04M2M5LTExZTgtODg1NC00MjAxMGE4MDAwOTUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6cmlzaGk6dGVzdC1pc3N1ZSJ9.qksgHypSTI-6gFDawokZUVJ-wV22XEd1p0Qc4HDrehD8onEsVoDfwnh45S0JlF5flC1ZyUO1BEgyA_kbKYcjUY3S8bfvqGqNNjpzWetKYdGGHtXUFEoH6tZw8o5WDbY9yz73PwEn_X1E_VH17Jw4K8M0J6uK9XCdKs7QXgASJ_wDURToU2BGgBjR9kWtSybXv3jXJKYqzTNPm92D9V-O1DFhiDxeWfUkT8ayRkTPTyLyMjR7AZ66NltbywGiTKIJjTkYXQq-fimv6EJaMPI4ndN3-Mp6idaVmM5w1uV8bJBHaL6Y4KhsxeqzWrfTzSz-6d5TN13smgCg9adGIzejoA";

            return new ConfigBuilder()
                    .withMasterUrl(url)
                    .withTrustCerts(true)
                    .withOauthToken(oathToken.replaceAll("(\\r|\\n)", ""))
                    .build();
        } catch (Exception e){

        }
        return null;
    }

    public Config rishi_usdc() {
        try {
            url = "https://10.193.196.4:6443";
            oathToken = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI_TOKEN");
            oathToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJyaXNhbmFuZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJ0ZXN0LWlzc3VlLXRva2VuLXd0ajJ4Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InRlc3QtaXNzdWUiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJmNTQxZmY2Yi04YzQyLTExZTgtYWM5ZC1kNDhjYjViZDA5OTUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6cmlzYW5hbmQ6dGVzdC1pc3N1ZSJ9.Ecstq-cxB_kFSjK4w-Arzy7Kc4QjH65vqSIsupliDzTgnEau55Z0Bzi8K088_Cj4ZDbwUylM-R3XG4qJDGH1KCAWv-095vy963wMbKdjjBZwzEMIY2v7FVYUfx6FtDiF1fOVJGBLUIV8SFEGFheAl4l0yz68xa8E6HIEyZVUje0v3448epktsICCH5BAdDxJulC6J3HwkToJ1nXojp9hwrE-9Gz05Vd--oACJURgfPMQWtt6llA7Kp70RmQlb7ulx8stP7PSTKXy1T1Ug36i2-yklDep2SCxfju_u_0PSFZjejltkPJrqrIpBrRYk4c8sK3K2pyzcx-i_lvj-mfFCg";

            return new ConfigBuilder()
                    .withMasterUrl(url)
                    .withTrustCerts(true)
                    .withOauthToken(oathToken.replaceAll("(\\r|\\n)", ""))
                    .build();
        } catch (Exception e){

        }
        return null;
    }

    public Config rishi_usdc_error() {
        try {
            url = "https://10.193.196.4:6443";
            oathToken = (String) jsonObject.get("URL_RISHI_GCE_CONATINER_NAMESPACED_RISHI_TOKEN");
            oathToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJyaXNhbmFuZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJ0ZXN0LWlzc3VlLXRva2VuLXd0ajJ4Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6InRlc3QtaXNzdWUiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJmNTQxZmY2Yi04YzQyLTExZTgtYWM5ZC1kNDhjYjViZDA5OTUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6cmlzYW5hbmQ6dGVzdC1pc3N1ZSJ9.Ecstq-cxB_kFSjK4w-Arzy7Kc4QjH65vqSIsupliDzTgnEau55Z0Bzi8K088_Cj4ZDbwUylM-R3XG4qJDGH1KCAWv-095vy963wMbKdjjBZwzEMIY2v7FVYUfx6FtDiF1fOVJGBLUIV8SFEGFheAl4l0yz68xa8E6HIEyZVUje0v3448epktsICCH5BAdDxJulC6J3HwkToJ1nXojp9hwrE-9Gz05Vd--oACJURgfPMQWtt6llA7Kp70RmQlb7ulx8stP7PSTKXy1T1Ug36i2-yklDep2SCxfju_u_0PSFZjejltkPJrqrIpBrRYk4c8sK3K2pyzcx-i_lvj-mfFCg_f";

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
