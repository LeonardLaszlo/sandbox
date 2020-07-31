package hu.bme.aut.android.mobilszoft.ui.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.android.mobilszoft.MobilSzoftApplication;
import hu.bme.aut.android.mobilszoft.R;
import hu.bme.aut.android.mobilszoft.model.Message;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsActivity;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsFragment;
import hu.bme.aut.android.mobilszoft.ui.login.LoginActivity;

public class MessagesFragment extends Fragment implements MessagesScreen {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PARTNER = "PARTNER";

    @Inject
    MessagesPresenter messagesPresenter;

    private EditText etNewMessage;
    private Button btnNewMessage;
    private TextView tvMessagesNetworkError;
    private RecyclerView recyclerViewMessages;
    private MessagesAdapter messagesAdapter;
    private SwipeRefreshLayout swipeRefreshLayoutMessages;
    private String username;
    private String password;
    private String partner;
    private List<Message> listOfMessages;

    public MessagesFragment() {
        MobilSzoftApplication.injector.inject(this);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        username = getActivity().getIntent().getStringExtra(ConversationsFragment.USERNAME);
        password = getActivity().getIntent().getStringExtra(ConversationsFragment.PASSWORD);
        partner = getActivity().getIntent().getStringExtra(ConversationsFragment.PARTNER);
        messagesPresenter.attachScreen(this);
    }

    @Override
    public void onDetach() {
        messagesPresenter.detachScreen();
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        tvMessagesNetworkError = (TextView) view.findViewById(R.id.tvMessagesNetworkError);
        etNewMessage = (EditText) view.findViewById(R.id.etNewMessage);
        btnNewMessage = (Button) view.findViewById(R.id.btnNewMessage);
        btnNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagesPresenter.newMessage(username, password, partner, etNewMessage.getText().toString());
            }
        });
        recyclerViewMessages = (RecyclerView) view.findViewById(R.id.recyclerViewMessages);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMessages.setLayoutManager(llm);

        listOfMessages = new ArrayList<>();

        messagesAdapter = new MessagesAdapter(getContext(), listOfMessages);
        recyclerViewMessages.setAdapter(messagesAdapter);

        swipeRefreshLayoutMessages = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMessages);

        swipeRefreshLayoutMessages.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutMessages.setRefreshing(false);
                messagesPresenter.refreshMessages();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        messagesPresenter.getMessages(username, password, partner);
    }

    @Override
    public void showMessages(List<Message> messages) {
        listOfMessages.clear();
        for (Message message : messages) {
            listOfMessages.add(message);
        }
        messagesAdapter = new MessagesAdapter(getContext(), listOfMessages);
        recyclerViewMessages.setAdapter(messagesAdapter);
    }

    @Override
    public void refreshMessages() {
        messagesPresenter.getMessages(username, password, partner);
    }

    @Override
    public void showNetworkError(String errorMsg) {
        tvMessagesNetworkError.setText(errorMsg);
    }
}
