package dueto.dueto.servercom;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Used to make requests to a server.
 */

public class Server
{
    /* TODO: implement "POST" requests addvideo, addthumbnail, addavatar, add..., logout*/
    public static final Server SERVER = new Server();

    public static int imgCount, videoCount;

    public static List<File> imgList, videoList;

    private final String ADDRESS = "http://35.231.109.184:8080/api/";
    private URL url;
    private static JSONObject session_cookie;

    public Server()
    {
        imgList = new ArrayList<>();
        videoList = new ArrayList<>();
    }

    public boolean login(String username, String password)
    {
        try {
            new LoginRequest().execute(username, password).get();
            if(session_cookie.has("session"))
                return true;
        }
        catch(Exception exc)
        {
            System.out.println(exc.getMessage());
        }
        return false;
    }

    public JSONObject request(String type, JSONObject information)
    {
        try {
            String endpoint, requesttype;
            switch (type.toLowerCase()) {
                case "video":
                case "home":
                case "profile":
                case "discover":
                case "genre":
                    endpoint = type.toLowerCase();
                    requesttype = "GET";
                    break;
                case "createuser":
                    endpoint = type.toLowerCase();
                    requesttype = "POST";
                    break;
                default:
                    throw new IllegalStateException(type + " is not a valid endpoint");
            }
            if (information == null) {
                information = new JSONObject();
            }
            return new GeneralRequest(endpoint, requesttype).execute(information).get();
        }catch(Exception exc) {
            System.out.println(exc.getMessage());
            for(StackTraceElement ste : exc.getStackTrace())
                System.out.println(ste.getLineNumber() + " in " + ste.getMethodName() + " of " + ste.getClassName());
            return null;
        }
    }

    /**
     * Log into the service, retrieving a session cookie
     * @param username username used for the login request
     * @param password password used for the login request
     * @return
     */
    private void loginTask(String username, String password) throws JSONException
    {
        try {

            //Creating JSON
            JSONObject loginData = new JSONObject();
            loginData.put("username", username);
            loginData.put("password", password);

            //Creating Objects
            url = new URL(ADDRESS+"login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //StringBuffer body = new StringBuffer();

            JsonManager manager;
            HashMap<String, String> sessionCookie = new HashMap<>();

            //DEBUG: System.out.println("debug: created objects");

            //set up connection
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            //DEBUG: System.out.println("debug: set up connection");
            //write request body
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(loginData.toString());
            outputStream.flush();
            outputStream.close();

            //DEBUG: System.out.println("debug: wrote bytes");
            //get cookie from header
            String cookie = conn.getHeaderField("Set-Cookie");

            //make map from cookie
            if(cookie != null) {
                sessionCookie.put("session", cookie.split(";")[0]);
                sessionCookie.put("path", cookie.split(";")[1].split("=")[1]);
                sessionCookie.put("expiry", cookie.split(";")[2].split("=")[1]);
                //DEBUG: System.out.println("debug: has cookie");
            }
            else
            {
                sessionCookie.put("invalid", "invalid");
            }

            //create JsonManager and set cookie JSON
            manager = new JsonManager(sessionCookie);
            session_cookie = manager.toJson();

            conn.disconnect();
        }
        catch(IOException exc)
        {
            System.out.println(exc.getMessage());
        }
    }

    private JSONObject requestTask(String request_type, String endpoint, JSONObject request_body)
    {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(ADDRESS+endpoint).openConnection();
            //StringBuffer body = new StringBuffer();
            conn.setRequestMethod(request_type);
            System.out.println("Request method set");

            conn.setDoOutput(true);
            System.out.println("output is set");

            conn.setDoInput(true);
            System.out.println("input is set");

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Cookie", session_cookie.getString("session"));

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(request_body.toString());
            outputStream.flush();
            outputStream.close();

            //DEBUG: System.out.println(session_cookie.getString("session"));

            conn.setConnectTimeout(1000);
            conn.setReadTimeout(2500);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("Got inputStream: " + in);
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + response.toString());

            conn.disconnect();
            return new JSONObject(response.toString());
        }
        catch(IOException |JSONException exc)
        {
            System.out.println("Exception "+exc.getMessage() + " Cause " + exc.getCause());
            for(StackTraceElement ste : exc.getStackTrace())
                System.out.println(ste.getLineNumber() + " in " + ste.getMethodName() + " of " + ste.getClassName());
            return null;
        }
    }

    class LoginRequest extends AsyncTask<String, Integer, JSONObject>
    {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                loginTask(strings[0], strings[1]);
            }
            catch(JSONException jsonexc)
            {
                System.out.println(jsonexc.getMessage());
                System.exit(1);
            }
            return null;
        }
    }

    class GeneralRequest extends AsyncTask<JSONObject, Integer, JSONObject>
    {
        String endpoint, type;

        public GeneralRequest(String endpoint, String type)
        {
            this.endpoint = endpoint;
            this.type = type;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            try {
                return requestTask(type, endpoint, jsonObjects[0]);
            }catch(Exception jsexc)
            {
                System.out.println(jsexc.getMessage() + "\n" + jsexc.getLocalizedMessage());
                return null;
            }
        }
    }

    public class DownloadRequest extends AsyncTask<Void, Integer, File>
    {
        private Context context;
        private JSONObject json;
        private boolean video;

        public DownloadRequest(Context context, JSONObject json, boolean video)
        {
            this.context = context;
            this.json = json;
            this.video = video;
        }

        @Override
        protected File doInBackground(Void... voids) {
            try {
                File file = File.createTempFile((video ? "vid":"img") + (video ? videoCount:imgCount), video ? "mp4":"bmp", context.getCacheDir());

                URL url_with_params = new JsonManager(json).jsonToUrl(ADDRESS+(video?"video?":"thumbnail"));
                ReadableByteChannel rbc = Channels.newChannel(url_with_params.openStream());
                FileOutputStream fos = new FileOutputStream(file);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();

                if(video)
                {
                    videoList.add(file);
                    videoCount++;
                }
                else
                {
                    imgList.add(file);
                    imgCount++;
                }

                return file;

            }
            catch(IOException | JSONException ioexc)
            {
                System.out.println(ioexc);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    public File downloadFile(Context context, JSONObject json, boolean video)
    {
        try {
            DownloadRequest dr = new DownloadRequest(context, json, video);
            return dr.execute().get();
        }
        catch(Exception exc)
        {
            return null;
        }
    }

    //public class

}
