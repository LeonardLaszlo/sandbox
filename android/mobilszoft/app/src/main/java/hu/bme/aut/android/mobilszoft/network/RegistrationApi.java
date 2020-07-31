package hu.bme.aut.android.mobilszoft.network;

import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationApi {
    @POST("register")
    Call<ApiMessage> register(@Body User user);
}
