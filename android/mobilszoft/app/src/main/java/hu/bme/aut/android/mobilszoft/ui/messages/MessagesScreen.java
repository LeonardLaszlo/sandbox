package hu.bme.aut.android.mobilszoft.ui.messages;

import java.util.List;

import hu.bme.aut.android.mobilszoft.model.Message;

public interface MessagesScreen {

    void showMessages(List<Message> messages);

    void refreshMessages();

    void showNetworkError(String errorMsg);
}
