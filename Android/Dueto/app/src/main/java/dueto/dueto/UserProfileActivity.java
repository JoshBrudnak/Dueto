package dueto.dueto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
