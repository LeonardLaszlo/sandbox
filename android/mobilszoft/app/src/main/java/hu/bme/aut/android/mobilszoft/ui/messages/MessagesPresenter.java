package hu.bme.aut.android.mobilszoft.ui.messages;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.di.Network;
import hu.bme.aut.android.mobilszoft.interactor.messages.MessagesInteractor;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.GetMessagesResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.messages.event.NewMessageResponseEvent;
import hu.bme.aut.android.mobilszoft.model.NewMessage;
import hu.bme.aut.android.mobilszoft.ui.Presenter;

import static hu.bme.aut.android.mobilszoft.MobilSzoftApplication.injector;

public class MessagesPresenter extends Presenter<MessagesScreen> {

    @Inject
    @Network
    Executor networkExecutor;
    @Inject
    MessagesInteractor messagesInteractor;

    @Override
    public void attachScreen(MessagesScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    public void getMessages(final String emailAddress, final String password, final String partner) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                messagesInteractor.getMessages(emailAddress, password, partner);
            }
        });
    }

    public void newMessage(final String emailAddress, final String password, final String partner, final String message) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NewMessage newMessage = new NewMessage();
                newMessage.setReceiver(partner);
                newMessage.setMessage(message);
                messagesInteractor.newMessage(emailAddress, password, newMessage);
            }
        });
    }

    public void refreshMessages() {
        screen.refreshMessages();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetMessagesResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showMessages(event.getMessages());
                } else {
                    screen.showNetworkError(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final NewMessageResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 201) {
                    screen.refreshMessages();
                } else {
                    screen.showNetworkError(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }
}
