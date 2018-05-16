package ke.co.mobimech.mpesab2c2.API;

import ke.co.mobimech.mpesab2c2.API.Remote.NetworkClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiUtils {
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    static {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static API getPaymentAPI(String BASE_URL, String authToken) {
        OkHttpClient client= NetworkClient.getPaymentOkhttpClient(authToken);
        return NetworkClient.getClient(BASE_URL,client).create(API.class);
    }

    public static AuthAPI getAuthenticationAPI(String CONSUMER_KEY, String CONSUMER_SECRET, String BASE_URL) {

        OkHttpClient client=NetworkClient.getAuthenticationOkhttpClient(CONSUMER_KEY,CONSUMER_SECRET);
        return NetworkClient.getClient(BASE_URL,client).create(AuthAPI.class);
    }

}
