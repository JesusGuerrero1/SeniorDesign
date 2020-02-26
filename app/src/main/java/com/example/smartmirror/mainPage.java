package com.example.smartmirror;
//MAIN PAGE
//This starts the app

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

public class mainPage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    GoogleSignInClient mGoogleSignInClient;
    TextView nameHeader;
    TextView emailHeader;
    ImageView photoHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main_page);

        data.setMirrorDimensions(this);
        Log.i("Margins", data.mirrorHeight + "*" + data.mirrorWidth);


        //Add the toolbar on the top of the page
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* This is a floating action button on the bottom right of the corner
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); //Here go the actions for it
            }
        });*/

        //This is the side bar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_edit, R.id.nav_profile, R.id.nav_app)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(mainPage.this);
        if (acct != null) {
            data.personName = acct.getDisplayName();
            data.personGivenName = acct.getGivenName();
            data.personFamilyName = acct.getFamilyName();
            data.personEmail = acct.getEmail();
            data.personId = acct.getId();
            data.personPhoto = acct.getPhotoUrl();


            photoHeader = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderImage);
            nameHeader = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderName);
            emailHeader = navigationView.getHeaderView(0).findViewById(R.id.NavHeaderEmail);

            Glide.with(this).load(data.personPhoto).into(photoHeader);
            nameHeader.setText(data.personName);
            emailHeader.setText(data.personEmail);
        }
        Log.e("Id",data.personId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Returns to home Fragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
