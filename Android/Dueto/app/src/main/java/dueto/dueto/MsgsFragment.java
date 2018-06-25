package dueto.dueto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dueto.dueto.messageoverview.MessageOverviewAdapter;
import dueto.dueto.messageoverview.MessageOverviewObject;
import dueto.dueto.util.MessagingHandler;


public class MsgsFragment extends Fragment {

    private static final String TAG = "Tab1Messages";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.messageoverview);
        MessageOverviewAdapter adapter = new MessageOverviewAdapter(this.getContext(), MessagingHandler.messageOverviewObjectArrayList);

        System.out.println("---------------------------------------------------------");

        for(MessageOverviewObject obj : MessagingHandler.messageOverviewObjectArrayList)
            System.out.println(obj.getName());

        MessagingHandler.setCurrentMessageOverviewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();}
}