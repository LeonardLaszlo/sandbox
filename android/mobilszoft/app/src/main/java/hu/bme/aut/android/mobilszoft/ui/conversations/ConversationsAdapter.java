package hu.bme.aut.android.mobilszoft.ui.conversations;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hu.bme.aut.android.mobilszoft.R;
import hu.bme.aut.android.mobilszoft.ui.messages.MessagesActivity;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {

    private static Context context;
    private static String username;
    private static String password;
    private List<String> conversationsList;

    public ConversationsAdapter(Context context, List<String> conversationsList) {
        this.context = context;
        this.conversationsList = conversationsList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_conversation, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String conversation = conversationsList.get(position);
        holder.tvConversation.setText(conversation);
    }

    @Override
    public int getItemCount() {
        return conversationsList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvConversation;
        public LinearLayout llConversation;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvConversation = (TextView) itemView.findViewById(R.id.tvConversation);
            llConversation = (LinearLayout) itemView.findViewById(R.id.llConversation);
            llConversation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessagesActivity.class);
                    intent.putExtra(ConversationsFragment.USERNAME, ConversationsFragment.username);
                    intent.putExtra(ConversationsFragment.PASSWORD, ConversationsFragment.password);
                    intent.putExtra(ConversationsFragment.PARTNER, tvConversation.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
