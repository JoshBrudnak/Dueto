package dueto.dueto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import java.util.ArrayList;
import dueto.dueto.templates.MainCell;
import dueto.dueto.templates.MainListAdapter;
import dueto.dueto.util.Utility;
public class MainActivity extends Activity {

    private Button LogIn;
    private Button SignIn;
    private Button Notifications;
    private VideoView mVideoView;
    Integer SELECT_FILE = 0;
    private int ACTIVITY_START_CAMERA_APP = 0;
    private VideoView video;
    private VideoView video2;
    private int bVisibility = View.INVISIBLE;
    private int menuButtonRotation = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView pictureView;
    private Display display;
    private boolean scrollable = true; //Scroll always

    private VideoView realVideo;
    private VideoView realVideo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utility man = Utility.getMan(this);
        setContentView(R.layout.activity_main);

        ListView mListView = (ListView) findViewById(R.id.listView);

        MainCell arnold = new MainCell("Arnold Schwarze","John Dungeldo is a striving idiot",
                "1.5m likes", "X comments", "Y reposts", "2d",
                "drawable://" + R.drawable.profile,"https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        MainCell john = new MainCell("John Dungeldo","John Dungeldo John Dungeldo John",
                "2.5k likes", "X comments", "Y reposts", "3d",
                "drawable://" + R.drawable.coop,"https://s3.amazonaws.com/androidvideostutorial/862014834.mp4");

        MainCell stacy = new MainCell("User 1","Leader",
                "Z likes", "X comments", "Y reposts", "4w",
                "drawable://" + R.drawable.icon, "https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        MainCell ashley = new MainCell("User 1","Web",
                "Z likes", "X comments", "Y reposts", "1m",
                "drawable://" + R.drawable.icon,"https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");

        //Add the Person objects to an ArrayList
        ArrayList<MainCell> mainList = new ArrayList<>();

        mainList.add(arnold);
        mainList.add(john);

        MainListAdapter adapter = new MainListAdapter(this, R.layout.main_adapter, mainList);
        mListView.setAdapter(adapter);

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

        discoverButton.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DiscoverActivity.class));
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
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL}, 1);

        cameraButton.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });

    }



//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
//            Uri videoUri = data.getData();
//            Uri videoUri2 = data.getData();
//
//            realVideo.setVideoURI(videoUri);
//            realVideo2.setVideoURI(videoUri2);
//        }
//    }

}
