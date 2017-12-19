package fabric8.authentication;

import io.fabric8.kubernetes.client.*;
import org.apache.commons.codec.binary.Base64;


@Deprecated
public class AuthenticationTest {
    private static final String masterUrl = "dummy_url";


    private static final String CLIENT_KEY_DATA = "LS0tLS1CRUdJTiBSU0EgUF                \" ------------          DUMMY DATA       ----------------------  \\n\" +h1RDJZOGVUT1NZRXR1R2QxSzFRTGIvbnFnTnVwZEw5RmVXcVE3MXA1eStTaTlXYjh1CjZ1b052VzlrOHNiY0FUZWtGcHZlVkk2cllkOWx6ZXBYSFZMYjJJeDZDczRKSHFRY0lFREcvK1h6WDlBUUd2SkIKTWRoMEpxRkJFVjMybGJNamVYTUZVc1FjVkd6VG5IN2JMVTE3N3dJREFRQUJBb0lCQUNwSld0Wm9QNDVGY0t6cQpNOUZSQXBEaEYzV09GWGJPSVlyYm9HejNDTldxTURpeEpPVEVzaTNpejhhK285Nkg3Z1hHMEhjclJZYXBNUGZPCmczdWpqOHc0RFAwazE2ZmVVOGF5amZyVCs1a3Y0TWFsbHhJYVl0UDZhNDczMm9LNDlQRDVxZ0U2YkdUVUg4cisKNVJrajhrVHZTZ3IwenZXRVpDSEpJMnRjZi83bXV3T3A4SldwZC81ZmpTTE5pRFQ1eTdJU0ZjK0R2aHhWRmZuWApGTXhYRFEyME5MWU5USFduNk5Da1Jtb1dsbkptVW1GMzFwQXQveUUtFWTUvTnByL2hhVWd3VnRLK2pmWEhHTk9QRkdtbEF2RWJ2UzZwOFplVldiemVYLwpLZFdzMlRQL09JdGVRT3RQQWZHYzJIQzc3ZXd5cmQ0VFBwWVNXWkltcVlTcDU4ai83azV6RXNnL2Rzdi9kV3hGCk1yZ2hpTW5oay93WjZoa2JnY2hMWlRQZUNkM1FCblRya3pHeUpoWEVwVnQxcXFFZDNrc0Z4dEVDZ1lFQTFqMVcKbnoyZmZGK0ZVSzk0N0JxSUM2OHdwUXpSdHhMOTg3RkdoRWdBZnBGeEhUTTl0N2VFRXlyZG5RcE1makUxSWxxbQo2djJreEJaak5NNkJaYjVuMHRFMDlhcnJhZmlZVGVGcW8vdVJ5bDRTNldLcVdCOGRNRTlHVTlzVmMyOUlaU1owCmc5djk2Q1dydUVLVXRtWktLQjJNT3RtOGxtdVFIMEhTUXgvUlJyOENnWUVBNVVkV3FDdU8yM2NyR0xLSWpObTgKVDVxbENpZWdXSTcwVndMUVlWb005dEQzK200MXNTa040OTJGRGs0QkxXbXU2Nnovb1pyUVMwSlE5cDNsRlJmYgpQOFFKTWQ2cDNFbTBhNDNmTUxDQkdmSGpHdUxVVkpieFhKdTk4V09Id0tLbnArSTNDaUt1cklBQ1J4Y25BRFljCk9HYXlmbmJWNldQTEJQRG16MENCNFFFQ2dZQkszUEpKQU9xYk5uSEtpUmF6UW00cHp4VXJoN2c4eGdreHY4L0oKWjdPc2hyeEZIT1BETUFDYUJnK2MwYjE3RjB6ZTdWaUlvd3VSYmRWSzNhSi80REk5aTlkNnhUZ3dOcXRhQTV3VgpJMUtGcWF2b0hBODBiZUx3UDdOdW1BNzk0SFFtekhKSXBYWXJTZ2FnZzd1ZHBVN2loN2k1MDk0L25lU2wxNFZXCjZXWmkxd0tCZ1FDbm5scUdMK3VCUHEwVk42OHZCakRzY0NtL2EvbVpaV3lYMEM4cDJISTc1K1M3MWJQUVZwaUkKbnQ5TExtM3RieE5Rd1R5MUgvNlBkNWJhR2kvdnNFLzBoWGUva2drbXdzMFo0UjNMWWVNYjdmMDJja0Z3WVVRWgpHRjVWVThjWHpqM2crbXdNQ2JWcDYvT3liV0ZLdmxYa0tyRlc1eTdUVUM0UndoT2FjaC9EM2c9PQotLS0tLUVORCBSU0EgUFJJVkFURSBLRVktLS0tLQo=";

