package ke.co.mobimech.mpesab2c2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import ke.co.mobimech.mpesab2c2.API.ApiUtils;
import ke.co.mobimech.mpesab2c2.API.Models.AccessToken;
import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentResponse;
import ke.co.mobimech.mpesab2c2.API.URLs;
import ke.co.mobimech.mpesab2c2.Utils.Enumerations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mpesa {

    private String BASE_URL;
    private String CONSUMER_KEY;
    private String CONSUMER_SECRET;
    @Nullable
    private AccessToken accessToken;

    private Mpesa(Enumerations environment, String CONSUMER_KEY, String CONSUMER_SECRET){
        this.CONSUMER_KEY = CONSUMER_KEY;
        this.CONSUMER_SECRET = CONSUMER_SECRET;
        this.BASE_URL = (environment == Enumerations.SANDBOX) ? URLs.SANDBOX_BASE_URL : URLs.PRODUCTION_BASE_URL;
    }
    public static Mpesa with(String consumerKey, String consumerSecret,  MpesaLib<AccessToken> MpesaLib) {
        return with(consumerKey, consumerSecret, Enumerations.SANDBOX, MpesaLib);
    }

    public static Mpesa with(String CONSUMER_KEY, String CONSUMER_SECRET, Enumerations env, MpesaLib<AccessToken> listener) {
        Mpesa mpesa = new Mpesa(env, CONSUMER_KEY, CONSUMER_SECRET);
        mpesa.auth(listener);
        return mpesa;
    }

    private void auth(final MpesaLib<AccessToken> listener) {

        ApiUtils.getAuthenticationAPI(CONSUMER_KEY, CONSUMER_SECRET, BASE_URL).getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    AccessToken accessToken = response.body();
                    if (accessToken != null) {
                        Mpesa.this.accessToken = accessToken;
                        listener.onResult(accessToken);
                        return;
                    }
                }
                listener.onError("Authentication Failed");
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                listener.onError("Authentication Failed: " + t.getLocalizedMessage());
            }
        });
    }

    public void B2CMpesaPayment(B2CPaymentRequest b2CPaymentRequest, final MpesaLib<B2CPaymentResponse> listener) {

        if (accessToken == null) {
            listener.onError("Not Authenticated");
            return;
        }
        B2CPaymentRequest request= new B2CPaymentRequest(
                b2CPaymentRequest.getInitiatorName(),
                b2CPaymentRequest.getSecurityCredential(),
                b2CPaymentRequest.getAmount(),
                b2CPaymentRequest.getCommandID(),
                b2CPaymentRequest.getRemarks(),
                b2CPaymentRequest.getPartyA(),
                b2CPaymentRequest.getPartyB(),
                b2CPaymentRequest.getQueueTimeOutURL(),
                b2CPaymentRequest.getResultURL(),
                b2CPaymentRequest.getOccassion()
        );

        ApiUtils.getPaymentAPI(BASE_URL, accessToken.getAccess_token()).makeB2CPayment(request).enqueue(new Callback<B2CPaymentResponse>() {
            @Override
            public void onResponse(@NonNull Call<B2CPaymentResponse> call, @NonNull Response<B2CPaymentResponse> response) {
                if (response.isSuccessful()) {
                    B2CPaymentResponse result = response.body();
                    if (result != null) {
                        listener.onResult(result);
                        return;
                    }
                }
                listener.onError("B2C payment Failed");
            }

            @Override
            public void onFailure(@NonNull Call<B2CPaymentResponse> call, @NonNull Throwable t) {
                listener.onError("B2C payment Failed: " + t.getLocalizedMessage());
            }
        });

    }


}
