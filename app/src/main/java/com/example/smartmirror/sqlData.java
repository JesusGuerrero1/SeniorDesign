package com.example.smartmirror;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class sqlData extends AsyncTask<String, Void, String> {
    private ProgressDialog pDialog;
    private String[] entries;
    private Context context;
    private int sqlMethod = 0;
    private boolean returnedData = false;
    private String IP = "10.72.64.142";
    //private String ROOT_IP = "http://10.74.119.121/";


    public sqlData(Context context, String[] dataEntry, int flag) {
        this.context = context;
        this.entries = dataEntry;
        sqlMethod = flag; //flag 0 means get and 1 means post.(By default it is get.)
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        //means by Get Method
        if (sqlMethod == 0) {
            try {
                //Connect to php page
                //String link = ROOT_IP + "getData.php";

                String link = "http://" + IP + "/android/getData.php";

                URL url = new URL(link);
                try {
                    //Connect to database client
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);

                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    //Read file
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();

                    //Return strings
                    Log.e("String", sb.toString());
                    returnedData = true;
                    return sb.toString();
                } catch (Exception e){
                    Log.e("Exception", e.getMessage());
                    return ("Exception: " + e.getMessage());
                }

            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                return ("Exception: " + e.getMessage());
            }
        } else if(sqlMethod == 1) {
            try {
                String userId = arg0[0];
                String link = "http://" + IP + "/android/updateCoordinates.php";
//                String link = ROOT_IP + "insertData.php";

                try {
                    // Building Parameters
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(link);

                    try {
                        List<NameValuePair> NameValuePair = new ArrayList<>();

                        //This sends data
                        NameValuePair.add(new BasicNameValuePair("userId", userId));
                        NameValuePair.add(new BasicNameValuePair("clkState", entries[0]));
                        NameValuePair.add(new BasicNameValuePair("clkX", entries[1]));
                        NameValuePair.add(new BasicNameValuePair("clkY", entries[2]));
                        NameValuePair.add(new BasicNameValuePair("wthState", entries[3]));
                        NameValuePair.add(new BasicNameValuePair("wthX", entries[4]));
                        NameValuePair.add(new BasicNameValuePair("wthY", entries[5]));
                        post.setEntity(new UrlEncodedFormEntity(NameValuePair));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                                response.getEntity().getContent()));

                        StringBuffer sb = new StringBuffer("");
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            System.out.println(line);
                            sb.append(line);
                        }
                        return sb.toString();
                    } catch (Exception e) {
                        Log.e("Try 2", e + "");
                        e.printStackTrace();
                    }
                } catch (Exception e){
                    Log.e("Try 1", e + "");
                }
                return null;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
        else if(sqlMethod == 2) {
            try {
                String userId = arg0[0];
                String link = "http://" + IP + "/android/checkUser.php";

                URL url = new URL(link);
                try {
                    // Building Parameters
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(link);

                    try {
                        List<NameValuePair> NameValuePair = new ArrayList<>();

                        //This sends data
                        NameValuePair.add(new BasicNameValuePair("userId", userId));
                        post.setEntity(new UrlEncodedFormEntity(NameValuePair));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                                response.getEntity().getContent()));

                        StringBuffer sb = new StringBuffer("");
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            System.out.println(line);
                            sb.append(line);
                        }
                        return sb.toString();
                    } catch (Exception e) {
                        Log.e("Try 2", e + "");
                        e.printStackTrace();
                    }
                } catch (Exception e){
                    Log.e("Try 1", e + "");
                }
                return null;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
     return new String("Nothing Happened");
    }

    @Override
    protected void onPostExecute(String end) {
        pDialog.dismiss();
        data.response = end;
        if(returnedData){
            end = "Data Received";
            returnedData = false;
        }
        switch(end)
        {
            case "new":
            case "returning":
                break;
            default:
                Toast.makeText(context,end,Toast.LENGTH_SHORT).show();
        }
    }
}