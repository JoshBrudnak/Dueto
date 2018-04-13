package dueto.dueto.messaging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimerTask;

import dueto.dueto.servercom.Server;

/**
 * Created by ben on 09/04/18.
 */

public class MessageRetriever extends TimerTask
{
    @Override
    public void run() {

        try {
            JSONArray jsonArray = new JSONArray(new Server().request("getmessages", null).toString());



        }
        catch(JSONException exc)
        {
            System.out.println("--------------------------------");
            System.out.println("Exception in MessageRetriever.run(): ");
            System.out.println(exc);
            System.out.println("--------------------------------");
        }


    }
}
