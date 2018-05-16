package ke.co.mobimech.mpesab2c2.API.Remote;

import java.util.concurrent.TimeUnit;
import ke.co.mobimech.mpesab2c2.API.Interceptors.AccessTokenInterceptor;
import ke.co.mobimech.mpesab2c2.API.Interceptors.AuthInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static ke.co.mobimech.mpesab2c2.Utiils.CommonUtils.CONNECTION_TIMEOUT;
import static ke.co.mobimech.mpesab2c2.Utiils.CommonUtils.FETCH_TIMEOUT;
import static ke.co.mobimech.mpesab2c2.Utiils.CommonUtils.INPUT_TIMEOUT;

public class NetworkClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    public static Retrofit getClient(String baseUrl, OkHttpClient client) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getPaymentOkhttpClient(String authToken) {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(INPUT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(FETCH_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor(authToken))
                .build();
        return okHttpClient;
    }

    public static OkHttpClient getAuthenticationOkhttpClient(String CONSUMER_KEY, String CONSUMER_SECRET) {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(INPUT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(FETCH_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AccessTokenInterceptor(CONSUMER_KEY, CONSUMER_SECRET))
                .build();
        return okHttpClient;
    }
}