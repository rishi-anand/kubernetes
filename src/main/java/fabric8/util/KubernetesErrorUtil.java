package fabric8.util;

import io.fabric8.kubernetes.client.KubernetesClientException;
import sun.security.validator.ValidatorException;

import java.net.ConnectException;
import java.net.ProtocolException;


public class KubernetesErrorUtil {

    public static String getErrorMsg(KubernetesClientException kce){

        if(EmptyUtil.isNotNull(kce.getCause())) {
            if (isProtocolException(kce.getCause())) {
                return "Mention protocol before endpoint.";
            }

            if (isConnectException(kce.getCause())) {
                return "Mention api-server port number after endpoint if applicable.";
            }

            if (isValidatorException(kce.getCause())) {
                return "Trust CA certificates";
            }
        }

        if(kce.getCode() == 401) {
            return "Service account token is invalid or given service account does not have permission for requested resource.";
        }

        return kce.getMessage();
    }

    private static boolean isProtocolException(Throwable throwable){
        return (throwable instanceof ProtocolException) ? true : false;
    }

    private static boolean isConnectException(Throwable throwable){
        return (throwable instanceof ConnectException) ? true : false;
    }

    private static boolean isValidatorException(Throwable throwable){
        return (throwable instanceof ValidatorException) ? true : false;
    }


}
