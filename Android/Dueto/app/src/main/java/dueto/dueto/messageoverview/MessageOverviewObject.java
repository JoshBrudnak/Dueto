package dueto.dueto.messageoverview;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dueto.dueto.messaging.Message;
import dueto.dueto.servercom.Server;
import dueto.dueto.util.MessagingHandler;

/**
 * Holds the information for overview cells in the messaging overview recycler
 */

public class MessageOverviewObject
{
    private String user, chatpartner;
    private Bitmap avatar;

    public MessageOverviewObject(String user)
    {
        this.user = user;
        avatar = Server.SERVER.getImage("avatar", user);
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("artist", user);
            JSONObject userProfile = Server.SERVER.request("artist",parameters);
            chatpartner = userProfile.getString("Name");
        }catch(JSONException exc)
        {
            System.out.println("--------------------------------");
            System.out.println("Exception in Server.URLParamRequest.URLParamRequest(): ");
            System.out.println(exc);
            System.out.println("--------------------------------");
        }

        if(!MessagingHandler.chats().containsKey(user))
            MessagingHandler.chats().put(user, new ArrayList<Message>());
    }


    public String getName()
    {
        return chatpartner;
    }

    public Bitmap getAvatar()
    {
        return avatar;
    }

    public String getUser()
    {
        return user;
    }

}
