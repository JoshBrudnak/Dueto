package dueto.dueto.servercom;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public String toString()
    {
        return json.toString();
    }

    public JSONObject toJson()
    {
        return json;
    }

}
