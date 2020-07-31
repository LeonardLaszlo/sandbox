package hu.bme.aut.android.mobilszoft.interactor.registration;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.interactor.registration.event.RegistrationResponseEvent;
import hu.bme.aut.android.mobilszoft.model.ApiMessage;
import hu.bme.aut.android.mobilszoft.model.User;
import hu.bme.aut.android.mobilszoft.network.RegistrationApi;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationInteractor {

    @Inject
    RegistrationApi registrationApi;

    public RegistrationInteractor() {
        MobilSzoftApplication.injector.inject(this);
    }

    public void register(User user) {
        Call<ApiMessage> registerCall = registrationApi.register(user);
        RegistrationResponseEvent event = new RegistrationResponseEvent();
        try {
            Response<ApiMessage> response = registerCall.execute();
            event.setCode(response.code());
            event.setUsername(user.getEmail());
            event.setPassword(user.getPassword());
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
