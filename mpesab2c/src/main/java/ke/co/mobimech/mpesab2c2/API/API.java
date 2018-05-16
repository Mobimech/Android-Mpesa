package ke.co.mobimech.mpesab2c2.API;

import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("Mpesa/b2c/v1/paymentrequest")
    Call<B2CPaymentResponse> makeB2CPayment(@Body B2CPaymentRequest b2CPaymentRequest);
}
