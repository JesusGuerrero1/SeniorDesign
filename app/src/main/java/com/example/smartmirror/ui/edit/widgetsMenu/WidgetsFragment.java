package com.example.smartmirror.ui.edit.widgetsMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartmirror.R;
import com.example.smartmirror.data;
import com.example.smartmirror.ui.edit.EditFragment;

public class WidgetsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_widgets, container, false);

        // initiate view's
        ImageButton addClockBtn = root.findViewById(R.id.addClock);
        ImageButton addWeatherBtn = root.findViewById(R.id.addWeather);



        // perform click event on button's
        addClockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!data.clockEnabled){
                    data.clockEnabled = true;
                    Toast.makeText(getActivity(),"Added Clock",Toast.LENGTH_LONG).show();// display the toast on home button click
                }
                else{
                    data.clockEnabled = false;
                    Toast.makeText(getActivity(),"Removed Clock",Toast.LENGTH_LONG).show();// display the toast on home button click
                }
                switchFragment();
            }
        });
        addWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!data.weatherEnabled){
                    data.weatherEnabled = true;
                    Toast.makeText(getActivity(),"Added Email",Toast.LENGTH_LONG).show();// display the toast on you tube button click
                }
                else{
                    data.weatherEnabled = false;
                    Toast.makeText(getActivity(),"Removed Email",Toast.LENGTH_LONG).show();// display the toast on you tube button click
                }
                switchFragment();
            }
        });


        return  root;
    }

    private  void switchFragment(){

        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new EditFragment()).addToBackStack(null).commit();


        //getFragmentManager().beginTransaction()
        //        .replace(R.id.nav_host_fragment, new EditFragment())
        //        .addToBackStack(null)
        //        .commit();
    }

}
