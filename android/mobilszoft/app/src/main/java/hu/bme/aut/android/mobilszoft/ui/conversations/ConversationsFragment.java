package hu.bme.aut.android.mobilszoft.ui.conversations;

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
import hu.bme.aut.android.mobilszoft.ui.login.LoginActivity;

public class ConversationsFragment extends Fragment implements ConversationsScreen {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PARTNER = "PARTNER";
    public static String username;
    public static String password;

    @Inject
    ConversationsPresenter conversationsPresenter;

    private EditText etNewConversation;
    private Button btnNewConversation;
    private TextView tvConversationsNetworkError;
    private RecyclerView recyclerViewConversations;
    private ConversationsAdapter conversationsAdapter;
    private SwipeRefreshLayout swipeRefreshLayoutConversations;
    private List<String> listOfConversations;

    public ConversationsFragment() {
        MobilSzoftApplication.injector.inject(this);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        username = getActivity().getIntent().getStringExtra(LoginActivity.USERNAME);
        password = getActivity().getIntent().getStringExtra(LoginActivity.PASSWORD);
        conversationsPresenter.attachScreen(this);
    }

    @Override
    public void onDetach() {
        conversationsPresenter.detachScreen();
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        tvConversationsNetworkError = (TextView) view.findViewById(R.id.tvConversationsNetworkError);
        etNewConversation = (EditText) view.findViewById(R.id.etNewConversation);
        btnNewConversation = (Button) view.findViewById(R.id.btnNewConversation);
        btnNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationsPresenter.newConversation(username, password, etNewConversation.getText().toString());
            }
        });
        recyclerViewConversations = (RecyclerView) view.findViewById(R.id.recyclerViewConversations);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewConversations.setLayoutManager(llm);

        listOfConversations = new ArrayList<>();

        conversationsAdapter = new ConversationsAdapter(getContext(), listOfConversations);
        recyclerViewConversations.setAdapter(conversationsAdapter);

        swipeRefreshLayoutConversations = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutConversations);

        swipeRefreshLayoutConversations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutConversations.setRefreshing(false);
                conversationsPresenter.refreshConversations();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        conversationsPresenter.getConversations(username, password);
    }

    @Override
    public void showConversations(List<String> conversations) {
        listOfConversations.clear();
        for (String conversation : conversations) {
            listOfConversations.add(conversation);
        }
        conversationsAdapter = new ConversationsAdapter(getContext(), listOfConversations);
        recyclerViewConversations.setAdapter(conversationsAdapter);
    }

    @Override
    public void refreshConversations() {
        conversationsPresenter.getConversations(username, password);
    }

    @Override
    public void showNetworkError(String errorMsg) {
        tvConversationsNetworkError.setText(errorMsg);
    }
}
