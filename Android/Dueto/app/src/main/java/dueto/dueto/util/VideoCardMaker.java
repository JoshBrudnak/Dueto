package dueto.dueto.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dueto.dueto.servercom.Server;
import dueto.dueto.templates.TableCell;

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
            else {
               // Utility.getMan().makeToast("An error occurred");
                return notAvailable(context);
            }
        }
        catch(JSONException exc)
        {
            //Utility.getMan().makeToast("An error occurred");
            return notAvailable(context);
        }
        return null;
    }

    private static TableCell[] notAvailable(Context context)
    {
        TableCell out = new TableCell(context);
        String notAv = "Videos currently not available";
        TextView text = new TextView(context);

        text.setText(notAv);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        out.setBackgroundColor(Color.LTGRAY);
        out.addView(text);

        TableCell[] arr = {out};

        return arr;
    }


}
