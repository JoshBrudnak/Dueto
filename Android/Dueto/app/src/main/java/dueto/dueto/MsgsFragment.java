package dueto.dueto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import dueto.dueto.templates.TableCell;

public class MsgsFragment extends Fragment {

    private static final String TAG = "Tab1Messages";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages,container,false);

        return view;
    }

}