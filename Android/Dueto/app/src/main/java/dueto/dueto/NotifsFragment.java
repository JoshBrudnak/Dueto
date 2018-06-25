package dueto.dueto;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import dueto.dueto.templates.NotificationCell;
import dueto.dueto.templates.NotificationListAdapter;

public class NotifsFragment extends Fragment {
    private static final String TAG = "Tab2Notifications";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView mListView = (ListView) getView().findViewById(R.id.listView);

        NotificationCell josh = new NotificationCell("Mr. Brudnak"," liked your post.",
                "1d", "drawable://" + R.drawable.profile, "drawable://" + R.drawable.cello);

        NotificationCell laith = new NotificationCell("Laith Rafidi"," followed you.",
                "2d", "drawable://" + R.drawable.coop, "drawable://" + R.drawable.guitars);

        NotificationCell ben = new NotificationCell("Ben Diehl"," liked your post.",
                "3d", "drawable://" + R.drawable.profile, "drawable://" + R.drawable.cello);

        NotificationCell lily = new NotificationCell("Flexerblex92"," reposted your post.",
                "4d", "drawable://" + R.drawable.coop, "drawable://" + R.drawable.guitars);

        //Add the Person objects to an ArrayList
        ArrayList<NotificationCell> notificationList = new ArrayList<>();

        notificationList.add(josh);
        notificationList.add(laith);
        notificationList.add(ben);
        notificationList.add(lily);



        Context globalContext;
        globalContext = getActivity().getApplicationContext();
        NotificationListAdapter adapter = new NotificationListAdapter(globalContext, R.layout.notification_adapter, notificationList);
        mListView.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications,container,false);

        return view;
    }
}
