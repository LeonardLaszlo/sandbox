package hu.bme.aut.android.mobilszoft.ui.conversations;

import java.util.List;

public interface ConversationsScreen {

    void showConversations(List<String> conversations);

    void refreshConversations();

    void showNetworkError(String errorMsg);
}
