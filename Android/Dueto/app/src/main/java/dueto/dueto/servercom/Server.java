package dueto.dueto.servercom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dueto.dueto.messaging.Message;
import dueto.dueto.util.MessagingHandler;
import dueto.dueto.util.Utility;

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

    private static final String CRLF = "\r\n";

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

    public List<Message> retrieveMessage(String user)
    {
        try{
            ArrayList<Message> out = new ArrayList<>();
            ArrayList<JSONObject> messages = (ArrayList) new MessageRetriever().execute(user).get();

            for(JSONObject msg : messages)
                out.add(new Message(msg, msg.getString("Artist").equals(user) ? Message.SENT : Message.RECEIVED));

            return out;
        }catch(Exception exc)
        {
            System.out.println("------------------------------------------------");
            System.out.println("Exception in Server.retrieveMessage:");
            System.out.println(exc.getMessage());
            System.out.println("------------------------------------------------");
        }
        return null;
    }

    public Bitmap getImage(String endpoint, String id)
    {
        try {
            return new ImageRequest(endpoint + (endpoint.equals("avatar") ? "?artist=" : "?id=") + id).execute().get();
        }
        catch(Exception exc)
        {
            System.out.println(exc);
            return null;
        }
    }

    public JSONObject request(String type, @Nullable JSONObject information)
    {
        try {
            String endpoint, requesttype;
            switch (type.toLowerCase()) {
                case "video":
                case "home":
                case "profile":
                case "discover":
                case "genre":
                case "artist":
                case "getmessages":
                    endpoint = type.toLowerCase();
                    return new URLParamRequest(endpoint, information).execute().get();
                case "createuser":
                case "postmessages":
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
            loginData.put("Username", username);
            loginData.put("Password", password);

            //Creating Objects
            url = new URL(ADDRESS+"login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //StringBuffer body = new StringBuffer();

            ParamManager manager;
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

            //create ParamManager and set cookie JSON
            manager = new ParamManager(sessionCookie);
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

            conn.setConnectTimeout(750);
            conn.setReadTimeout(1250);

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

    private class LoginRequest extends AsyncTask<String, Integer, JSONObject>
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

    private class GeneralRequest extends AsyncTask<JSONObject, Integer, JSONObject>
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

    private class URLParamRequest extends AsyncTask<String, Integer, JSONObject>
    {

        private URL mURL;

        public URLParamRequest(String endpoint, JSONObject jsonParams)
        {
            try {
                mURL = new ParamManager(jsonParams).jsonToUrl(ADDRESS + endpoint);
                //mURL = new URL("http://35.231.109.184:8080/api/artist?artist=1");
                System.out.println("--------------------------------");
                System.out.println(mURL);
                System.out.println("--------------------------------");
            }
            catch(IOException | JSONException exc)
            {
                System.out.println("--------------------------------");
                System.out.println("Exception in Server.URLParamRequest.URLParamRequest(): ");
                System.out.println(exc);
                System.out.println("--------------------------------");
            }
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
                //StringBuffer body = new StringBuffer();
                conn.setRequestMethod("GET");
                //DEBUG: System.out.println("Request method set");

                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Cookie", session_cookie.getString("session"));

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

                //DEBUG: System.out.println("Response: " + response.toString());

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
    }

    private class DownloadRequest extends AsyncTask<Void, Integer, File>
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

                URL url_with_params = new ParamManager(json).jsonToUrl(ADDRESS+(video?"video?":"thumbnail"));
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

    private class ImageRequest extends AsyncTask<Void, Integer, Bitmap>
    {
        public URL imageURL;

        public ImageRequest(String imageURL) {
            try {
                this.imageURL = new URL(ADDRESS+imageURL);
            }
            catch(MalformedURLException exc)
            {

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                InputStream is = imageURL.openStream();
                return BitmapFactory.decodeStream(is);
            }catch(IOException ioexc)
            {
                return null;
            }
        }
    }

    private class UploadTask extends AsyncTask<File, Integer, Void>
    {
        JSONObject information;
        final String boundary = "--" + Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String endpoint, filetype;

        public UploadTask(@NonNull String endpoint, @NonNull JSONObject information, String filetype)
        {
            this.information = information;
            this.endpoint = endpoint;
            this.filetype = filetype;
        }


        @Override
        protected Void doInBackground(File... files)
        {
            try {
                //Creating Objects
                url = new URL(ADDRESS+endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                //DEBUG: System.out.println("output is set");
                connection.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundary);
                connection.setRequestProperty("Cookie", session_cookie.getString("session"));
                connection.setRequestProperty("Connection", "keep-alive");

                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "utf-8"), true);

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + files[0].getName() + "\"").append(CRLF);
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(files[0].getName())).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(files[0].toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush();
            }
            catch(IOException | JSONException exc)
            {
                Utility.printError("Server.UploadTask.doInBackground", exc);
            }

            return null;
        }
    }

    /**
     * Executes an asynchronous request to the server retrieving new messages
     */
    private class MessageRetriever extends AsyncTask<String, Integer, List<JSONObject>>
    {
        private String endpointAddress = ADDRESS.concat("getmessages?Artist=");

        @Override
        protected List<JSONObject> doInBackground(String... user)
        {
            try {
                URL url = new URL(endpointAddress.concat(user[0]));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                //DEBUG: System.out.println("Request method set");

                conn.setDoInput(true);
                //DEBUG: System.out.println("input is set");

                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Cookie", session_cookie.getString("session"));

                //DEBUG: System.out.println(session_cookie.getString("session"));

                conn.setConnectTimeout(1000);
                conn.setReadTimeout(2500);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //DEBUG: System.out.println("Got inputStream: " + in);

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //DEBUG: System.out.println("Response: " + response.toString());

                conn.disconnect();

                JSONArray messages = new JSONArray(response.toString());
                //put messages in list of JSONObjects

                ArrayList<JSONObject> messageList = new ArrayList<>();

                for(int i = 0; i < messages.length(); i++)
                {
                    messageList.add(messages.getJSONObject(i));
                }

                return messageList;

            }
            catch(IOException | JSONException exc)
            {
                System.out.println("------------------------------------------------");
                System.out.println("Exception in Server.MessageRetriever.doInBackground:");
                System.out.println(exc.getMessage());
                System.out.println("------------------------------------------------");
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}