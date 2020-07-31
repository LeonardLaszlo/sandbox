package hu.bme.aut.android.mobilszoft.interactor.conversations.event;

import java.util.List;

public class GetConversationsResponseEvent {

    private int code;
    private List<String> conversations;
    private String message;
    private String username;
    private String password;
    private Throwable throwable;

    //<editor-fold desc="Constructors|Getters|Setters">

    public GetConversationsResponseEvent() {
    }

    public GetConversationsResponseEvent(int code, List<String> conversations, String message, String username, String password, Throwable throwable) {
        this.code = code;
        this.conversations = conversations;
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

    public List<String> getConversations() {
        return conversations;
    }

    public void setConversations(List<String> conversations) {
        this.conversations = conversations;
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
