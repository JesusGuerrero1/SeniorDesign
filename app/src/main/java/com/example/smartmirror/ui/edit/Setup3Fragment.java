package com.example.smartmirror.ui.edit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.smartmirror.R;
import com.example.smartmirror.data;
import com.example.smartmirror.ui.edit.widgetsMenu.Widgets3Fragment;

public class Setup3Fragment extends Fragment {
    private int imageHeight = 100;
    private int imageWidth = 100;
    private ViewGroup dragdropLayout;
    private int xDelta;
    private int yDelta;
    private int[][] sets = new int[2][2];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_setup3, container, false);

        dragdropLayout = (RelativeLayout) root.findViewById(R.id.dragdrop3);

        //Layout for Image size
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);

        //Creates clock image and sets it enabled on screen
        ImageView clockImage = root.findViewById(R.id.clockImage3);
        ImageView emailImage = root.findViewById(R.id.emailImage3);

        //Removes views from parent to change location
        if(clockImage.getParent() != null) {
            ((ViewGroup) clockImage.getParent()).removeView(clockImage);
            if(emailImage.getParent() != null){
                ((ViewGroup) emailImage.getParent()).removeView(emailImage);
            }
        }

        clockImage.setEnabled(data.clock3Enabled);
        emailImage.setEnabled(data.email3Enabled);

        //Change location of image
        layoutParams.leftMargin = data.xClock3;
        layoutParams.topMargin = data.yClock3;
        Log.e("Coords clock", layoutParams.leftMargin + ", " + layoutParams.topMargin);

        //Allows user to move the widget depending on if it's enabled
        if(data.clock3Enabled){
            clockImage.setVisibility(root.VISIBLE);
            Log.e("Coords clock", layoutParams.leftMargin + ", " + layoutParams.topMargin);
            dragdropLayout.addView(clockImage, layoutParams);
            //Move the image
            clockImage.setOnTouchListener(onTouchListener());
        }
        else{
            clockImage.setVisibility(root.INVISIBLE);
        }

        //Layout for Image size
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);
        //Change location of image
        lp.leftMargin = data.xEmail3;
        lp.topMargin = data.yEmail3;
        Log.e("Coords email", lp.leftMargin + ", " + lp.topMargin);

        //Allows for image to move if it's enabled
        if(data.email3Enabled){
            dragdropLayout.addView(emailImage, lp);
            emailImage.setVisibility(root.VISIBLE);
            //Move the image
            emailImage.setOnTouchListener(onTouchListener());
        }
        else{
            emailImage.setVisibility(root.INVISIBLE);
        }


        return root;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.main_page,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //handle menu item clicks
        int id = item.getItemId();

        if (id==R.id.action_settings){
            Fragment fragment = new Widgets3Fragment();

            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.nav_host_fragment,fragment).addToBackStack(null).commit();
        }
        else if (id==R.id.action_save){
            if(data.clock3Enabled) {
                data.xClock3 = sets[0][0];
                data.yClock3 = sets[0][1];
                Log.e("Coords saved", data.xClock3 + ", " + data.yClock3);
            }

            if(data.email3Enabled) {
                data.xEmail3 = sets[1][0];
                data.yEmail3 = sets[1][1];
                Log.e("Coords saved", data.xEmail3 + ", " + data.yEmail3);
            }
            data.Setup3Saved = true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Handles the movement of an image
    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {



            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(final View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //Move the image around
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 1080 - x - xDelta - imageWidth;
                        layoutParams.bottomMargin = 1920 - y - yDelta - imageHeight;

                        if(layoutParams.leftMargin < 0){
                            layoutParams.leftMargin = 0;
                        }
                        else if(layoutParams.leftMargin > 1080 - imageWidth){
                            layoutParams.leftMargin = 1080 - imageWidth;
                        }

                        if(layoutParams.topMargin < 0){
                            layoutParams.topMargin = 0;
                        }
                        else if(layoutParams.topMargin > 1920 - imageHeight){
                            layoutParams.topMargin = 1920 - imageHeight;
                        }
                        view.setLayoutParams(layoutParams);
                        break;

                    case MotionEvent.ACTION_UP:
                        //After letting go of image, save it's coordinates
                        RelativeLayout.LayoutParams lP = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();

                        switch (view.getId()){
                            case R.id.clockImage3:
                                sets[0][0] = lP.leftMargin;
                                sets[0][1] = lP.topMargin;
                                Toast.makeText(getActivity(), "Coordinates ("+ sets[0][0] + ", " + sets[0][1] + ") set",Toast.LENGTH_SHORT).show();
                            case R.id.emailImage3:
                                sets[1][0] = lP.leftMargin;
                                sets[1][1] = lP.topMargin;
                                Toast.makeText(getActivity(), "Coordinates ("+ sets[1][0] + ", " + sets[1][1] + ") set",Toast.LENGTH_SHORT).show();
                        }
                }
                dragdropLayout.invalidate();
                return true;
            }
        };
    }
}
