package dueto.dueto.messageoverview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dueto.dueto.R;
import dueto.dueto.messaging.MessageListActivity;
import dueto.dueto.util.MessagingHandler;

/**
 * Created by ben on 11/04/18.
 */

public class MessageOverviewAdapter extends RecyclerView.Adapter
{
    private Context context;
    private List<MessageOverviewObject> messageOverviewObjectList;

    public MessageOverviewAdapter(Context context, List<MessageOverviewObject> messageOverviewObjectList)
    {
        this.context = context;
        this.messageOverviewObjectList = messageOverviewObjectList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MessageOverviewObject overviewObject = messageOverviewObjectList.get(position);
        ((MessageOverviewViewHolder) holder).bind(overviewObject);
    }

    @Override
    public int getItemCount() {
        return MessagingHandler.chats().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_overview_cell, parent, false);

        return new MessageOverviewViewHolder(view);
    }

    //INNER CLASSES

    private class MessageOverviewViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView avatar;

        MessageOverviewViewHolder(View itemView)
        {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.overview_name);
            avatar = (ImageView) itemView.findViewById(R.id.overview_avatar);
            itemView.setOnClickListener(e->
            {
                Intent intent = new Intent(context, MessageListActivity.class);
                context.startActivity(intent);
            });
        }

        void bind(MessageOverviewObject overviewObject)
        {
            name.setText(overviewObject.getName());
            avatar.setImageBitmap(overviewObject.getAvatar());
        }

    }

}
