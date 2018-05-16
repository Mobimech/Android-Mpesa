package ke.co.mobimech.mpesa.API;

import ke.co.mobimech.mpesa.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesa.API.Models.B2CPaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("Mpesa/b2c/v1/paymentrequest")
    Call<B2CPaymentResponse> makeB2CPayment(@Body B2CPaymentRequest b2CPaymentRequest);
}
