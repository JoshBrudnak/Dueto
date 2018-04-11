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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dueto.dueto.servercom.Server;
import dueto.dueto.templates.ProfileCell;
import dueto.dueto.templates.ProfileListAdapter;
import dueto.dueto.templates.TableCell;

import static android.content.ContentValues.TAG;


public class PostsFragment extends Fragment {

    Intent i;
    String[] listitems = {"Activity 1"};
    private ListView mListView;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView mListView = (ListView) getView().findViewById(R.id.listView);

        //Create the Person objects
        /*
        Person john = new Person("John","12-20-1998","Male",
                getResources().getIdentifier("@drawable/cartman_cop", null,this.getPackageName()));
                */

        ProfileCell john = new ProfileCell("User 1","iOS","Member",
                "drawable://" + R.drawable.icon);
        ProfileCell steve = new ProfileCell("User 2","iOS","Member",
                "drawable://" + R.drawable.icon);
        ProfileCell stacy = new ProfileCell("User 3","Leader","Member",
                "drawable://" + R.drawable.icon);
        ProfileCell ashley = new ProfileCell("User 4","Web","Member",
                "drawable://" + R.drawable.icon);
        ProfileCell matt = new ProfileCell("User 5","Android","Member",
                "drawable://" + R.drawable.icon);
        ProfileCell matt2 = new ProfileCell("User 6","N/A","N/A",
                "drawable://" + R.drawable.icon);
        ProfileCell matt3 = new ProfileCell("User 7","N/A","N/A",
                "drawable://" + R.drawable.icon);
        ProfileCell matt4 = new ProfileCell("User 8","N/A","N/A",
                "drawable://" + R.drawable.icon);



        //Add the Person objects to an ArrayList
        ArrayList<ProfileCell> peopleList = new ArrayList<>();
        peopleList.add(john);
        peopleList.add(steve);
        peopleList.add(stacy);
        peopleList.add(ashley);
        peopleList.add(matt);
        peopleList.add(matt2);
        peopleList.add(matt3);

        Context globalContext;
        globalContext = getActivity().getApplicationContext();
        ProfileListAdapter adapter = new ProfileListAdapter(globalContext, R.layout.profile_adapter, peopleList);
        mListView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

}
