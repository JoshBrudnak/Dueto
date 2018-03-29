package dueto.dueto.servercom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ben on 03/03/18.
 * Convert a map parameters to JSON
 */

public class JsonManager
{
    private JSONObject json;

    public JsonManager(Map params) throws JSONException
    {
        if(!params.isEmpty())
        {
            json = new JSONObject();
            Set<String> keySet = params.keySet();

            for(String k : keySet)
                json.put(k, params.get(k));
        }
    }

    public JsonManager(JSONObject json)
    {
        this.json = json;
    }

    @Override
    public String toString()
    {
        return json.toString();
    }

    public JSONObject toJson()
    {
        return json;
    }

    public URL jsonToUrl(String url) throws IOException, JSONException
    {
        Iterator<String> iter = json.keys();

        while(iter.hasNext())
        {
            String temp = iter.next();

            url = url.concat(temp + "=" + json.getString(temp));
            if(iter.hasNext())
                url = url.concat("&");
        }
        return new URL(url);
    }
}
