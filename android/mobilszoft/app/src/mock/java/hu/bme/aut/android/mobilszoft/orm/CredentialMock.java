package hu.bme.aut.android.mobilszoft.orm;

public class CredentialMock implements UserPersistanceApi{
    private String email = "email";
    private String password = "password";

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
