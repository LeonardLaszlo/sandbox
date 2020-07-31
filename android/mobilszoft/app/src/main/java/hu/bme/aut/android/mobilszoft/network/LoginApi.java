package hu.bme.aut.android.mobilszoft.network;

import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LoginApi {
    @GET("login")
    Call<ApiMessage> login(@Header("Authorization") String authorization);
}
