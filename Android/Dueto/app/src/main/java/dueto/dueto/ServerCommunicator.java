package dueto.dueto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class to communicate with our server and send HTTPRequests to the sever, receiving and returning JSON.
 */

public class ServerCommunicator
{
    private URL url;
    private static String sessionID;
    private static int userID;

    private HttpURLConnection connection;

    public ServerCommunicator(){
        try{
            url = new URL("https://35.231.109.184:8082/api/login?");

        }catch(MalformedURLException mURLe)
        {
            System.out.println(mURLe.getMessage());
        }

    }

    public JSONObject makeRequest(String requestType, HashMap parameters)
    {
        JSONObject out = new JSONObject();

        try {
            connection = (HttpURLConnection) url.openConnection();
            System.out.println("-----------------Connection established -----------------");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            switch (requestType.toLowerCase()) {
                case "video": {
                    requestVideo(parameters);
                }
                case "discover": {
                    requestDiscover(parameters);
                }
                case "login": {
                    final HashMap<String, String> tempParams = parameters;

                    connection.setRequestMethod("POST");
                }
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            out.getJSONObject(content.toString());

            in.close();
            connection.disconnect();

            } catch(IOException | JSONException ioexc)
            {
                System.out.println(ioexc.getMessage());
                System.exit(0);
            }
        return out;
    }

    //----------------------------------------------------------------
    //PRIVATE METHODS
    //----------------------------------------------------------------

    private void requestVideo(HashMap parameters)
    {
        if(parameters.containsKey("videoID"))
        {
            try {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes("videoid="+parameters.get("videoID"));
                outputStream.flush();
                outputStream.close();
            }
            catch(IOException ioexc)
            {
                System.out.println(ioexc.getMessage());
            }
        }
        else
        {
            throw new IllegalStateException("No videoID specified!");
        }
    }

    private void requestDiscover(HashMap parameters)
    {
    }

    private void login(HashMap parameters)
    {
        try {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes("username=" + parameters.get("username") + ",password=" + parameters.get("password"));
            outputStream.flush();
            outputStream.close();
        }
        catch(IOException ioexc)
        {
            System.out.println(ioexc.getMessage());
        }
    }
}
