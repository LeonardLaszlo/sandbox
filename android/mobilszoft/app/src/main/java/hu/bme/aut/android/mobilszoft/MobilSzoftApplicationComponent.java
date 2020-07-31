package hu.bme.aut.android.mobilszoft;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.android.mobilszoft.interactor.InteractorModule;
import hu.bme.aut.android.mobilszoft.interactor.conversations.ConversationsInteractor;
import hu.bme.aut.android.mobilszoft.interactor.login.LoginInteractor;
import hu.bme.aut.android.mobilszoft.interactor.messages.MessagesInteractor;
import hu.bme.aut.android.mobilszoft.interactor.registration.RegistrationInteractor;
import hu.bme.aut.android.mobilszoft.network.NetworkModule;
import hu.bme.aut.android.mobilszoft.ui.UIModule;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsActivity;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsFragment;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsPresenter;
import hu.bme.aut.android.mobilszoft.ui.login.LoginActivity;
import hu.bme.aut.android.mobilszoft.ui.login.LoginPresenter;
import hu.bme.aut.android.mobilszoft.ui.messages.MessagesActivity;
import hu.bme.aut.android.mobilszoft.ui.messages.MessagesFragment;
import hu.bme.aut.android.mobilszoft.ui.messages.MessagesPresenter;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class, InteractorModule.class})
public interface MobilSzoftApplicationComponent {
    // Activities
    void inject(LoginActivity loginActivity);

    void inject(ConversationsActivity conversationsActivity);

    void inject(MessagesActivity messagesActivity);

    // Interactors
    void inject(LoginInteractor loginInteractor);

    void inject(RegistrationInteractor registrationInteractor);

    void inject(ConversationsInteractor conversationsInteractor);

    void inject(MessagesInteractor messagesInteractor);

    // Presenters
    void inject(LoginPresenter loginPresenter);

    void inject(ConversationsPresenter conversationsPresenter);

    void inject(MessagesPresenter messagesPresenter);

    // Fragments
    void inject(ConversationsFragment conversationsFragment);

    void inject(MessagesFragment messagesFragment);
}
