package dueto.dueto;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.VideoView;
import java.util.ArrayList;

/**
 * Created by ben on 07/01/18.
 * This class loads the videos, which should be displayed on the "Home" and places them in table rows in the layout.
 * If the server cannot be reached this class will provide the private method
 * notAvailable, which will display a connection error message.
 */

public class LoadHome extends Activity
{
    /**
     *
     */
    public void generateVideos(/*User user*/)
    {
        ArrayList<ImageView> viewers = new ArrayList<>();
        TableLayout table = (TableLayout)findViewById(R.id.homeTable);
        TableRow row = new TableRow(null);


        //Load videos into viewers


        for(ImageView v : viewers)
        {
            row.addView(v);
            table.addView(row);
        }


    }


    /**
    * notAvailable()
     */
    private static void notAvailable()
    {

    }
}
