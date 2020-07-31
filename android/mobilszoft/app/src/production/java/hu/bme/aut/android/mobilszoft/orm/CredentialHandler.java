package hu.bme.aut.android.mobilszoft.orm;

import java.util.List;

public class CredentialHandler implements UserPersistanceApi {

    private Credential userCredential;

    CredentialHandler() {
        List<Credential> storedCredentials = Credential.listAll(Credential.class);

        if (!storedCredentials.isEmpty()) {
            if (storedCredentials.size() > 1) {
                Credential.deleteAll(Credential.class);
                userCredential = new Credential();
            } else {
                userCredential = storedCredentials.get(0);
            }
        }
    }

    @Override
    public String getEmail() {
        return userCredential.getEmail();
    }

    @Override
    public void setEmail(String email) {
        userCredential.setEmail(email);
        userCredential.save();
    }

    @Override
    public String getPassword() {
        return userCredential.getPassword();
    }

    @Override
    public void setPassword(String password) {
        userCredential.setPassword(password);
        userCredential.save();
    }
}
