package dueto.dueto.templates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;
import dueto.dueto.R;
public class ProfileListAdapter extends ArrayAdapter<ProfileCell> {

    private static final String TAG = "ProfileListAdapter";

    private Context mContext;
    private int mResource;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<ProfileCell> mVideos;

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView likes;
        TextView comments;
        TextView reposts;
        TextView timeStamp;
        ImageView image;
        ImageView thumbnail;
        UniversalVideoView video;
    }


    public ProfileListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ProfileCell> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
        mVideos = objects;
        setupImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String name = getItem(position).getName();
        String description = getItem(position).getDescription();
        String likes = getItem(position).getLikes();
        String comments = getItem(position).getComments();
        String reposts = getItem(position).getReposts();
        String timeStamp = getItem(position).getTimeStamp();
        String imgUrl = getItem(position).getImgURL();
        String thumbURL = getItem(position).getThumbnail();
        String videoURL = getItem(position).getVideoURL();

        final View result;      //create the view result for showing the animation

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            //convertView = inflater.inflate(mResource, parent, false);
            //convertView = inflater.inflate(R.layout.profile_adapter, null);
            convertView = inflater.inflate(R.layout.profile_adapter, parent, false);

            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.userName);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.likes = (TextView) convertView.findViewById(R.id.likes);
            holder.comments = (TextView) convertView.findViewById(R.id.comments);
            holder.reposts = (TextView) convertView.findViewById(R.id.reposts);
            holder.timeStamp = (TextView) convertView.findViewById(R.id.timeStamp);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.video = (UniversalVideoView) convertView.findViewById(R.id.video);

            //result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            //result = convertView;
        }

        holder.name.setText(name);
        holder.description.setText(description);
        holder.likes.setText(likes);
        holder.comments.setText(comments);
        holder.timeStamp.setText(timeStamp);
        holder.reposts.setText(reposts);

        holder.thumbnail.bringToFront();
        //holder.thumbnail.setAlpha(0.5f); //controls transparency of the image (0-1, float, where 1 is opaque)

        try {
            ProfileCell video = mVideos.get(position);
            holder.video.getHolder().setSizeFromLayout();
            //play video using android api, when video view is clicked.
            String url = video.getVideoURL(); // your URL here
            Uri videoUri = Uri.parse(url);
            holder.video.setVideoURI(videoUri);

            holder.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.video.pause();

                    holder.video.setOnTouchListener(new View.OnTouchListener() {
                        private long startClickTime;

                        @Override
                        public boolean onTouch(View v, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            startClickTime = System.currentTimeMillis();
                        }
                        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                            if (System.currentTimeMillis() - startClickTime < ViewConfiguration.getTapTimeout()) {
                                 if (!holder.video.isPlaying()) {
                                      v.performClick();
                                      holder.thumbnail.setVisibility(View.INVISIBLE);
                                      holder.video.start();

                                      return true;
                                  }
                                  if (holder.video.isPlaying()) {
                                      v.performClick();
                                      //holder.thumbnail.setVisibility(View.VISIBLE);
                                      holder.video.pause();

                                      return true;
                                 }

                            }
                            else {
                                return false;
                            }

                        return true;
                        }
                        else {
                            return true;
                        }

                        return true;
                        }
                    });

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.image);
        imageLoader.displayImage(thumbURL, holder.thumbnail);

        return convertView;
    }

    private void setupImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }
}
