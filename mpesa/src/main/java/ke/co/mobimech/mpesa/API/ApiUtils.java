package ke.co.mobimech.mpesa.API;

import ke.co.mobimech.mpesa.API.Remote.NetworkClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtils {
    private static MpesaApi mpesaApi = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    static {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static MpesaApi getPaymentAPI(String BASE_URL, String authToken) {
        OkHttpClient client=NetworkClient.getPaymentOkhttpClient(authToken);
        if (mpesaApi == null) {
            mpesaApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(MpesaApi.class);
        }
        return mpesaApi;
    }

    public static MpesaApi getSTKPushAPI(String BASE_URL, String authToken) {
        OkHttpClient client=NetworkClient.getSTKPushOkhttpClient(authToken);

        if (mpesaApi == null) {
            mpesaApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(MpesaApi.class);
        }
        return mpesaApi;
    }

    public static AuthAPI getAuthenticationAPI(String CONSUMER_KEY, String CONSUMER_SECRET, String BASE_URL) {

        OkHttpClient client=NetworkClient.getAuthenticationOkhttpClient(CONSUMER_KEY,CONSUMER_SECRET);
        return NetworkClient.getClient(BASE_URL,client).create(AuthAPI.class);
    }

}
