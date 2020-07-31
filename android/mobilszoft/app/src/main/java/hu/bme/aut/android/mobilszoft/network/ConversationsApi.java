package hu.bme.aut.android.mobilszoft.network;

import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.ListOfConversations;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConversationsApi {
    @GET("conversations")
    Call<ListOfConversations> getConversations(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json"})
    @POST("conversations/{partner}")
    Call<ApiMessage> newConversation(@Header("Authorization") String authorization, @Path("partner") String partner);
}
