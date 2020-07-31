package hu.bme.aut.android.mobilszoft.network;

import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.ListOfMessages;
import hu.bme.aut.android.mobilszoft.model.NewMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessagesApi {
    @GET("messages/{partner}")
    Call<ListOfMessages> getMessages(@Header("Authorization") String authorization, @Path("partner") String partner);

    @POST("messages")
    Call<ApiMessage> newMessage(@Header("Authorization") String authorization, @Body NewMessage newMessage);
}
