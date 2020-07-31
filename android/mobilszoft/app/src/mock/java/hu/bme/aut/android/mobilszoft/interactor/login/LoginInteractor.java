package hu.bme.aut.android.mobilszoft.interactor.login;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.interactor.login.event.LoginResponseEvent;
import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.network.LoginApi;
import retrofit2.Call;
import retrofit2.Response;

public class LoginInteractor {

    @Inject
    LoginApi loginApi;

    public LoginInteractor() {
        MobilSzoftApplication.injector.inject(this);
    }

    public void login(String username, String password) {
        LoginResponseEvent event = new LoginResponseEvent();
        event.setCode(200);
        event.setUsername("user");
        event.setPassword("pass");
        event.setMessage("OK");
        EventBus.getDefault().post(event);
    }
}
