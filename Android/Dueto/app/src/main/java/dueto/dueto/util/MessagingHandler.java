package dueto.dueto.util;

import android.content.Context;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dueto.dueto.messageoverview.MessageOverviewAdapter;
import dueto.dueto.messageoverview.MessageOverviewObject;
import dueto.dueto.messaging.Message;
import dueto.dueto.templates.FriendlyMessageCell;

/**
 *
 */

public class MessagingHandler {

    private static HashMap<String, List<Message>> userNameToChatMap = new HashMap<>();
    private static String currentMessagedUser = null;
    public static ArrayList<MessageOverviewObject> messageOverviewObjectArrayList = new ArrayList<>();
    public static MessageOverviewAdapter currentMessageOverviewAdapter = null;
//    public static HashMap<MessageOverviewCell, Messenger> overviewToMessengerMap = new HashMap<>();

//    public static Stack<MessageOverviewCell> makeMessagesStack(JSONObject json) throws JSONException {
//        JSONArray jsonArray = json.getJSONArray("Messages");
//        Stack<MessageOverviewCell> out = new Stack<>();
//
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//
//        }
//        return out;
//    }

    public static FriendlyMessageCell sendMessage(Context context, Display display, String recipient, String message) {
        try {


            JSONObject toSend = new JSONObject();
            toSend.put("User", recipient);
            toSend.put("Message", message);
            toSend.put("Time", Calendar.getInstance().getTime().toString());

            FriendlyMessageCell out = new FriendlyMessageCell(context, display, toSend);

            System.out.println(out);
            System.out.println(message);
            //TODO: Send message to server

            return out;
        } catch (JSONException exc) {
            System.out.println("JSON ERROR IN MESSAGING_HANDLER.JAVA");
        }
        return null;
    }

    public static List<Message> loadMessages(String user) {
        ArrayList<Message> out = new ArrayList<>();
        try {

        } catch (Exception e) {
            System.out.println("lol exception");
        }
        return out;
    }

    public static List<Message> getMessageListFromJsonArray(JSONArray jsonArray, String user) {
        ArrayList<Message> out = new ArrayList<>();
        try {
            final int LENGTH = jsonArray.length();
            int typeOfMessage;

            for (int i = 0; i < LENGTH; i++) {
                JSONObject tempJson = jsonArray.getJSONObject(i);

                if (tempJson.getString("Artist").equals(user))
                    typeOfMessage = Message.RECEIVED;
                else
                    typeOfMessage = Message.SENT;

                out.add(new Message(tempJson, typeOfMessage));
            }
        } catch (JSONException exc) {
            System.out.println("-----------------------------------");
            System.out.println("Exception in MessagingHandler.getMessageListFromJsonArray: ");
            System.out.println(exc.getMessage());
            System.out.println("-----------------------------------");
        }
        return out;
    }

    public static HashMap<String, List<Message>> chats()
    {
        return userNameToChatMap;
    }

    public static List<Message> getChat(String user)
    {
        return userNameToChatMap.get(user);
    }

    public static void addMessage(String user, Message message)
    {
        userNameToChatMap.get(user).add(message);
    }

    public static List<Message> getCurrentChat()
    {
        return userNameToChatMap.get(currentMessagedUser);
    }

    public static void setCurrentMessagedUser(String user)
    {
        currentMessagedUser = user;
    }

    public static String getCurrentMessagedUser()
    {
        return currentMessagedUser;
    }

    public static MessageOverviewAdapter getCurrentMessageOverviewAdapter()
    {
        return currentMessageOverviewAdapter;
    }

    public static void setCurrentMessageOverviewAdapter(MessageOverviewAdapter currentMessageOverviewAdapter) {
        MessagingHandler.currentMessageOverviewAdapter = currentMessageOverviewAdapter;
    }
}
