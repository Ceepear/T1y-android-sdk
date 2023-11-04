package net.t1y.v5.android;

public class Option {
    public static final String URL_DEFAULT = "http://dev.t1y.net/api";
    public int Application_ID;
    public String APIKey;
    private String secretKey;
    public String Url;

    public interface OnSecretKeyGetInterface {
        String getSecretKey();
    }

    public Option(String Url, int Application_ID, String APIKey, OnSecretKeyGetInterface onSecretKeyGetInterface) {
        this.Application_ID = Application_ID;
        this.APIKey = APIKey;
        this.secretKey = onSecretKeyGetInterface.getSecretKey();
        this.Url = Url;
    }

    public Option(String Url, int Application_ID, String APIKey, String secretKey) {
        this.Application_ID = Application_ID;
        this.APIKey = APIKey;
        this.secretKey = secretKey;
        this.Url = Url;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
