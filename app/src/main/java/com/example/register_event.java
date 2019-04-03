package com.example;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class register_event extends AppCompatActivity {
    public String event_id,string_image, ticketid , source;
    private ImageView follow_event,eve_img;
    public int id,src;
    private Bitmap bitmap;
    SimpleDateFormat curFormater ;

    private TextView event_name, event_owner, event_address, event_desc, event_start_date, event_start_time, event_end_date, event_end_time,
            event_type, event_seats, event_country;
    private Integer UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserID = SharedPrefManager.getInstance(this).getUserid();
        event_owner = (TextView) findViewById(R.id.event_3);
        event_name = (TextView) findViewById(R.id.event_2);
        event_address = (TextView) findViewById(R.id.event_14);
        event_desc = (TextView) findViewById(R.id.event_4);
        event_end_time = (TextView) findViewById(R.id.event_10);
        event_type = (TextView) findViewById(R.id.event_12);
        event_end_date = (TextView) findViewById(R.id.event_9);
        event_seats = (TextView) findViewById(R.id.event_13);
        event_start_time = (TextView) findViewById(R.id.event_8);
        event_start_date = (TextView) findViewById(R.id.event_6);
        event_country = (TextView) findViewById(R.id.event_11);
        follow_event = (ImageView) findViewById(R.id.event_follow);
        eve_img = (ImageView) findViewById(R.id.eve_img);

        get_id();
        Bundle bundle = getIntent().getExtras();
       event_id = bundle.getString("message");
       source= bundle.getString("source");
       Log.v("oko",event_id);
        id = Integer.valueOf(event_id);
        src=Integer.valueOf(source);


        getData();

        getfollowevent();

        follow_event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((Integer) (follow_event.getTag()) == R.drawable.ic_event_add) {
                    follow();
                    } else {

                    unfollow();
                }


            }
        });


    }


    private void getData() {
        curFormater = new SimpleDateFormat("yyyy-MM-dd");

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.EVENT_GET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
       Log.v("uuuu",response);
                            event_name.setText(obj.getString("title"));
                            event_address.setText(obj.getString("address"));
                            event_desc.setText(obj.getString("desc"));
                            event_end_time.setText(obj.getString("end_tm"));
                            event_type.setText(obj.getString("type"));
                            event_end_date.setText(obj.getString("end_dt"));
                            event_seats.setText(obj.getString("seats"));
                            event_start_time.setText(obj.getString("start_tm"));
                        //    event_start_date.setText(obj.getString("start_dt"));
                            event_owner.setText("BY "+obj.getString("owner") );
                            event_country.setText(obj.getString("city"));
                            String url = Constants.EVENT_IMGS+"/"+obj.getString("img");
                            Picasso.with(getApplicationContext())
                                    .load(url)
                                    .fit()
                                    .tag(getApplicationContext())
                                    .into(eve_img);
                          //  Toast.makeText(register_event.this, obj.getString("start_dt"), Toast.LENGTH_LONG).show();
                            printDifference(curFormater.parse(obj.getString("start_dt")),curFormater.parse(obj.getString("end_dt")));


                        } catch (JSONException e) {

                            e.printStackTrace();
                        } catch (ParseException e) {
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
                Log.v("oko",event_id);
                params.put("id", event_id);
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);

    }

    public String get_id() {

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, Constants.EVENT_TKT_UPLOAD_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("message");
                            print(response);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(register_event.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );


        queue.add(getRequest);


        return ticketid;
    }


    private void follow() {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(ticketid, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_EVENT_Follow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                      //  Toast.makeText(register_event.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(register_event.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                string_image = getStringImage(bitmap);
                params.put("userid", String.valueOf(UserID));
                params.put("eventid", String.valueOf(event_id));
                params.put("QR",string_image);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest2);

            Log.i("SendMailActivity", "Send Button Clicked.");

            String fromEmail = "onfleekapp2017@gmail.com";
            String fromPassword = "onfleek12345";

            String toEmails = SharedPrefManager.getInstance(this).getUserEmail();
            List<String> toEmailList = Arrays.asList(toEmails
                    .split("\\s*,\\s*"));
            Log.i("SendMailActivity", "To List: " + toEmailList);
            String emailSubject ="Event Registration";
             String emailBody = "click here for your ticket"+event_name.getText()+Constants.EVENT_GET_TKTS+UserID+event_id+".png";
            new SendMailTask(register_event.this).execute(fromEmail,
                    fromPassword, toEmailList, emailSubject, emailBody);

        getfollowevent();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void unfollow() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EVENT_UNFollow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(register_event.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      //  Toast.makeText(register_event.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("userid", String.valueOf(UserID));
                params.put("eventid", String.valueOf(event_id));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        getfollowevent();

    }

    private void getfollowevent() {


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.URL_get_EVENT_Follow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray array = new JSONArray(response);

                            check(array);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {

                     //   Toast.makeText(register_event.this, "hiiiii", Toast.LENGTH_LONG).show();

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

    public void check(JSONArray array) {

        if (src == 0) {

            if (array.length() == 0) {
                Drawable res = getResources().getDrawable(R.drawable.ic_event_add);
                follow_event.setImageDrawable(res);
                follow_event.setTag(R.drawable.ic_event_add);
            } else {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        if (id == array.getInt(i)) {
                            Drawable res = getResources().getDrawable(R.drawable.ic_event_delete);
                            follow_event.setImageDrawable(res);
                            follow_event.setTag(R.drawable.ic_event_delete);
                        } else {
                            Drawable res = getResources().getDrawable(R.drawable.ic_event_add);
                            follow_event.setImageDrawable(res);
                            follow_event.setTag(R.drawable.ic_event_add);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
     else {

            follow_event.setEnabled(false);
            follow_event.setTag(R.drawable.ic_edit);
        }

    }

    private void print(JSONObject id) {
        try {
            ticketid = id.getString("message");

            // Toast.makeText(register_event.this,ticket, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }
    private void printDifference(Date startDate, Date endDate) {
       // Toast.makeText(register_event.this, "giiiiiii", Toast.LENGTH_LONG).show();
        Calendar c = Calendar.getInstance();
        Date d =  c.getTime();


        long differentst = startDate.getTime()-d.getTime();
        long differented = endDate.getTime() - d.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = differentst / daysInMilli;
        differentst = differentst % daysInMilli;

        long elapsedHours = differentst / hoursInMilli;
        differentst = differentst % hoursInMilli;

        long elapsedMinutes = differentst / minutesInMilli;
        differentst = differentst % minutesInMilli;

        long elapsedSeconds = differentst / secondsInMilli;


        long elapsedDaysed = differented / daysInMilli;
        differented = differented % daysInMilli;

        long elapsedHoursed = differented / hoursInMilli;
        differented = differented % hoursInMilli;

        long elapsedMinutesed = differented / minutesInMilli;
        differented = differented % minutesInMilli;

        long elapsedSecondsed = differented / secondsInMilli;
        event_start_date.setText(String.valueOf(elapsedDays));
//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
//

    }

}
