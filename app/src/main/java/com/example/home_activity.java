package com.example;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rp.listview.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;


public class home_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private TextView name ;
    private ImageView profile_img;
    private NavigationView navigationView ;
    private  String profile_img_url;
    private View header_View;
    private DrawerLayout drawer;
    public static Context contextOfApplication;
    String flag;
    Menu menu;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header_View =  navigationView.getHeaderView(0);
        profile_img = (ImageView)header_View.findViewById(R.id.imageView);

        contextOfApplication = getApplicationContext();

        setSupportActionBar(toolbar);





        profile_img_url=Constants.EVENT_IMGS+"/"+SharedPrefManager.getInstance(this).getUserimg();
        Picasso.with(this).load(Constants.EVENT_IMGS+SharedPrefManager.getInstance(this).getUserimg()).error(R.drawable.default_user).into(profile_img);
        name = (TextView) header_View.findViewById(R.id.userrname);
        name.setText(SharedPrefManager.getInstance(this).getUsername());
        res= getIntent().getStringExtra("EXTRA_SESSION_ID");
        if(res.equals("false")){
            res="false";
        }
        else if(res.equals("true")){
            flag="true";
            forceRTLIfSupported();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment mFragment = null;
        mFragment = new home();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_frame, mFragment).commit();


    }



    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profilemenue, menu);
        this.menu = menu;
        menu.findItem(R.id.home).setIcon(R.drawable.ic_profile);
        if(res.equals("true")) {
            menu.findItem(R.id.logout).setIcon(R.drawable.ic_back2);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.logout:

                SharedPrefManager.getInstance(this).logout();
                finish();
                Intent ii = new Intent(this, LoginActivity.class);
                ii.putExtra("EXTRA_SESSION_ID", flag);
                startActivity(ii);
                return true;
            case R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("EXTRA_SESSION_ID", flag);

                startActivity(i);
                return true;
            case R.id.search:

                startActivity(new Intent(this, search.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.nav_edit_layout:
                fragment=new editprofile();
                break;
            case R.id.nav_scan:
                fragment=new scanQR();
                break;
           case R.id.nav_map:

                fragment=new Language_();
                break;
            case R.id.nav_tickets:

                fragment=new tickets();
                break;

            case R.id.nav_lan:
                fragment=new Language_();
                break;
            case R.id.nav_calen:
                fragment=new Calendar();
                break;


        }
        if(fragment!= null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.home_frame,fragment);
            ft.commit();


        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        displaySelectedScreen(id);
        Log.d("FragmentList",getSupportFragmentManager().getFragments().toString());
        return true;
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Result" , "reqCode : " + requestCode + ", resultCode" + resultCode);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {


                String event_id =  result.getContents();

                Intent intent = new Intent(home_activity.this, register_event.class);
                intent.putExtra("message",event_id);
                intent.putExtra("source",0);
                startActivity(intent);

            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

}

