package dueto.dueto;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import static android.content.ContentValues.TAG;


public class PostsFragment extends Fragment {

    Intent i;
    String[] listitems = {"Activity 1"};



//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listitems));
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id){
//
//        switch(position){
//            case 0:
//                i = new Intent(getActivity(), MainActivity.class);
//                break;
//        }
//
//        startActivity(i);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

}
