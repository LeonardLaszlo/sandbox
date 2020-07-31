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
        String credentials = username + ":" + password;
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        try {
            Call<ApiMessage> loginCall = loginApi.login(basic);
            Response<ApiMessage> response = loginCall.execute();
            event.setCode(response.code());
            event.setUsername(username);
            event.setPassword(password);
            if (response.body() != null) {
                event.setMessage(response.body().getMessage());
            }
            if (response.errorBody() != null) {
                event.setMessage(response.message());
            }
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            EventBus.getDefault().post(event);
        }
    }
}
