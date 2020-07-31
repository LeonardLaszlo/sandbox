package hu.bme.aut.android.mobilszoft.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(NetworkConfig.ENDPOINT_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public LoginApi provideLoginApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

    @Provides
    @Singleton
    public RegistrationApi provideRegistrationApi(Retrofit retrofit) {
        return retrofit.create(RegistrationApi.class);
    }

    @Provides
    @Singleton
    public ConversationsApi provideConversationsApi(Retrofit retrofit) {
        return retrofit.create(ConversationsApi.class);
    }

    @Provides
    @Singleton
    public MessagesApi provideMessagesApi(Retrofit retrofit) {
        return retrofit.create(MessagesApi.class);
    }
}
