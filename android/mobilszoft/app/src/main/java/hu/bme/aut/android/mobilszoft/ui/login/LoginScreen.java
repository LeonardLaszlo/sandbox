package hu.bme.aut.android.mobilszoft.ui.login;

public interface LoginScreen {
    void showResponse(String message);

    void doLogin(String username, String password);

    void showNetworkError(String errorMsg);
}
