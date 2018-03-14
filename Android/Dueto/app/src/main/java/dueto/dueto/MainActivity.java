package dueto.dueto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import dueto.dueto.servercom.Server;
import dueto.dueto.templates.TableCell;

public class MainActivity extends Activity {

    private Button LogIn;
    private Button SignIn;
    private Button Notifications;
    private int bVisibility = View.INVISIBLE;
    private int menuButtonRotation = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    ImageView pictureView;
    private Display display;
    private boolean scrollable = true; //Scroll always
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        display = getWindowManager().getDefaultDisplay();
        TableLayout t1 = findViewById(R.id.homeTable);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        ImageView profilepic = new ImageView(this);
        ImageView profilepic2 = new ImageView(this);
        ImageView thumbpic = new ImageView(this);
        ImageView thumbpic2 = new ImageView(this);
        profilepic.setImageResource(R.drawable.profile);
        profilepic2.setImageResource(R.drawable.coop);
        thumbpic.setImageResource(R.drawable.cello);
        thumbpic2.setImageResource(R.drawable.guitars);
        JSONObject test = new JSONObject();
        try
        {
//            test.put("artist", 1);
//            test.put("name", "Sample");

            jsonObject.put("Artist", new JSONObject().put("Name", "Arnold Schwarzenegger"));
            jsonObject.put("Desc", "John Dungeldo is a striving idiot");
            jsonObject.put("profilepic", profilepic);
            jsonObject.put("thumbpic", thumbpic);
            jsonObject.put("Time", "1d");
            jsonObject.put("Likes", "1512000");

            jsonObject2.put("Artist", new JSONObject().put("Name", "John Dungeldo"));
            jsonObject2.put("Desc", "John Dungeldo John Dungeldo John Dungeldo");
            jsonObject2.put("profilepic", profilepic2);
            jsonObject2.put("thumbpic", thumbpic2);
            jsonObject2.put("Time", "2d");
            jsonObject2.put("Likes", "2520");
        }
        catch(JSONException js)
        {
            System.out.println(js.getMessage());
        }

        TableCell cell = new TableCell(this, display, jsonObject);
        TableCell cell2 = new TableCell(this, display, jsonObject2);
        t1.addView(cell);
        t1.addView(cell2);

        JSONObject home = Server.SERVER.request("home", test);
        //------------------------------------------------------------------------------------------


        //Defining buttons to use them later
        final FloatingActionButton
                menuButton = findViewById(R.id.menuButton),
                cameraButton = findViewById(R.id.cameraButton),
                coopButton = findViewById(R.id.coopButton),
                discoverButton = findViewById(R.id.discoverButton),
                messagesButton = findViewById(R.id.messagesButton),
                profileButton = findViewById(R.id.profileButton),
                homeButton = findViewById(R.id.homeButton);

        final RelativeLayout constr = findViewById(R.id.relLayout);

        final ScrollView sView = findViewById(R.id.sView);

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimelineActivity.class));
            }
        });

        coopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });

        //Setting button visibility to invisible
        constr.setVisibility(bVisibility);
        menuButton.setRotation(menuButtonRotation);
        menuButtonRotation = 45;
        bVisibility = View.VISIBLE;

        menuButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                menuButton.setRotation(menuButtonRotation);
                constr.setVisibility(bVisibility);
                bVisibility = bVisibility != 0 ? 0 : 4;
                menuButtonRotation = menuButtonRotation != 45 ? 45 : 0;

                if (scrollable) {
                    sView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                } else {
                    sView.setOnTouchListener(null);
                }

                scrollable = !scrollable;
            }
        });

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

        cameraButton.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });

    }

}
