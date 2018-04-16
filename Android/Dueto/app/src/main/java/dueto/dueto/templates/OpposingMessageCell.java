package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import org.json.JSONObject;

/**
 * Created by ben on 28/03/18.
 */

public class OpposingMessageCell extends MessageCell
{
    public OpposingMessageCell(Context context, Display display, JSONObject jsonMsg)
    {
        super(context, display, jsonMsg);
        //Configuring linearLayout for friendly messages
        this.linearLayout.setGravity(Gravity.START);
        this.linearLayout.setBackgroundColor(Color.WHITE);

        //Configuring messageView for friendly messages
        this.messageView.setTextColor(Color.BLACK);

        //Configuring timeView for friendly messages
        this.timeView.setGravity(Gravity.START);
        this.timeView.setTextColor(Color.BLACK);
    }
}
