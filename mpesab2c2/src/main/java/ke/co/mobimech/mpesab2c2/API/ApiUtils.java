package ke.co.mobimech.mpesab2c2.API;

import okhttp3.logging.HttpLoggingInterceptor;

public class ApiUtils {
    private static API api = null;
    private static AuthAPI authAPI = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    static {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static API getPaymentAPI(String BASE_URL, String authToken) {

        return api;
    }

    public static AuthAPI getAuthAPI(String CONSUMER_KEY, String CONSUMER_SECRET, String BASE_URL) {


        return authAPI;
    }

}
