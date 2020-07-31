package hu.bme.aut.android.mobilszoft.orm;

public interface UserPersistanceApi {
    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);
}
