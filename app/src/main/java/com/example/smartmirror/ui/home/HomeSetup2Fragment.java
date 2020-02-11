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

public class HomeSetup2Fragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_setup2, container, false);

        //Sets up the relative view
        RelativeLayout mirrorView = root.findViewById(R.id.mirrorview2);

        //Creates a new image clock
        ImageView clockImage = root.findViewById(R.id.clock2);
        ImageView emailImage = root.findViewById(R.id.email2);

        //Size of images
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        //Size of images
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);

        //Removes views from parent to change location
        if(clockImage.getParent() != null) {
            ((ViewGroup) clockImage.getParent()).removeView(clockImage);
            if(emailImage.getParent() != null){
                ((ViewGroup) emailImage.getParent()).removeView(emailImage);
            }
        }

        //Show Images if they are enabled
        if(data.clock2Enabled){
            clockImage.setVisibility(root.VISIBLE);
            //Sets coordinates of clock image
            layoutParams.leftMargin = data.xClock2;
            layoutParams.topMargin = data.yClock2;

            mirrorView.addView(clockImage, layoutParams);
        }
        else{
            clockImage.setVisibility(root.INVISIBLE);
        }

        if(data.email2Enabled){
            emailImage.setVisibility(root.VISIBLE);
            //Sets coordinates of email image
            lp.leftMargin = data.xEmail2;
            lp.topMargin = data.yEmail2;

            mirrorView.addView(emailImage, lp);
        }
        else{
            emailImage.setVisibility(root.INVISIBLE);
        }

        //Toast.makeText(getActivity(), "Coordinates ("+ data.xClock + ", " + data.yClock + ")",Toast.LENGTH_SHORT).show();


        return root;
    }

}
