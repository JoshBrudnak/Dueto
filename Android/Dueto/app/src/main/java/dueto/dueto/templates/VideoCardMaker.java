package dueto.dueto.templates;

import android.content.Context;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dueto.dueto.servercom.Server;

/**
 * Creates TableCell objects based on JSON from server
 */

public class VideoCardMaker
{
    public static TableCell[] getCards(String endpoint, Context context, Display display)
    {
        try {
            JSONObject input = Server.SERVER.request(endpoint, new JSONObject());
            if (input != null) {
                JSONArray videoCards = input.getJSONArray("VideoCards");
                int length = videoCards.length();
                TableCell[] out = new TableCell[length];

                for(int i = 0; i < length; i++)
                {
                    out[i] = new TableCell(context, display, (JSONObject) videoCards.get(i));
                }
            }
            else
                return null;
        }
        catch(JSONException exc)
        {
            exc.printStackTrace();
        }
        return null;
    }
}
