package com.example.smartmirror.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartmirror.R;
import com.example.smartmirror.data;


public class HomeSetup1Fragment extends Fragment {



    private String cX, cY;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_setup1, container, false);

        //Sets up the relative view
        RelativeLayout mirrorView = root.findViewById(R.id.mirrorview1);

        //Creates a new image clock
        ImageView clockImage = root.findViewById(R.id.clock1);
        ImageView weatherImage = root.findViewById(R.id.weather1);

        //Size of images
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        //Size of images
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);

        //Removes views from parent to change location
        if(clockImage.getParent() != null) {
            ((ViewGroup) clockImage.getParent()).removeView(clockImage);
            if(weatherImage.getParent() != null){
                ((ViewGroup) weatherImage.getParent()).removeView(weatherImage);
            }
        }

        //Show Images if they are enabled
        if(data.clockEnabled){
            clockImage.setVisibility(root.VISIBLE);
            //Sets coordinates of clock image
            layoutParams.leftMargin = data.xClock;
            layoutParams.topMargin = data.yClock;

            mirrorView.addView(clockImage, layoutParams);
        }
        else{
            clockImage.setVisibility(root.INVISIBLE);
        }

        if(data.weatherEnabled){
            weatherImage.setVisibility(root.VISIBLE);
            //Sets coordinates of email image
            lp.leftMargin = data.xWeather;
            lp.topMargin = data.yWeather;

            mirrorView.addView(weatherImage, lp);
        }
        else{
            weatherImage.setVisibility(root.INVISIBLE);
        }

        //Toast.makeText(getActivity(), "Coordinates ("+ data.xClock + ", " + data.yClock + ")",Toast.LENGTH_SHORT).show();


        return root;
    }

}
