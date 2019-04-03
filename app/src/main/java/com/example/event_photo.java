package com.example;


import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;

import android.graphics.Color;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class event_photo extends AppCompatActivity implements View.OnClickListener {
  public ImageView event_photo;
    private int PICK_IMAGE_REQUEST = 1;
    private Button uploadImage;
    private Bitmap bitmap;
    public String string_image,eventid;
   public String []emails ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photo);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
      uploadImage=(Button)findViewById(R.id.upload_event_img) ;
        Bundle bundle = getIntent().getExtras();
        eventid = bundle.getString("id");
        Log.v("test",eventid);
        event_photo= (ImageView)findViewById(R.id.event_photo) ;
        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);
        getfollowers();

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);
        uploadImage.setOnClickListener(this);

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();


            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                event_photo.setImageBitmap(bitmap);
                uploadImage();
                finish();
                startActivity(new Intent(this,MainActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void onClick(View v) {
        if (v == uploadImage) {

            showFileChooser();
        }


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.event_save:{

                }
            case R.id.two:{

                return true;}
            case R.id.three: {

                return true;
            }



        }
        return true;

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void uploadImage(){



        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.upload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Toast.makeText(event_photo.this, eventid, Toast.LENGTH_LONG).show();

                            JSONArray array = new JSONArray(s);
                            loading.dismiss();

                        } catch (JSONException e) {

                            loading.dismiss();
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(event_photo.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                string_image = getStringImage(bitmap);
                Map<String,String> params = new Hashtable<String, String>();

                params.put("eventid",String.valueOf(eventid));
                params.put("image", string_image);

                return params;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);





    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void sendNotification(final  String email) {

        final String msg = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
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
                        send_email = email;
                        Log.v("tsttt", send_email);
                    } else {
                        send_email = SharedPrefManager.getInstance(getApplicationContext()).getUserEmail();
                        Log.v("tsttt", send_email);
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
                                + "\"contents\": {\"en\": \" has new event\"}"

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
    private void getfollowers() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.URL_getemails,
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
                params.put("userid", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserid()));

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(stringRequest);
    }

    private void check(JSONArray array) {

        emails=new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                try {
                    emails[i] = array.getString(i);
                    Log.v("tstt",emails[i]);
                    sendNotification( emails[i]);

                    }
                 catch (JSONException e) {
                    e.printStackTrace();
                }


            }



    }

}





