package dueto.dueto.templates;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import dueto.dueto.R;
import dueto.dueto.servercom.Server;


/**
 * TableCell, defining general accessors as well as fields of a TableCell
 */

public class TableCell extends TableRow
{
    private ImageView thumbnail,
                      profile;
    private VideoView videoView;

    private String description, time;
    private JSONObject artist;

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
            artist = internal.getJSONObject("Artist");
            description = internal.getString("Desc");
            time = internal.getString("Time");
            likes = internal.getInt("Likes");

            TableLayout tl = new TableLayout(context);
            tl.setBackgroundColor(Color.parseColor("#e6e6e6"));

            TableRow trUp = new TableRow(context);

            TableRow trLow = new TableRow(context);
            trLow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT));
            trLow.setMinimumHeight(width/5);
            
            LinearLayout nameLinLayout = new LinearLayout(context);
            nameLinLayout.setOrientation(LinearLayout.VERTICAL);
            
            LinearLayout horLinLayout = new LinearLayout(context);
            horLinLayout.setOrientation(LinearLayout.HORIZONTAL);
            
            TextView artistName = new TextView(context);
            artistName.setText(artist.getString("Name"));
            artistName.setTextColor(Color.BLACK);
            artistName.setTextSize(20);

            TextView descriptionView = new TextView(context);
            descriptionView.setText(description);
            descriptionView.setTextColor(Color.BLACK);
            descriptionView.setTextSize(12);

            nameLinLayout.addView(artistName);
            nameLinLayout.addView(descriptionView);
            nameLinLayout.setMinimumWidth(width - width/7 - time.length()*24);

            File file = new File("test.png");
            try {
                file.createNewFile();
            }catch (IOException ioexc)
            {
                System.out.println(ioexc);
            }

//            Server.SERVER.downloadFile(new JSONObject().put("artist", "1").put("name", "Sample") , file);
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            ImageView test = new ImageView(context);
//            test.setImageBitmap(bitmap);
//            //test.set

            thumbnail = (ImageView) internal.get("thumbpic");
            thumbnail.setMinimumWidth(width);
            thumbnail.setMinimumHeight(width);
            thumbnail.setMaxHeight(width);
            thumbnail.setMaxWidth(width);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            thumbnail.setOnClickListener(e->
            {
                //TODO: insert video player
            });

            profile = (ImageView) internal.get("profilepic");
            profile.setMinimumHeight(width/7);
            profile.setMinimumWidth(width/7);
            profile.setMaxWidth(width / 7);
            profile.setMaxHeight(width/7);

            profile.setOnClickListener(e->{
                //TODO: insert link to profile here.
            });

            TextView timeView = new TextView(context);
            timeView.setText(time);
            timeView.setTextSize(12);

            trUp.addView(this.thumbnail);
            horLinLayout.addView(this.profile);
            horLinLayout.addView(nameLinLayout);
            horLinLayout.addView(timeView);
            trLow.addView(horLinLayout);
            nameLinLayout.addView(createSmallLayout(context, likes));
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

        DecimalFormat df = new DecimalFormat("0.#");

        String likeString= Integer.toString(likes);

        if(likes > 999 && likes < 1000000)
            likeString = df.format((double) likes /1000.0) + "k";

        else if(likes > 999999)
            likeString = df.format((double) likes /1000000.0) + "m";

        noOfLikesView.setText(likeString);

        //Resize
        likeView.setMaxHeight(width/100);
        likeView.setMaxWidth(width/100);
        likeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        likeView.setScaleX((float) 0.5);
        likeView.setScaleY((float) 0.5);
        noOfLikesView.setTextSize(13);
        //add to smallLayout
        output.addView(likeView);
        output.addView(noOfLikesView);

        System.out.println("createSmallLayout called and executed");

        return output;
    }

}

