package hu.bme.aut.android.mobilszoft.interactor.conversations;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
        event.setCode(200);
        event.setUsername("user");
        event.setPassword("pass");
        List<String> conversations = new ArrayList<>();
        conversations.add("user2");
        conversations.add("user3");
        event.setConversations(conversations);
        EventBus.getDefault().post(event);
    }

    public void newConversation(String username, String password, String partner) {
        NewConversationResponseEvent event = new NewConversationResponseEvent();
        event.setCode(201);
        event.setUsername("user");
        event.setPassword("pass");
        event.setMessage("Created");
        EventBus.getDefault().post(event);
    }
}
