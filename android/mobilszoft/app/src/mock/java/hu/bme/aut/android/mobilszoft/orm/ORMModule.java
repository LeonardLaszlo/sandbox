package hu.bme.aut.android.mobilszoft.orm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ORMModule {
    @Provides
    @Singleton
    public UserPersistanceApi provideCredentialApi() {
        return new CredentialMock();
    }
}
