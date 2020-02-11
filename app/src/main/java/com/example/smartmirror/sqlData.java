package com.example.smartmirror;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class sqlData extends AsyncTask <String, Void, String>{
    private int xpos,ypos;
    private Context context;
    private int byGetOrPost = 0;
    public static int x = 0;
    public static int y = 0;
    JSONParser jsonParser = new JSONParser();



    public sqlData(Context context, int xpos, int ypos, int flag) {
        this.context = context;
        this.xpos = xpos;
        this.ypos = ypos;
        byGetOrPost = flag; //flag 0 means get and 1 means post.(By default it is get.)
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        //means by Get Method
        if(byGetOrPost == 0){

            try{
                //Connect to php page
                String link = "http://10.74.119.206/get_record.php";

                URL url = new URL(link);

                //Connect to database client
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                //Read file
                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();

                //Return strings
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        } else{
            try{
                String dataX = (String)arg0[0];
                String dataY = (String)arg0[1];
                String link = "http://10.74.119.206/insert.php";


                // Building Parameters
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(link);
                try {
                    List<NameValuePair> NameValuePair = new ArrayList<>();
                    NameValuePair.add(new BasicNameValuePair("coordinatesX", dataX));
                    NameValuePair.add(new BasicNameValuePair("coordinatesY", dataY));
                    post.setEntity(new UrlEncodedFormEntity(NameValuePair));

                    HttpResponse response = client.execute(post);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }


            /**
            //Input stream
            InputStream is;
            String result;
            String line;
            int code;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

            //Convert arguments to strings
            String dataX = (String)arg0[0];
            String dataY = (String)arg0[1];

            //Adds strings to a list where they are included with their column name
            nameValuePairs.add(new BasicNameValuePair("coordinatesX",dataX));
            nameValuePairs.add(new BasicNameValuePair("coordinatesY",dataY));

            Log.e("pass 0",dataX + " " + dataY);
            try
            {
                //Connect to the database
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://10.74.119.206/insert.php"); //Link to php insert script
                httppost.setEntity((new UrlEncodedFormEntity(nameValuePairs, "utf-8")));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            }catch(Exception e)
            {
                return new String("Exception: " + e.toString());
            }
            //Convert all the data to a string
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    Log.e("pass 4",line);

                }
                is.close();
                result = sb.toString().trim();
                Log.e("pass 2", "connection success ");
            }
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
            }

            //Create a json string to send the data
            try
            {
                Log.e("pass 3",result + "");
                JSONObject json_data = new JSONObject(result);
                code=(json_data.getInt("code"));

                if(code==1)
                {
                    return new String("Inserted Successfully");
                }
                else
                {
                    return new String("Unsuccessfully");
                }
            }
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
            }**/

        }
    }

    @Override
    protected void onPostExecute(String end){
       data.response = "Login Successful";
       data.coordinates = end;
    }
}

/**
public class sqlData extends Application {
    //Before SQL connection sqlData.class
    //AWS mySQL database username: smartmirror
    //
    //password: hksahfih18$?

    public static int x = 0;
    public static int y = 0;
}
 **/
