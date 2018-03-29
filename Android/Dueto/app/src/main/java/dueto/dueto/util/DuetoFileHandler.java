package dueto.dueto.util;

import android.content.Context;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import dueto.dueto.servercom.Server;

/**
 * Created by ben on 28/03/18.
 */

public class DuetoFileHandler {
    public static ArrayList<File> load(Context context, String identifier) {
        File dir = context.getCacheDir();
        ArrayList<File> out = new ArrayList<>();

        for (File file : dir.listFiles())
            if (file.getName().contains(identifier))
                out.add(file);

        return out;
    }

    public static void loadOnStartUp(Context context)
    {
        Server.videoList = load(context, "vid");
        Server.imgList = load(context, "img");
        Server.videoCount = Server.videoList.size();
        Server.imgCount = Server.imgList.size();
    }


}
