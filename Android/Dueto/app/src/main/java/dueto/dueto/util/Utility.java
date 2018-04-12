package dueto.dueto.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ben on 30/03/18.
 */

public class Utility {

    private Context mycontext;

    private static final Utility man = new Utility();

    public static Utility getMan()
    {
        return man;
    }

    public static Utility getMan(Context context) {
        if(context == null)
            man.setContext(context);
        return man;
    }

    private Utility()
    {

    }

    private void setContext(Context context)
    {
        mycontext = context;
    }

    public void makeToast(String message)
    {
        Toast toast = Toast.makeText(mycontext, message, Toast.LENGTH_LONG);
        toast.show();
    }



}
