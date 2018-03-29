package dueto.dueto.templates;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.Stack;

import dueto.dueto.MessengerActivity;
import dueto.dueto.R;
import dueto.dueto.util.MessagingHandler;

/**
 * Created by ben on 28/03/18.
 */

public class MessageOverviewCell extends TableRow {

    private TextView usernameView;

    public MessageOverviewCell(Context context)
    {
        super(context);
    }

    public MessageOverviewCell(Context context, Display display, String username/*, File image*/)
    {
        super(context);

        DisplayMetrics dms = new DisplayMetrics();
        display.getMetrics(dms);

        int height = dms.heightPixels;
        int width = dms.widthPixels;

        //Bitmap avatar = BitmapFactory.decodeFile(image.getPath());
        ImageView avatarView = new ImageView(context);
        //avatarView.setImageBitmap(avatar);
        avatarView.setImageResource(R.drawable.profile);

        usernameView = new TextView(context);
        usernameView.setText(username);

        LinearLayout ln = new LinearLayout(context);
        ln.setOrientation(HORIZONTAL);

        avatarView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        avatarView.setMaxHeight(width/7);
        avatarView.setMaxWidth(width/7);
        avatarView.setPadding(width/20, width/20, width/20, width/20);

        usernameView.setPadding(width/20,width/20,width/20,width/20);

        ln.addView(avatarView);
        ln.addView(usernameView);
        this.addView(ln);

        this.setOnClickListener(e->{

            if(!MessagingHandler.overviewToMessengerMap.containsKey(this) || MessagingHandler.overviewToMessengerMap.get(this) == null)
            {
                MessagingHandler.overviewToMessengerMap.put(this, new Messenger(context, display, new Stack<>(), username));
            }

            MessengerActivity.messenger = MessagingHandler.overviewToMessengerMap.get(this);

            Intent intent = new Intent(context, MessengerActivity.class);
            context.startActivity(intent);
        });
    }

    public void checkForNewMessage()
    {
        //TODO: IMPLEMENT MESSAGE CHECKING
//        if(/*new Message*/)
//        {
//            usernameView.setTextColor(Color.BLUE);
//        }
    }

}
