package dueto.dueto.messaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dueto.dueto.R;

public class MessageAdapter extends RecyclerView.Adapter
{
    private Context mContext;
    private List<Message> mMessageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        return mMessageList.get(position).getType();
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == Message.SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friendlybubble, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == Message.RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.meanbubble, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case Message.SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case Message.RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    //INNER CLASSES

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        //ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTime());
        }
    }
    
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTime());
        }
    }

}
