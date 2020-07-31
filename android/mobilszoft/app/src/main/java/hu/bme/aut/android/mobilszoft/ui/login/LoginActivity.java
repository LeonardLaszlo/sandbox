package hu.bme.aut.android.mobilszoft.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.R;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsActivity;

public class LoginActivity extends AppCompatActivity implements LoginScreen {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    @Inject
    LoginPresenter loginPresenter;

    private EditText etEmail;
    private EditText etPassword;
    private TextView tvResult;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MobilSzoftApplication.injector.inject(this);


        MobilSzoftApplication application = (MobilSzoftApplication) getApplication();
        mTracker = application.getDefaultTracker();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvResult = (TextView) findViewById(R.id.tvAuthenticationResult);

        Button btnLoginUser = (Button) findViewById(R.id.btnLoginUser);
        btnLoginUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginPresenter.showLoginResult(etEmail.getText().toString(), etPassword.getText().toString());
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Login")
                        .build());
            }
        });
        Button btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.showRegistrationResult(etEmail.getText().toString(), etPassword.getText().toString());
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Register")
                        .build());
            }
        });


        mTracker = application.getDefaultTracker();

        Log.i("Info", "Setting screen name: Login/Register");
        mTracker.setScreenName("App started");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.detachScreen();
    }

    @Override
    public void showResponse(String message) {
        tvResult.setText(message);
    }

    @Override
    public void doLogin(String username, String password) {
        Intent intent = new Intent(LoginActivity.this, ConversationsActivity.class);
        intent.putExtra(USERNAME, username);
        intent.putExtra(PASSWORD, password);
        startActivity(intent);
    }

    @Override
    public void showNetworkError(String errorMessage) {
        System.err.println(errorMessage);
        tvResult.setText(errorMessage);
    }
}
