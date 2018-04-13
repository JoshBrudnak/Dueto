package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import org.json.JSONObject;

import dueto.dueto.R;

/**
 * Used to create friendly (send) messageCells
 */

public class FriendlyMessageCell extends MessageCell
{
    public FriendlyMessageCell(Context context, Display display, JSONObject jsonMsg)
    {
        super(context, display, jsonMsg);

//        TableCell.LayoutParams params = new TableCell.LayoutParams();
//        params.weight = 1.0f;
//        params.gravity = Gravity.END;
//        this.setLayoutParams(params);

        //Configuring linearLayout for friendly messages
        this.linearLayout.setGravity(Gravity.END);
        this.linearLayout.setBackgroundColor(Color.BLUE);

        //Configuring messageView for friendly messages
        this.messageView.setTextColor(Color.WHITE);

        //Configuring timeView for friendly messages
        this.timeView.setGravity(Gravity.END);
        this.timeView.setTextColor(Color.WHITE);
    }
}
