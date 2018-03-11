package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;

import dueto.dueto.R;


/**
 * TableCell, defining general accessors as well as fields of a TableCell
 */

public class TableCell extends TableRow
{
    private ImageView thumbnail,
                      profile;
    private VideoView videoView;

    private String artist, description, time;

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
            description = internal.getString("description");
            time = internal.getString("time");
            likes = internal.getInt("likes");

            TableLayout tl = new TableLayout(context);
            tl.setBackgroundColor(Color.WHITE);

            TableRow trUp = new TableRow(context);

            TableRow trLow = new TableRow(context);
            trLow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT));
            trLow.setMinimumHeight(width/5);
            
            LinearLayout nameLinLayout = new LinearLayout(context);
            nameLinLayout.setOrientation(LinearLayout.VERTICAL);
            
            LinearLayout horLinLayout = new LinearLayout(context);
            horLinLayout.setOrientation(LinearLayout.HORIZONTAL);
            
            TextView artistName = new TextView(context);
            artistName.setText(artist);
            artistName.setTextColor(Color.BLACK);
            artistName.setTextSize(20);

            TextView descriptionView = new TextView(context);
            descriptionView.setText(description);
            descriptionView.setTextColor(Color.BLACK);
            descriptionView.setTextSize(12);

            nameLinLayout.addView(artistName);
            nameLinLayout.addView(descriptionView);
            nameLinLayout.setMinimumWidth(width - width/7 - time.length()*24);

            thumbnail = (ImageView) internal.get("thumbpic");
            thumbnail.setMinimumWidth(width);
            thumbnail.setMinimumHeight(width);
            thumbnail.setMaxHeight(width);
            thumbnail.setMaxWidth(width);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            profile = (ImageView) internal.get("profilepic");
            profile.setMinimumHeight(width/7);
            profile.setMinimumWidth(width/7);
            profile.setMaxWidth(width / 7);
            profile.setMaxHeight(width/7);

            profile.setOnClickListener(e->{
            });

            TextView timeView = new TextView(context);
            timeView.setText(time);
            timeView.setTextSize(12);

            trUp.addView(this.thumbnail);
            horLinLayout.addView(this.profile);
            horLinLayout.addView(nameLinLayout);
            horLinLayout.addView(timeView);
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

    private LinearLayout createSmallLayout(Context context, int likes)
    {
        LinearLayout output = new LinearLayout(context);

        output.setOrientation(LinearLayout.HORIZONTAL);

        ImageView likeView = new ImageView(context);
        TextView noOfLikesView = new TextView(context);
        ImageView shareView = new ImageView(context);
        ImageView commentView = new ImageView(context);

        //Create LikeBar
        View likeBar = new View(context);
        likeView.setImageResource(R.drawable.menu);
        noOfLikesView.setText(likes);
        //Resize
        likeView.setMaxHeight(width/10);
        likeView.setMaxWidth(width/10);
        noOfLikesView.setTextSize(10);
        //add to smallLayout
        output.addView(likeView);
        output.addView(noOfLikesView);

        return output;
    }

}

