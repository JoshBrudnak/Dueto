package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * TableCell, defining general accessors as well as fields of a TableCell
 */

public class TableCell extends TableRow
{
    private ImageView thumbnail,
                      profile;
    private VideoView videoView;

    private String artist, videoTitle, description;

    private int likes;

    private int height, width;

    /**
     * Creates a new TableCell object, with standard parameters
     * @param context
     */
    public TableCell(Context context)
    {
        super(context);
    }
    /**
     *
     */
    public TableCell(Context context, Display display, JSONObject json)
    {
        super(context);

        DisplayMetrics dms = new DisplayMetrics();
        display.getMetrics(dms);

        height = dms.heightPixels;
        width = dms.widthPixels;

        try {
            JSONObject internal = json;
            artist = internal.getString("artist");
            videoTitle = internal.getString("name");
            description = internal.getString("description");

            TableLayout tl = new TableLayout(context);
            tl.setBackgroundColor(Color.WHITE);

            TableRow trUp = new TableRow(context);

            TableRow trLow = new TableRow(context);
            trLow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT));
            
            LinearLayout nameLinLayout = new LinearLayout(context);
            nameLinLayout.setOrientation(LinearLayout.VERTICAL);
            
            LinearLayout horLinLayout = new LinearLayout(context);
            horLinLayout.setOrientation(LinearLayout.HORIZONTAL);
            
            TextView artistName = new TextView(context);
            artistName.setText("by ".concat(artist));
            artistName.setTextColor(Color.BLUE);
            artistName.setTextSize(12);

            TextView vidTitle = new TextView(context);
            vidTitle.setText(videoTitle);
            vidTitle.setTextColor(Color.BLUE);
            vidTitle.setTextSize(20);

            nameLinLayout.addView(vidTitle);
            nameLinLayout.addView(artistName);

            thumbnail = (ImageView) internal.get("thumbpic");
            thumbnail.setMinimumWidth(width);
            thumbnail.setMinimumHeight(width);
            thumbnail.setMaxHeight(width);
            thumbnail.setMaxWidth(width);

            profile = (ImageView) internal.get("profilepic");
            profile.setMinimumHeight(width/7);
            profile.setMinimumWidth(width/7);
            profile.setMaxWidth(width / 7);
            profile.setMaxHeight(width/7);

            profile.setOnClickListener(e->{
            });

            trUp.addView(this.thumbnail);
            horLinLayout.addView(this.profile);
            horLinLayout.addView(nameLinLayout);
            trLow.addView(horLinLayout);
            tl.addView(trUp);
            tl.addView(trLow);
            this.addView(tl);

        }catch(JSONException jsonexc)
        {
            System.out.println("You done fucked up: " + jsonexc.getMessage());
            System.exit(0);
        }

    }
}
