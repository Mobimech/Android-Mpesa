package ke.co.mobimech.mpesab2c2.API;

import ke.co.mobimech.mpesab2c2.API.Models.AccessToken;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthAPI {

    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken();

}
