package ke.co.mobimech.mpesa.API.Interceptors;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class AuthInterceptor implements Interceptor {
    private String authToken;

    public AuthInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request().newBuilder()

                .addHeader("Authorization", "Bearer " + authToken)
                .build();

        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String message = buffer.readUtf8();
        Log.e("INTERCEPTOR", "THE BIG MESSAGE : " + message);

        return chain.proceed(request);
    }
}
