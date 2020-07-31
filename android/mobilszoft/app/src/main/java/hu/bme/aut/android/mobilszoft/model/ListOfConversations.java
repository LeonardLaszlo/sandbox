package hu.bme.aut.android.mobilszoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOfConversations {

    @SerializedName("conversations")
    @Expose
    private List<String> conversations;

    public List<String> getConversations() {
        return conversations;
    }

    public void setConversations(List<String> conversations) {
        this.conversations = conversations;
    }
}
