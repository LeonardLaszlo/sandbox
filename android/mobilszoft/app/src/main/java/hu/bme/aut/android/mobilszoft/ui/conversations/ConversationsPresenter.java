package hu.bme.aut.android.mobilszoft.ui.conversations;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.di.Network;
import hu.bme.aut.android.mobilszoft.interactor.conversations.ConversationsInteractor;
import hu.bme.aut.android.mobilszoft.interactor.conversations.event.GetConversationsResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.conversations.event.NewConversationResponseEvent;
import hu.bme.aut.android.mobilszoft.ui.Presenter;

import static hu.bme.aut.android.mobilszoft.MobilSzoftApplication.injector;

public class ConversationsPresenter extends Presenter<ConversationsScreen> {

    @Inject
    @Network
    Executor networkExecutor;
    @Inject
    ConversationsInteractor conversationsInteractor;

    @Override
    public void attachScreen(ConversationsScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    public void getConversations(final String emailAddress, final String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                conversationsInteractor.getConversations(emailAddress, password);
            }
        });
    }

    public void newConversation(final String emailAddress, final String password, final String partner) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                conversationsInteractor.newConversation(emailAddress, password, partner);
            }
        });
    }

    public void refreshConversations() {
        screen.refreshConversations();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetConversationsResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showConversations(event.getConversations());
                } else {
                    screen.showNetworkError(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final NewConversationResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 201) {
                    screen.refreshConversations();
                } else {
                    screen.showNetworkError(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }
}
