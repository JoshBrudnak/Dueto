package dueto.dueto.util;

import android.content.Context;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Stack;
import java.util.Calendar;

import dueto.dueto.templates.FriendlyMessageCell;
import dueto.dueto.templates.MessageOverviewCell;
import dueto.dueto.templates.Messenger;

/**
 * Created by ben on 28/03/18.
 */

public class MessagingHandler
{
    public static HashMap<MessageOverviewCell, Messenger> overviewToMessengerMap = new HashMap<>();

    public static Stack<MessageOverviewCell> makeMessagesStack(JSONObject json) throws JSONException
    {
        JSONArray jsonArray = json.getJSONArray("Messages");
        Stack<MessageOverviewCell> out = new Stack<>();


        for(int i = 0; i < jsonArray.length();i++)
        {

        }
        return  out;
    }

    public static FriendlyMessageCell sendMessage(Context context, Display display, String recipient, String message)
    {
       try
       {
           JSONObject toSend = new JSONObject();
           toSend.put("User", recipient);
           toSend.put("Message", message);
           toSend.put("Time", Calendar.getInstance().getTime().toString());

           FriendlyMessageCell out = new FriendlyMessageCell(context, display, toSend);

           System.out.println(out);
           System.out.println(message);
            //TODO: Send message to server

           return out;
       }
       catch(JSONException exc)
       {
           System.out.println("JSON ERROR IN MESSAGING_HANDLER.JAVA");
       }
       return null;
    }

}
