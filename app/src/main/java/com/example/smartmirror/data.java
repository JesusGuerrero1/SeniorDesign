package com.example.smartmirror;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

public class data extends Application {

    //UNIQUE_ID    |   clock_state   |   clockX  |  clockY  |   weather_state   |   weatherX    |   weatherY    |   greeting_state  |   greetingX   |   greetingY   |   currently_used
    //uniqueId         clockEnabled      clockX     clockY      weatherEnabled      weatherX        weatherY        greetingEnabled     greetingX       greetingY

    public static String [] page1Data = new String [9];
    public static String [] emailWidget1 = new String [4];

    public static void parseData(String [] p, boolean clkEnabled, int clkX, int clkY, boolean wthEnabled, int wthX, int wthY){
        //Turn all data into a string array
        if(clkEnabled){
            p[0] = 1+"";
        }else{
            p[0] = 0+"";
        }
        p[1] = clkX+"";
        p[2] = clkY+"";

        if(wthEnabled){
            p[3] = 1 + "";
        }else{
            p[3] = 0 + "";
        }
        p[4] = wthX + "";
        p[5] = wthY + "";
    }

    public static void parseReceivedData(String gotData){
        String delims = "[,]";
        String [] tokens = gotData.split(delims);
        Log.e("Tokens",tokens[0]);
        Log.e("Tokens",tokens[1]);
        Log.e("Tokens",tokens[2]);
        Log.e("Tokens",tokens[3]);
        if(tokens[0].equals("1")){
            data.clockEnabled = true;
        }else{
            data.clockEnabled = false;
        }

        data.xClock = Integer.parseInt(tokens[1]);
        data.yClock = Integer.parseInt(tokens[2]);

        if(tokens[3].equals("1")){
            data.weatherEnabled = true;
        }else{
            data.weatherEnabled = false;
        }

        data.xWeather = Integer.parseInt(tokens[4]);
        data.yWeather = Integer.parseInt(tokens[5]);
    }


    public static void initVars(){
        //Enables widgets on the screen
        clockEnabled = false;
        weatherEnabled = false;

        //X,Y coordinates for widgets
        xClock = 0;
        yClock = 0;
        xWeather = 200;
        yWeather = 0;
    }


    //PAGE 1 VARIABLES

    //Enables widgets on the screen
    public static boolean clockEnabled = false;
    public static boolean weatherEnabled = false;

    //X,Y coordinates for widgets
    public static int xClock = 0;
    public static int yClock = 0;
    public static int xWeather = 200;
    public static int yWeather = 0;

    //PAGE 2 VARIABLES

    //Enables widgets on the screen
    public static boolean clock2Enabled = false;
    public static boolean weather2Enabled = false;

    //X,Y coordinates for widgets
    public static int xClock2 = 0;
    public static int yClock2 = 0;
    public static int xWeather2 = 200;
    public static int yWeather2 = 0;

    //PAGE 3 VARIABLES

    //Enables widgets on the screen
    public static boolean clock3Enabled = false;
    public static boolean weather3Enabled = false;

    //X,Y coordinates for widgets
    public static int xClock3 = 0;
    public static int yClock3 = 0;
    public static int xWeather3 = 200;
    public static int yWeather3 = 0;


    //Responses from getting data from the SQL server
    public static String response = "No Response";

    //Data from user Google profile
    public static String personName;
    public static String personGivenName;
    public static String personFamilyName;
    public static String personEmail;
    public static String personId;
    public static Uri personPhoto;



    //Height and width of mirror view
    public static int mirrorHeight = 20;
    public static int mirrorWidth = 20;

    public static void setMirrorDimensions (Context context){
        int dpH,dpW;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        Log.i("Screen Density: ", logicalDensity+"");
        dpH = (int)Math.ceil(1920 / logicalDensity);
        dpW = (int)Math.ceil(1080 / logicalDensity);

        mirrorHeight = dpH;
        mirrorWidth = dpW;
    }
}

