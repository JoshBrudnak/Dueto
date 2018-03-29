package dueto.dueto.templates;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ben on 28/03/18.
 */

class MessageCell extends TableRow {

    private int width, height, maxWidth;
    protected TextView messageView, timeView;
    protected LinearLayout linearLayout;

    public MessageCell(Context context)
    {
        super(context);
    }

    public MessageCell(Context context, Display display, JSONObject jsonMsg)
    {
        super(context);

        //Getting width and height of the display
        DisplayMetrics dms = new DisplayMetrics();
        display.getMetrics(dms);
        height = dms.heightPixels;
        width = dms.widthPixels;
        maxWidth = (width/2) - 20;

        //Getting strings representing the messages
        String message, time;

        try {
            message = jsonMsg.getString("Message");
            time = jsonMsg.getString("Time");
        }
        catch(JSONException jsonexc)
        {
            message = "Not available";
            time = "mm/dd/yy @ --:--";
        }

        //Initialising the TextView
        messageView = new TextView(context);
        //Making it wrap text
        messageView.setSingleLine(false);
        //Setting maximum width
        messageView.setMaxWidth(maxWidth);
        //Adding padding on the sides and below
        messageView.setPadding(5,0,5,5);
        //Aligning in parent
        messageView.setGravity(Gravity.CENTER_HORIZONTAL);
        //Setting text
        messageView.setText(message);

        //Initialising the TimeView
        timeView = new TextView(context);
        //No text wrapping
        timeView.setSingleLine(true);
        //Adding padding only to the left and the right
        timeView.setPadding(5,0,5,0);
        //Setting smaller text size
        timeView.setTextSize(10);
        //Set text
        timeView.setText(time);

        //Initialising linearLayout
        linearLayout = new LinearLayout(context);
        //Setting orientation
        linearLayout.setOrientation(VERTICAL);
        //Setting layout parameters to wrap content
        linearLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT));
        //Adding views
        linearLayout.addView(messageView);
        linearLayout.addView(timeView);
    }

}
