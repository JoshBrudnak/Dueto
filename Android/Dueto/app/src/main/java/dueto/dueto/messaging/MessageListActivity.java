package dueto.dueto.messaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import dueto.dueto.R;
import dueto.dueto.servercom.Server;
import dueto.dueto.util.MessagingHandler;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private Button sendButton;

    private Server messageSender = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);

        List<Message> mMessageList = MessagingHandler.getCurrentChat();
        mMessageRecycler = (RecyclerView) findViewById(R.id.layout_chat);
        mMessageAdapter = new MessageAdapter(this, mMessageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);


        sendButton = (Button) findViewById(R.id.button_chatbox_send);

        sendButton.setOnClickListener(e->
                {
                    EditText editText = (EditText) findViewById(R.id.edittext_chatbox);
                    String message = editText.getText().toString();
                    if(message == "")
                    {
                        return;
                    }
                    try {
                        JSONObject jsonMessage = new JSONObject();
                        jsonMessage.put("Artist", MessagingHandler.getCurrentMessagedUser());
                        jsonMessage.put("Message", message);

                        //jsonMessage.put("Time",messageSender.request("postmessages", jsonMessage).getString("Time"));
                        messageSender.request("postmessages", jsonMessage);
                        jsonMessage.put("Time", "now");

                        mMessageList.add(new Message(jsonMessage, Message.SENT));
                        mMessageAdapter.notifyDataSetChanged();
                        editText.setText("");

                    } catch (JSONException js)
                    {
                        System.out.println(js.getMessage());
                    }

                }
        );

    }
}