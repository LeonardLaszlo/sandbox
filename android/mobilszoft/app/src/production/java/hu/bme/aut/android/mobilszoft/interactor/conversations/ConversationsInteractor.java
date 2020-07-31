package hu.bme.aut.android.mobilszoft.interactor.conversations;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.interactor.conversations.event.GetConversationsResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.conversations.event.NewConversationResponseEvent;
import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.ListOfConversations;
import hu.bme.aut.android.mobilszoft.network.ConversationsApi;
import retrofit2.Call;
import retrofit2.Response;

public class ConversationsInteractor {

    @Inject
    ConversationsApi conversationsApi;

    public ConversationsInteractor() {
        MobilSzoftApplication.injector.inject(this);
    }

    public void getConversations(String username, String password) {
        GetConversationsResponseEvent event = new GetConversationsResponseEvent();
        String credentials = username + ":" + password;
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        try {
            Call<ListOfConversations> getConversationsCall = conversationsApi.getConversations(basic);
            Response<ListOfConversations> response = getConversationsCall.execute();
            event.setCode(response.code());
            event.setUsername(username);
            event.setPassword(password);
            if (response.body() != null) {
                event.setConversations(response.body().getConversations());
            }
            if (response.errorBody() != null) {
                event.setMessage(response.message());
            }
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            EventBus.getDefault().post(event);
        }
    }

    public void newConversation(String username, String password, String partner) {
        NewConversationResponseEvent event = new NewConversationResponseEvent();
        String credentials = username + ":" + password;
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        try {
            Call<ApiMessage> newConversationCall = conversationsApi.newConversation(basic, partner);
            Response<ApiMessage> response = newConversationCall.execute();
            event.setCode(response.code());
            event.setUsername(username);
            event.setPassword(password);
            if (response.body() != null) {
                event.setMessage(response.body().getMessage());
            }
            if (response.errorBody() != null) {
                event.setMessage(response.message());
            }
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            EventBus.getDefault().post(event);
        }
    }
}
