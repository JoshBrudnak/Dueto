package dueto.dueto.messaging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */

public class Message
{
    private String message, time;
    private int type;

    public static final int SENT = 0, RECEIVED = 1;

    public Message(JSONObject jsonMessage, int type)
    {
        this.type = type;
        try {
            message = jsonMessage.getString("Message");
            time = jsonMessage.getString("Time");
        }catch (JSONException j)
        {
            message = "Message cannot be retrieved at the moment";
            time = "N/A";
        }
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public int getType() {
        return type;
    }
}
