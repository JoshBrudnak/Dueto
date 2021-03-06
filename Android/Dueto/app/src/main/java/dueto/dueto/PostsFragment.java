package dueto.dueto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import dueto.dueto.templates.ProfileCell;
import dueto.dueto.templates.ProfileListAdapter;


public class PostsFragment extends Fragment {

    Intent i;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView mListView = (ListView) getView().findViewById(R.id.listView);

        ProfileCell john = new ProfileCell("User 1","John Dungeldo is a striving idiot",
                "1.5m likes", "X comments", "Y reposts", "2d",
                "drawable://" + R.drawable.icon, "drawable://" + R.drawable.cello, "https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");

        ProfileCell steve = new ProfileCell("User 1","John Dungeldo John Dungeldo John Dungeldo",
                "2.5k likes", "X comments", "Y reposts", "3d",
                "drawable://" + R.drawable.icon, "drawable://" + R.drawable.guitars, "https://s3.amazonaws.com/androidvideostutorial/862009639.mp4");

        ProfileCell stacy = new ProfileCell("User 1","Leader",
                "Z likes", "X comments", "Y reposts", "4w",
                "drawable://" + R.drawable.icon, "drawable://" + R.drawable.cello,  "https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        ProfileCell ashley = new ProfileCell("User 1","Web",
                "Z likes", "X comments", "Y reposts", "1m",
                "drawable://" + R.drawable.icon, "drawable://" + R.drawable.guitars, "https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");

        ArrayList<ProfileCell> profileList = new ArrayList<>();

        profileList.add(john);
        profileList.add(steve);
        profileList.add(stacy);
        profileList.add(ashley);
        profileList.add(john);
        profileList.add(steve);
        profileList.add(stacy);
        profileList.add(ashley);

        Context globalContext;
        globalContext = getActivity().getApplicationContext();
        ProfileListAdapter adapter = new ProfileListAdapter(globalContext, R.layout.profile_adapter, profileList);
        mListView.setAdapter(adapter);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

}
