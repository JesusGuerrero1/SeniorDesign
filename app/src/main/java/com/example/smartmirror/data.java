package com.example.smartmirror;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

public class data extends Application {
//Before SQL connection sqlData.class
//AWS mySQL database username: smartmirror
//
//password: hksahfih18$?

    private static final String ROOT_URL = "http://192.168.101.1/HeroApi/v1/Api.php?apicall=";

    //  Store data in an array [number of widgets][4 properties]
    //  widgets [widget number] [Page Number, enabled 0 = false/1 = true, x coordinate, y coordinate]

    //public static int [][] widgets = new int [2][4];

    //SETUP 1 VARIABLES

    //Enables widgets on the screen
    public static boolean clockEnabled = false;
    public static boolean emailEnabled = false;

    //X,Y coordinates for widgets
    public static int xClock = 0;
    public static int yClock = 0;
    public static int xEmail = 700;
    public static int yEmail = 750;

    //SETUP 2 VARIABLES

    //Enables widgets on the screen
    public static boolean clock2Enabled = false;
    public static boolean email2Enabled = false;

    //X,Y coordinates for widgets
    public static int xClock2 = 600;
    public static int yClock2 = 750;
    public static int xEmail2 = 700;
    public static int yEmail2 = 750;

    //SETUP 3 VARIABLES

    //Enables widgets on the screen
    public static boolean clock3Enabled = false;
    public static boolean email3Enabled = false;

    //X,Y coordinates for widgets
    public static int xClock3 = 600;
    public static int yClock3 = 750;
    public static int xEmail3 = 700;
    public static int yEmail3 = 750;

    //Flags for saving views
    public static boolean Setup1Saved = false;
    public static boolean Setup2Saved = false;
    public static boolean Setup3Saved = false;


    //For testing SQL Get and Send buttons
    public static int x = 150;
    public static int y = 300;

    //Responses from getting data from the SQL server
    public static String response = "No Response";
    public static String coordinates = "No coordinates";

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

