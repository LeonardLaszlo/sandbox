package hu.bme.aut.android.mobilszoft.interactor.messages.event;

public class NewMessageResponseEvent {

    private int code;
    private String message;
    private String username;
    private String password;
    private Throwable throwable;

    //<editor-fold desc="Constructors|Getters|Setters">

    public NewMessageResponseEvent() {
    }

    public NewMessageResponseEvent(int code, String message, String username, String password, Throwable throwable) {
        this.code = code;
        this.username = username;
        this.message = message;
        this.password = password;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    //</editor-fold>
}
