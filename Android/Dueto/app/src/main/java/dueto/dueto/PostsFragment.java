package dueto.dueto;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dueto.dueto.servercom.Server;
import dueto.dueto.templates.ProfileCell;
import dueto.dueto.templates.ProfileListAdapter;
import dueto.dueto.templates.TableCell;

import static android.content.ContentValues.TAG;


public class PostsFragment extends Fragment {

    Intent i;
    String[] listitems = {"Activity 1"};

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView mListView = (ListView) getView().findViewById(R.id.listView);

        ProfileCell john = new ProfileCell("User 1","John Dungeldo is a striving idiot",
                "1.5m likes", "X comments", "Y reposts", "2d",
                "drawable://" + R.drawable.icon,"https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");

        ProfileCell steve = new ProfileCell("User 1","John Dungeldo John Dungeldo John Dungeldo",
                "2.5k likes", "X comments", "Y reposts", "3d",
                "drawable://" + R.drawable.icon,"https://s3.amazonaws.com/androidvideostutorial/862009639.mp4");

        ProfileCell stacy = new ProfileCell("User 1","Leader",
                "Z likes", "X comments", "Y reposts", "4w",
                "drawable://" + R.drawable.icon, "https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        ProfileCell ashley = new ProfileCell("User 1","Web",
                "Z likes", "X comments", "Y reposts", "1m",
                "drawable://" + R.drawable.icon,"https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");



        //Add the Person objects to an ArrayList
        ArrayList<ProfileCell> profileList = new ArrayList<>();

        profileList.add(john);
        profileList.add(steve);

        Context globalContext;
        globalContext = getActivity().getApplicationContext();
        ProfileListAdapter adapter = new ProfileListAdapter(globalContext, R.layout.profile_adapter, profileList);
        mListView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

}
