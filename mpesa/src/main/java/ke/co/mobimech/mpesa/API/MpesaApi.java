package ke.co.mobimech.mpesa.API;

import ke.co.mobimech.mpesa.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesa.API.Models.B2CPaymentResponse;
import ke.co.mobimech.mpesa.API.Models.C2BPaymentRequest;
import ke.co.mobimech.mpesa.API.Models.C2BPaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MpesaApi {
    @POST("mpesa/b2c/v1/paymentrequest")
    Call<B2CPaymentResponse> makeB2CPayment(@Body B2CPaymentRequest b2CPaymentRequest);

    @POST("mpesa/stkpush/v1/processrequest")
    Call<C2BPaymentResponse> makeC2BPayment(@Body C2BPaymentRequest c2BPaymentRequest);
}
