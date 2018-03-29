package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Stack;

import dueto.dueto.R;
import dueto.dueto.util.MessagingHandler;

/**
 * Created by ben on 28/03/18.
 */

public class Messenger extends LinearLayout
{
    private TextView usernameView;
    private EditText currentMessageText;
    private TableLayout messageLayout;
    private Button sendButton;

    private int height, width;

    public Messenger(Context context)
    {
        super(context);
    }

    public Messenger(Context context, Display display, Stack<MessageCell> messages, String username)
    {
        super(context);

        //Getting display metrics
        DisplayMetrics dms = new DisplayMetrics();
        display.getMetrics(dms);

        height = dms.heightPixels;
        width = dms.widthPixels;

        this.setMinimumHeight(height);
        this.setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity =Gravity.TOP;
        //Setting up views

        //usernameView setup
        usernameView = new TextView(context);
        //Setting text properties
        usernameView.setText(username);
        usernameView.setTextSize(15);
        usernameView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        //Setting gravity
        usernameView.setLayoutParams(params);

        //messageLayout setup
        messageLayout = new TableLayout(context);
        //Populating the View with messages
        while(!messages.empty())
            messageLayout.addView(messages.pop());
        //Setting scrolling properties
        messageLayout.setScrollBarFadeDuration(2500);
        //Setting minHeight
        messageLayout.setMinimumHeight(8*height/10);
        messageLayout.setMinimumWidth(width);
        //centering
        params.gravity = Gravity.CENTER;
        messageLayout.setLayoutParams(params);
        messageLayout.setBackgroundColor(Color.LTGRAY);

        //Setting up a temporary horizontal linear layout for currentMessageText and sendButton
        LinearLayout hor = new LinearLayout(context);
        hor.setBackgroundColor(Color.WHITE);
        hor.setOrientation(HORIZONTAL);

        //currentMessageText setup
        currentMessageText = new EditText(context);
        //Setting dimensions
        currentMessageText.setMaxHeight(height/7);
        currentMessageText.setWidth(8*(width/10));
        //Unnecessary fun features
        currentMessageText.setHint("Say something...");

        //sendButton setup
        sendButton = new Button(context);
        //Setting dimensions
        sendButton.setMaxHeight(2*width/10);
        sendButton.setMinHeight(2*width/10);
        sendButton.setMaxWidth(2*width/10);
        sendButton.setMinWidth(2*width/10);
        //Setting image
        sendButton.setBackgroundColor(Color.GREEN);
        //Setting up on-click listener for sendButton
        sendButton.setOnClickListener(e -> {
            messageLayout.addView(MessagingHandler.sendMessage(context, display, "peter pan", currentMessageText.getText().toString()));
            currentMessageText.setText("");
            messageLayout.postInvalidate();

            for(int i = 0; i < messageLayout.getChildCount(); i++)
            {
                System.out.println(messageLayout.getChildAt(i));
            }

        });

        hor.addView(currentMessageText);
        hor.addView(sendButton);
        hor.setGravity(Gravity.CENTER);

        this.addView(usernameView);
        this.addView(messageLayout);
        this.addView(hor);
        this.setGravity(Gravity.BOTTOM);
    }



}
