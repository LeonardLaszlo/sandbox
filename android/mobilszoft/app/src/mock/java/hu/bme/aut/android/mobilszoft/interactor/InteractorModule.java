package hu.bme.aut.android.mobilszoft.interactor;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.android.mobilszoft.interactor.conversations.ConversationsInteractor;
import hu.bme.aut.android.mobilszoft.interactor.login.LoginInteractor;
import hu.bme.aut.android.mobilszoft.interactor.messages.MessagesInteractor;
import hu.bme.aut.android.mobilszoft.interactor.registration.RegistrationInteractor;

@Module
public class InteractorModule {

    @Provides
    public LoginInteractor provideLoginInteractor() {
        return new LoginInteractor();
    }

    @Provides
    public RegistrationInteractor provideRegistrationInteractor() {
        return new RegistrationInteractor();
    }

    @Provides
    public ConversationsInteractor provideConversationsInteractor() {
        return new ConversationsInteractor();
    }

    @Provides
    public MessagesInteractor provideMessagesInteractor() {
        return new MessagesInteractor();
    }
}
