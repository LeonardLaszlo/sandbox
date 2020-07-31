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
        RegistrationResponseEvent event = new RegistrationResponseEvent();
        event.setCode(201);
        event.setUsername("user");
        event.setPassword("pass");
        event.setMessage("created");
        EventBus.getDefault().post(event);
    }
}
