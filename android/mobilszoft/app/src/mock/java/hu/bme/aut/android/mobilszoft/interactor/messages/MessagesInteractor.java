package hu.bme.aut.android.mobilszoft.interactor.messages;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.GetMessagesResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.NewMessageResponseEvent;
import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.ListOfMessages;
import hu.bme.aut.android.mobilszoft.model.Message;
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
        event.setCode(200);
        event.setUsername("user");
        event.setPassword("pass");
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user2", "user", "Hello!"));
        messages.add(new Message("user3", "user", "Szia!"));
        event.setMessages(messages);
        EventBus.getDefault().post(event);
    }

    public void newMessage(String username, String password, NewMessage newMessage) {
        NewMessageResponseEvent event = new NewMessageResponseEvent();
        event.setCode(201);
        event.setUsername("user");
        event.setPassword("pass");
        event.setMessage("Created");
        EventBus.getDefault().post(event);
    }
}
