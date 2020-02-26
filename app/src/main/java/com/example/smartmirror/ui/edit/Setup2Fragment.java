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
import com.example.smartmirror.ui.edit.widgetsMenu.Widgets2Fragment;

public class Setup2Fragment extends Fragment {
    private int imageHeight = 100;
    private int imageWidth = 100;
    private ViewGroup dragdropLayout;
    private int xDelta;
    private int yDelta;
    private int[][] sets = new int[2][2];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_setup2, container, false);

        dragdropLayout = (RelativeLayout) root.findViewById(R.id.dragdrop2);

        //Layout for Image size
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);

        //Creates clock image and sets it enabled on screen
        ImageView clockImage = root.findViewById(R.id.clockImage2);
        ImageView weatherImage = root.findViewById(R.id.weatherImage2);

        //Removes views from parent to change location
        if(clockImage.getParent() != null) {
            ((ViewGroup) clockImage.getParent()).removeView(clockImage);
            if(weatherImage.getParent() != null){
                ((ViewGroup) weatherImage.getParent()).removeView(weatherImage);
            }
        }

        clockImage.setEnabled(data.clock2Enabled);
        weatherImage.setEnabled(data.weather2Enabled);

        //Change location of image
        layoutParams.leftMargin = data.xClock2;
        layoutParams.topMargin = data.yClock2;
        Log.d("Coords clock", layoutParams.leftMargin + ", " + layoutParams.topMargin);

        //Allows user to move the widget depending on if it's enabled
        if(data.clock2Enabled){
            clockImage.setVisibility(root.VISIBLE);
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
        lp.leftMargin = data.xWeather2;
        lp.topMargin = data.yWeather2;
        Log.d("Coords Weather", lp.leftMargin + ", " + lp.topMargin);

        //Allows for image to move if it's enabled
        if(data.weather2Enabled){
            dragdropLayout.addView(weatherImage, lp);
            weatherImage.setVisibility(root.VISIBLE);
            //Move the image
            weatherImage.setOnTouchListener(onTouchListener());
        }
        else{
            weatherImage.setVisibility(root.INVISIBLE);
        }


        return root;
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.main_page,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * This handles the selection of the corner drop down menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //handle menu item clicks
        int id = item.getItemId();

        if (id==R.id.action_settings){
            Fragment fragment = new Widgets2Fragment();

            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.nav_host_fragment,fragment).addToBackStack(null).commit();
        }
        else if (id==R.id.action_save){
            if(data.clock2Enabled) {
                data.xClock2 = sets[0][0];
                data.yClock2 = sets[0][1];
            }

            if(data.weather2Enabled) {
                data.xWeather2 = sets[1][0];
                data.yWeather2 = sets[1][1];
            }
            Toast.makeText(getActivity(), "Coordinates saved.",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method handles the movement of the images
     * @return
     */
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
                            case R.id.clockImage2:
                                sets[0][0] = lP.leftMargin;
                                sets[0][1] = lP.topMargin;
                                Log.d("Coordinates Set", sets[0][0] + " " + sets[0][1]);
                                break;
                            case R.id.weatherImage2:
                                sets[1][0] = lP.leftMargin;
                                sets[1][1] = lP.topMargin;
                                Log.d("Coordinates Set", sets[1][0] + " " + sets[1][1]);
                                break;
                        }
                }
                dragdropLayout.invalidate();
                return true;
            }
        };
    }
}