    private static final String CLIENT_CERT_DATA = "LS0tLS1CRUdJTiBDlSa                \" ------------          DUMMY DATA       ----------------------  \\n\" +Q4S1FoUUlacTZXaFEKekYzRUNTUlNMWTJzdWo4Mmg3RjFEQkJGSE1FTjdSTFQxNkFWcTUwZDZpU0hQQ0ZEeTlDend3TGs2VWVjazNScApneEw0RVArYm5IU3hJL3F4K1Z1d1Frdk9FVU9KN0xkYmxQb1hBWHY4MHdOV1hlZlVHQ2JscVUrakVaNExUbVNzCmwzdzY4VzdFVTdIOThDcjhER1VCbVY5a3YxUnFXV3pqQU1NVlNJbmJZQmFIRHk0UFpqeDVNNUpnUzI0WjNVclYKQXR2K2VxQTI2bDB2MFY1YXBEdldubkw1S0wxWnZ5N3E2ZzI5YjJUeXh0d0JONlFXbTk1VWpxdGgzMlhONmxjZApVdHZZakhvS3pna2VwQndnUU1iLzVmTmYwQkFhOGtFeDJIUW1vVUVSWGZhVnN5TjVjd1ZTeEJ4VWJOT2NmdHN0ClRYdnZBZ01CQUFHakVEQU9NQXdHQTFVZEV3RUIvd1FDTUFBd0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFKcnUKRmVCbWJEdCtKT2h4dzgzc0zJMWTZJbFJ1cG4zCkRtU09aSi8rNEtjQUo1dmxqQ3RlVWQvRWNVblUzUURYd1ZNUFFYanVwM2lyZEliMi9ha2FneE9nNTE0QmpvNXMKdndLMFF0VVpFclFXbVQyWnUxR21HQVJ6QkdIeUJFR3FaaUl4dVVxMXZWMWxIeGNORERQZmpzd0hCcEZUQTJXagpGcHdnOW1RMkEwSmxRYzNIdzZEUEQrTENodnIzaGxzTUd1V0RPaHVOUzh0VmtsaE4wUmtwcENZNHFQNm5zUmE4CjM5MmtxTnJzSlVYUlB5ZERvejg9Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K";

    private static String CA_CERT_DATA = "LS0tLS1CRUdJ                \" ------------          DUMMY DATA       ----------------------  \\n\" +FVGkvTklFaElXbjZYYjQvTApXSjRRRDcxdFNDYlltUmZDL0hVajhSTXpjYTk5VzVjamxENGZNaHllb1UyYXFHb3I3SXVoWm8zT3pwVHRLTzRnCmhRaVVVU2hhbTdGWW5rZmFvd2tVtK2czNEZUYW1YNmxtS0p6bVZJRUlGS3ViU3lVWXNXc2xWTApQcG5jQjJTY3RLZ3BabDhNSjgwVnd4T09oak05U2x0REM4b0h1d3pxK1ZZZ0lQSDh3eC9RUUNjZEZyOUhJWHJ3CjVBT1FxNS9WUVowWjVOZTArQlNCc1A1aUpsQVQ3OExGamt4elI2RDNneFFIUXdJREFRQUJveU13SVRBT0JnTlYKSFE4QkFmOEVCQU1DQWdRd0R3WURWUjBUQVFIL0JBVXdBd0VCL3pBTkJna3Foa2lHOXcwQkFRc0ZBQU9DQVFFQQpDWVF4aHFnNktpN2swRmFGZStpR29tVm92YUR3OWhFbDY2SzdBcFc0azdMMlJQTTRrMnFmKzRxQ1E4MHNhZ0ZhCkhMU2hiM21BQnVxQlJhUDhTNWlaOGtFS0p6RWMwdzB2ckM4Z0NUU2swSWZUQ3dvc1lSdTBORC9YbHVNZUZ0RHcKdFpWOE9Ba0x1QkwwRi8xSXFHRzAyd2NoTGdzQVE4VEUzeWN0dnF1RzBtTzhOeXJ2RTgyVmlBdXlDSmN2TE9jUgpCZmZ6emNhMjBVTGZmc1FQWDB1QjhTbm9HYlFNNGFCQm9EWTZTb254NkR0dkNzQnlrc3AwQUgwNlhDSERmcXVZCmdsajc2STEzeUFZdlFweEF2bGp2Q3V3cGR0WWQ1dnNPZ3YyMkY3SVZiNURpSDdPTDd0dzdqWFJpdlFLekN4RjUKa1dOei9xWDJGSk0wRVFzNndXUGFuUT09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K";

