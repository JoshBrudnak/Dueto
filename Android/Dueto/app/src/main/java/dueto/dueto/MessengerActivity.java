package dueto.dueto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dueto.dueto.templates.MessageOverviewCell;
import dueto.dueto.templates.Messenger;
import dueto.dueto.util.MessagingHandler;

public class MessengerActivity extends AppCompatActivity {

    public static Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(messenger.getParent() == null)
            this.setContentView(messenger);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
