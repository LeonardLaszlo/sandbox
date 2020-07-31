package hu.bme.aut.android.mobilszoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewMessage {

    @SerializedName("receiver")
    @Expose
    private String receiver;

    @SerializedName("message")
    @Expose
    private String message;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
