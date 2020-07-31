package hu.bme.aut.android.mobilszoft.orm;

import com.orm.SugarRecord;

public class Credential extends SugarRecord {
    private String email;
    private String password;

    // Default constructor is necessary for SugarRecord
    public Credential() {

    }

    public Credential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