    public static void main(String[] args) throws Exception{

        try {

            KubernetesClient client = googleSClusterClient();
            System.out.println("API Version : " + client.pods().inNamespace("default").list());

        } catch (KubernetesClientException ke) {
            System.out.println("Caught Exception ");
            ke.printStackTrace();
        }
    }

    private static KubernetesClient googleSClusterClient(){
        Config config = getCertConfig();
        config = getController1();
        //config = getController1();

        return new DefaultKubernetesClient(config);
    }

    private static Config getCertConfig(){
        Config config = new ConfigBuilder().withMasterUrl(masterUrl)
                .withTrustCerts(true)
                .withCaCertData(new String(Base64.encodeBase64(CA_CERT_DATA.getBytes())))
                .withClientCertData(new String(Base64.encodeBase64(CLIENT_CERT_DATA.getBytes())))
                .withClientKeyData(new String(Base64.encodeBase64(CLIENT_KEY_DATA.getBytes())))

                .withCaCertData(CA_CERT_DATA)
                .withClientCertData(CLIENT_CERT_DATA)
                .withClientKeyData(CLIENT_KEY_DATA)
                .build();

        return config;
    }


    private static Config getController1(){
        String oathToken = "eyJhbGciOiJSUzI1NiIsInR" +
                " ------------          DUMMY DATA       ----------------------  \n" +
                "VMpTcrluOpuF_n1cHbrg";

        oathToken = "eyJhbGciOiJSUzI1NiIsInR" +
                "5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia\n" +
                " ------------          DUMMY DATA       ----------------------  \n" +
                "VMpTcrluOpuF_n1cHbrg";

        Config config = new ConfigBuilder()
                .withMasterUrl("https://35.199.160.101:6443")
                .withTrustCerts(true)
                .withOauthToken(oathToken.replaceAll("(\\r|\\n)", ""))
                .withCaCertFile("/Users/risanand/work/kube8/kube-hard-way/ca.pem")

        .build();

        return config;
    }

    private static Config getHATokenConfig2(){
        String CLIENT_CERT="-----BEGIN CERTIFICATE-----\n" +
                "MIIDCDCCAfACCQCImYx1TJXElDANBgkqhkiG9w0BAQUFADBoMQswCQYDVQQGEwJV\n" +
                " ------------          DUMMY DATA       ----------------------  \n" +
                "sp0QoYTYo6/V7e7d\n" +
                "-----END CERTIFICATE-----";
        String CLIENT_KEY="-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIEpAIBAAKCAQEAwm9sva2vOkpLGwW6dhCw+xt8uE1TTDXObWqeO8g8jHiGvcSV\n" +
                "2V3uD5qne94st7mewXuGs4/3FWYpIZwkm4I7cafO7L8MmENNwCKx32u934dfc/fZ\n" +
                "oLD9qYr5d21jQ3LoXERsf/71WSvyumig1GEbSAk3zal6RnBvfM5xjAFXKK+J1TKk\n" +

                "nlTV1mECgYA5Hw6FaB+tVwB1jh7KKiCx3DD6MITjC6qy8KaEg/dyo/Y0KqlVtMDQ\n" +
                "369fJaZjtcOuzERbg31hJ3NY4QKFbmxW2kXFUXVUT2gASQuF4WL3nmfYkwy6PrLx\n" +
                "meE1XVmTR9P8b8M/Nz39pVzXQ9eXn8EPvR1Swv+1Nf+RDkPLyl30Tg==\n" +
                "-----END RSA PRIVATE KEY-----";

        String CA_CERT="-----BEGIN CERTIFICATE-----\n" +
                "9gYdZgLupoUCcnL6HfX3q7lKqM3t12K81yxdRjnnlOksDAXXtIV//Z1VhCmv8laP\n" +
                " ------------          DUMMY DATA       ----------------------  \n" +
                "MdZYAHFgo/UyG DUMMY  DATA  3qeXk\n" +
                "BED1yZFwCmlcqXdPcPf46YMApnRp6IEcIxWPBKZggDFbL5OdJfy7UHcQEeKhxQ+D\n" +
                "CCB10ZClviEfdfTjnnpoql4b8bfbphc=\n" +
                "-----END CERTIFICATE-----";
        Config config = new ConfigBuilder()
                .withMasterUrl("https://35.199.160.101:6443")
                .withTrustCerts(true)
                //.withCaCertData(new String(Base64.encodeBase64(CA_CERT.getBytes())))
                .withCaCertFile("/Users/risanand/work/kube8/kube-hard-way/ca.pem")
                .withClientCertData(new String(Base64.encodeBase64(CLIENT_CERT.getBytes())))
                .withClientKeyData(new String(Base64.encodeBase64(CLIENT_KEY.getBytes())))
                .build();

        return config;
    }
}
