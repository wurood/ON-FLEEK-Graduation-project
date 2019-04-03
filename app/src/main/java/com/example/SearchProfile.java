package com.example;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by wurood on 10/7/2017.
 */

public class SearchProfile extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView profile_nav_ser;
    private ImageView profileser_image, follow, add_catn;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private String data, user_name_ser, profile_image_url, category;
    private TextView proser_name, proser_email;
    private Toolbar myToolbar;
    private Intent in;
    private Bitmap bitmap;
    private int UserID, FollowerID;
    private Typeface font1, font2;
    private ProgressDialog progressDialog;
    private FirebaseAuth mauth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
        mauth = FirebaseAuth.getInstance();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, new event()).commit();
        profile_nav_ser = (BottomNavigationView) findViewById(R.id.pronav_proser);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        in = getIntent();
        proser_name = (TextView) findViewById(R.id.etxt_proser_name);
        proser_email = (TextView) findViewById(R.id.etxt_proser_desc);
        add_catn = (ImageView) findViewById(R.id.img_category);
        profileser_image = (ImageView) findViewById(R.id.img_proser);
        follow = (ImageView) findViewById(R.id.btn_follow);
        profile_image_url = SharedPreSearch.getInstance(this).getUserimg();
        FollowerID = SharedPreSearch.getInstance(this).getUserid();
        UserID = SharedPrefManager.getInstance(this).getUserid();
        category = SharedPrefManager.getInstance(this).getUserCategory();
        font1 = Typeface.createFromAsset(SearchProfile.this.getAssets(), "fonts/Montserrat-Bold.ttf");
        font2 = Typeface.createFromAsset(SearchProfile.this.getAssets(), "fonts/Montserrat-Regular.ttf");
        Picasso.with(this).load(profile_image_url).into(profileser_image);
        getfollow();
        in = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        setSupportActionBar(myToolbar);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        proser_name.setText(SharedPreSearch.getInstance(this).getUsername());
        proser_email.setText(SharedPreSearch.getInstance(this).getUserEmail());
        proser_name.setTypeface(font1);
        user_name_ser = SharedPrefManager.getInstance(this).getUsername().trim();
        follow.setOnClickListener(this);
        if (category.equals("personal")) {
            add_catn.setImageResource(R.drawable.ic_person_pro);

        } else if (category.equals("education")) {
            add_catn.setImageResource(R.drawable.ic_edu);

        } else if (category.equals("business")) {
            add_catn.setImageResource(R.drawable.ic_busniess);

        } else if (category.equals("party")) {
            add_catn.setImageResource(R.drawable.ic_pf);

        } else if (category.equals("cultural")) {
            add_catn.setImageResource(R.drawable.ic_cal);

        } else if (category.equals("food")) {
            add_catn.setImageResource(R.drawable.ic_food);

        } else if (category.equals("music")) {
            add_catn.setImageResource(R.drawable.ic_music);

        } else if (category.equals("art")) {
            add_catn.setImageResource(R.drawable.ic_art);

        } else if (category.equals("festival")) {
            add_catn.setImageResource(R.drawable.ic_pf);

        } else if (category.equals("health")) {
            add_catn.setImageResource(R.drawable.ic_doc);

        }

        profile_nav_ser.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int id = item.getItemId();

                if (id == R.id.Calender) {

                    transaction.replace(R.id.content_frame, new calender()).commit();

                } else if (id == R.id.Events) {
                    transaction.replace(R.id.content_frame, new event()).commit();

                } else if (id == R.id.Settings) {
                    transaction.replace(R.id.content_frame, new event()).commit();

                }

                return true;
            }

        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profilemenue, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.home:

                startActivity(new Intent(this, home_activity.class));
                return true;
            case R.id.search:

                startActivity(new Intent(this, search.class));
                return true;

        }
        return true;

    }

    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), user_name_ser, Toast.LENGTH_SHORT).show();

    }

    private void follow() {

        sendNotification();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Follow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(SearchProfile.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(SearchProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("userid", String.valueOf(UserID));
                params.put("followerid", String.valueOf(FollowerID));

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);


        getfollow();
    }

    private void unfollow() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UNFollow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(SearchProfile.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(SearchProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("userid", String.valueOf(UserID));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        getfollow();

    }

    private void getfollow() {


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.URL_getFollow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray array = new JSONArray(response);

                            Log.v("test", array.toString());

                            check(array);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", String.valueOf(UserID));

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);
    }

    private void check(JSONArray array) {
        if (array.length() == 0) {

            Drawable res = getResources().getDrawable(R.drawable.ic_follow);
            follow.setImageDrawable(res);
            follow.setTag(R.drawable.ic_follow);
        } else {
            for (int i = 0; i < array.length(); i++) {
                try {
                    if (FollowerID == array.getInt(i)) {
                        Drawable res = getResources().getDrawable(R.drawable.ic_unfollow);
                        follow.setImageDrawable(res);
                        follow.setTag(R.drawable.ic_unfollow);
                    } else {
                        Drawable res = getResources().getDrawable(R.drawable.ic_follow);
                        follow.setImageDrawable(res);
                        follow.setTag(R.drawable.ic_follow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public void onClick(View v) {
        if (v == follow) {

            if ((Integer) (follow.getTag()) == R.drawable.ic_follow) {
                follow();
            } else {

                unfollow();
            }


        }

    }



    private void sendNotification() {
     final    String msg = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (MainActivity.LoggedIn_User_Email.equals(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail())) {
                        send_email = SharedPreSearch.getInstance(getApplicationContext()).getUserEmail();
                    } else {
                        send_email = SharedPrefManager.getInstance(getApplicationContext()).getUserEmail();
                    }
                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic Yzc3ZWRjZTktMmNjMC00YTYxLTkyODktOWI5NDQyODhhZTRi");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"de564fca-8862-48c8-82fe-096a34b33455\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"

                                + "\"contents\": {\"en\": \""+msg+ " followed you\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });


    }
}


