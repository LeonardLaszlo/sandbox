package hu.bme.aut.android.mobilszoft.orm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ORMModule {
    @Provides
    @Singleton
    public UserPersistanceApi provideUserPersistanceApi() {
        return new CredentialHandler();
    }
}
