package com.example.smartmirror.ui.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartmirror.R;
import com.example.smartmirror.data;
import com.example.smartmirror.sqlData;

public class AppFragment extends Fragment {

    private AppViewModel appViewModel;
    private String cX, cY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appViewModel =ViewModelProviders.of(this).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_app, container, false);

        //Button for getting data and sending data
        Button getBtn = root.findViewById(R.id.getButton);
        Button sendBtn = root.findViewById(R.id.sendButton);

        //Button for opening camera to take pictures
        Button pictureBtn = root.findViewById(R.id.imagesBtn);

        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new GalleryFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment,fragment).addToBackStack(null).commit();
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new sqlData(getContext(), data.x,data.y,0).execute();
                Toast.makeText(getActivity(),data.coordinates,Toast.LENGTH_SHORT).show();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cX = data.x + "";
                cY = data.y + "";
                new sqlData(getContext(),data.x,data.y,1).execute(cX,cY);
                Toast.makeText(getActivity(),data.coordinates,Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}