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

import dueto.dueto.templates.ProfileListAdapter;

public class OtherProfileActivity extends AppCompatActivity{

    private static final String TAG = "OtherProfilePreview";
    private ImageButton messageUser;
    private ImageButton playMusic;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_other_user);

        TextView newName = (TextView) findViewById(R.id.otherName);
        newName.setText(getIntent().getStringExtra("otherName"));
        newName.setGravity(Gravity.CENTER);

        if(getIntent().hasExtra("otherByteArray")) {
            ImageView _imv = (ImageView) findViewById(R.id.otherIcon);
            Bitmap _bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("otherByteArray"),0,getIntent().getByteArrayExtra("otherByteArray").length);
            _imv.setImageBitmap(_bitmap);
        }

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
