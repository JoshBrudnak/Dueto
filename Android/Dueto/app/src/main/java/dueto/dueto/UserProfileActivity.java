package dueto.dueto;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity{

    private static final String TAG = "ProfilePicPreview";
    private ImageButton editProfile;
    private ImageButton editMusic;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        //firstView = (ImageButton) findViewById(R.id.firstView);
        editProfile = (ImageButton) findViewById(R.id.editButton);
        editMusic = (ImageButton) findViewById(R.id.musicButton);
        scrollView = (ScrollView) findViewById(R.id.userScroll);

        scrollView.scrollTo(0, 0);

        TextView newName = (TextView) findViewById(R.id.profileName);
        newName.setText(getIntent().getStringExtra("name"));
        newName.setGravity(Gravity.CENTER);

        TextView newLoc = (TextView) findViewById(R.id.profileLoc);
        newLoc.setText(getIntent().getStringExtra("location"));
        newLoc.setGravity(Gravity.CENTER);

        TextView newDesc = (TextView) findViewById(R.id.profileDesc);
        newDesc.setText(getIntent().getStringExtra("description"));
        newDesc.setGravity(Gravity.CENTER);

        if(getIntent().hasExtra("byteArray")) {
            ImageView _imv = (ImageView) findViewById(R.id.firstView);
            Bitmap _bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            _imv.setImageBitmap(_bitmap);
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });
        editMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditMusic.class);
                startActivity(intent);
            }
        });

    }

    public void ChangeFragment(View view) {
        Fragment fragment;

        if (view == findViewById(R.id.button1)) {
            fragment = new PostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        else if (view == findViewById(R.id.button2)) {
            fragment = new RepostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        else {
            fragment = new PostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }

    }
}
