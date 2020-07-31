package hu.bme.aut.android.mobilszoft.interactor.messages;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.GetMessagesResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.NewMessageResponseEvent;
import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.ListOfMessages;
import hu.bme.aut.android.mobilszoft.model.NewMessage;
import hu.bme.aut.android.mobilszoft.network.MessagesApi;
import retrofit2.Call;
import retrofit2.Response;

public class MessagesInteractor {

    @Inject
    MessagesApi messagesApi;

    public MessagesInteractor() {
        MobilSzoftApplication.injector.inject(this);
    }

    public void getMessages(String username, String password, String partner) {
        GetMessagesResponseEvent event = new GetMessagesResponseEvent();
        String credentials = username + ":" + password;
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        try {
            Call<ListOfMessages> getMessagesCall = messagesApi.getMessages(basic, partner);
            Response<ListOfMessages> response = getMessagesCall.execute();
            event.setCode(response.code());
            event.setUsername(username);
            event.setPassword(password);
            if (response.body() != null) {
                event.setMessages(response.body().getMessages());
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

    public void newMessage(String username, String password, NewMessage newMessage) {
        NewMessageResponseEvent event = new NewMessageResponseEvent();
        String credentials = username + ":" + password;
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        try {
            Call<ApiMessage> newMessageCall = messagesApi.newMessage(basic, newMessage);
            Response<ApiMessage> response = newMessageCall.execute();
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
