package dueto.dueto.templates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import dueto.dueto.R;

public class NotificationListAdapter extends ArrayAdapter<NotificationCell> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<NotificationCell> mVideos;

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView timeStamp;
        ImageView image;
        ImageView otherIMG;
    }

    public NotificationListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NotificationCell> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
        mVideos = objects;
        setupImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        String description = getItem(position).getDescription();
        String timeStamp = getItem(position).getTimeStamp();
        String imgUrl = getItem(position).getImgURL();
        String otherIMG = getItem(position).getotherIMG();

        final View result;

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            //convertView = inflater.inflate(mResource, parent, false);
            //convertView = inflater.inflate(R.layout.profile_adapter, null);
            convertView = inflater.inflate(R.layout.notification_adapter, parent, false);

            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.userName);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.timeStamp = (TextView) convertView.findViewById(R.id.timeStamp);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.otherIMG = (ImageView) convertView.findViewById(R.id.image2);

            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.name.setText(name);
        holder.description.setText(description);
        holder.timeStamp.setText(timeStamp);

        if (!imageLoader.isInited()) {
            setupImageLoader();
        }

        if (imageLoader.isInited()) {
            imageLoader.displayImage(imgUrl, holder.image);
            imageLoader.displayImage(otherIMG, holder.otherIMG);
        }

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
