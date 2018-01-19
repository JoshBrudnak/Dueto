package dueto.dueto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity {

    private Button LogIn;
    private Button SignIn;
    private int bVisibility = View.INVISIBLE;
    private boolean scrollable = true; //Scroll always
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        /*TableLayout t1 = findViewById(R.id.homeTable);
        TableRow row = new TableRow(this);
        t1.addView(row);*/

        LogIn = (Button)findViewById(R.id.logButt); //the sign-in + log-in buttons on the Main page
        SignIn = (Button)findViewById(R.id.signButt);

        LogIn.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }
        });

        //Defining buttons to use them later
        final FloatingActionButton
                menuButton = findViewById(R.id.menuButton),
                cameraButton  = findViewById(R.id.cameraButton),
                coopButton = findViewById(R.id.coopButton),
                discoverButton = findViewById(R.id.discoverButton),
                messagesButton = findViewById(R.id.messagesButton),
                profileButton = findViewById(R.id.profileButton),
                homeButton = findViewById(R.id.homeButton);

        final ScrollView sView = findViewById(R.id.sView);

        //Setting button visibility to invisible
        cameraButton.setVisibility(bVisibility);
        coopButton.setVisibility(bVisibility);
        discoverButton.setVisibility(bVisibility);
        messagesButton.setVisibility(bVisibility);
        homeButton.setVisibility(bVisibility);
        profileButton.setVisibility(bVisibility);

        bVisibility = View.VISIBLE;

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraButton.setVisibility(bVisibility);
                coopButton.setVisibility(bVisibility);
                discoverButton.setVisibility(bVisibility);
                messagesButton.setVisibility(bVisibility);
                homeButton.setVisibility(bVisibility);
                profileButton.setVisibility(bVisibility);
                bVisibility = bVisibility != 0 ? 0:4;

                if(scrollable) {
                    sView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                }
                else
                {
                    sView.setOnTouchListener(null);
                }

                scrollable = !scrollable;
            }
        });
    }
}
