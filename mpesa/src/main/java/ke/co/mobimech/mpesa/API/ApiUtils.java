package ke.co.mobimech.mpesa.API;

import ke.co.mobimech.mpesa.API.Remote.NetworkClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtils {
    private static API api = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    static {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static API getPaymentAPI(String BASE_URL, String authToken) {
        OkHttpClient client=NetworkClient.getPaymentOkhttpClient(authToken);
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(API.class);
        }
        return api;
    }

    public static API getSTKPushAPI(String BASE_URL, String authToken) {
        OkHttpClient client=NetworkClient.getSTKPushOkhttpClient(authToken);

        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(API.class);
        }
        return api;
    }

    public static AuthAPI getAuthenticationAPI(String CONSUMER_KEY, String CONSUMER_SECRET, String BASE_URL) {

        OkHttpClient client=NetworkClient.getAuthenticationOkhttpClient(CONSUMER_KEY,CONSUMER_SECRET);
        return NetworkClient.getClient(BASE_URL,client).create(AuthAPI.class);
    }

}
