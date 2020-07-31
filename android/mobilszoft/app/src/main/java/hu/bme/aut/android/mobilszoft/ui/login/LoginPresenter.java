package hu.bme.aut.android.mobilszoft.ui.login;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.di.Network;
import hu.bme.aut.android.mobilszoft.interactor.login.LoginInteractor;
import hu.bme.aut.android.mobilszoft.interactor.login.event.LoginResponseEvent;
import hu.bme.aut.android.mobilszoft.interactor.registration.RegistrationInteractor;
import hu.bme.aut.android.mobilszoft.interactor.registration.event.RegistrationResponseEvent;
import hu.bme.aut.android.mobilszoft.model.User;
import hu.bme.aut.android.mobilszoft.ui.Presenter;

import static hu.bme.aut.android.mobilszoft.MobilSzoftApplication.injector;

public class LoginPresenter extends Presenter<LoginScreen> {

    @Inject
    @Network
    Executor networkExecutor;
    @Inject
    RegistrationInteractor registrationInteractor;
    @Inject
    LoginInteractor loginInteractor;

    @Override
    public void attachScreen(LoginScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    public void showLoginResult(final String emailAddress, final String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loginInteractor.login(emailAddress, password);
            }
        });
    }

    public void showRegistrationResult(final String emailAddress, final String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setEmail(emailAddress);
                user.setPassword(password);
                registrationInteractor.register(user);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final LoginResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    /*Credential oldUserData = Credential.findById(Credential.class, 1);
                    if (oldUserData != null) {
                        oldUserData.delete();
                    }*/
                    //Credential credential = new Credential(event.getUsername(), (long) 1, event.getPassword());
                    //credential.save();
                    screen.doLogin(event.getUsername(), event.getPassword());
                } else {
                    screen.showResponse(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final RegistrationResponseEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 201) {
                    /*Credential oldCredential = Credential.findById(Credential.class, 1);
                    if (oldCredential != null) {
                        oldCredential.delete();
                    }
                    Credential credential = new Credential(event.getUsername(), (long) 1, event.getPassword());
                    credential.save();*/
                    screen.doLogin(event.getUsername(), event.getPassword());
                } else {
                    screen.showResponse(event.getCode() + " " + event.getMessage());
                }
            }
        }
    }
}
