package com.example;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class addevent extends AppCompatActivity implements View.OnClickListener {
    private String username ,test ;
    private EditText event_name ,event_owner, event_address , event_desc , event_start_date ,event_start_time ,event_end_date ,event_end_time ,
      event_seats , event_country;
    private  Calendar  myCalendar;
    private DatePickerDialog.OnDateSetListener start_date_listner , end_date_listner;
    private Toolbar myToolbar;
    private Bitmap bitmap;
    public   String slat ,code, slng, eventid ,string_image,userid ,id, title, description, start_time, start_date, end_time, end_date, city,address, seat_num,type;
    double lat, lng;
    private ImageView img_category;
    private  Dialog dialog;
    private TextView event_type;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        myToolbar = (Toolbar) findViewById(R.id.event_toolbar);
        event_owner =  (EditText) findViewById(R.id.event_owner);
        event_name= (EditText) findViewById(R.id.event_title);
        event_address= (EditText) findViewById(R.id.event_address);
        event_desc= (EditText) findViewById(R.id.event_desc);
        event_end_time= (EditText) findViewById(R.id.event_end_time);
        event_type= (TextView) findViewById(R.id.event_type);
        img_category = (ImageView) findViewById(R.id.img_cat_reg);
        event_end_date= (EditText) findViewById(R.id.event_end_date);
        event_seats= (EditText) findViewById(R.id.event_seats);
        event_start_time= (EditText) findViewById(R.id.event_start_time);
        event_start_date= (EditText) findViewById(R.id.event_start_date);
        event_country= (EditText) findViewById(R.id.event_country);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // get_id();
        username=SharedPrefManager.getInstance(addevent.this).getUsername();


        event_owner.setText(username);
        event_country.setOnClickListener(this);
        event_end_date.setOnClickListener(this);
        setSupportActionBar(myToolbar);
        event_start_time.setOnClickListener(this);
        event_type.setOnClickListener(this);


          myCalendar = Calendar.getInstance();
        img_category.setOnClickListener(this);
        event_start_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(addevent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        event_start_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        event_end_time.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(addevent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        event_end_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


         start_date_listner = new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        end_date_listner = new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        event_end_date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addevent.this, end_date_listner, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        event_start_date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addevent.this, start_date_listner, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





    }
    public void onClick(View v) {
        if (v==img_category) {
            show();
        }




    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        event_end_date.setText(sdf.format(myCalendar.getTime()));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel1() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        event_start_date.setText(sdf.format(myCalendar.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_event, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.event_save:



                getLatLongFromGivenAddress(event_country.getText().toString());
                slat = String.valueOf(lat);
                slng = String.valueOf(lng);
                add_event();


                Intent myIntent = new Intent(addevent.this, event_photo.class);
                myIntent.putExtra("id", eventid);

                startActivity(myIntent);


                return true;
            case R.id.home:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                return true;
        }
        return true;

    }

    private void add_event(){


    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String get_id() {

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, Constants.EVENT_QR_UPLOAD_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id=response.getString("message");
                            print(response);

                        }
                        catch (JSONException e) {

                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addevent.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );


        queue.add(getRequest);


        return eventid;
    }
private void print(JSONObject id){
    try {
        eventid=id.getString("message");

       // Toast.makeText(addevent.this,eventid, Toast.LENGTH_LONG).show();
    }
    catch (JSONException e) {

        e.printStackTrace();
    }







}
    public  void getLatLongFromGivenAddress(String address)
    {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {

            if(geoCoder.isPresent()){
                List<Address> list = geoCoder.getFromLocationName(address, 1);
                Address add = list.get(0);
                lat = add.getLatitude();
                lng = add.getLongitude();


                Log.d("latitude", "" + lat);
                Log.d("longitude", "" + lng);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void show(){
        String sel;
        final CharSequence[] items = getResources().getStringArray(R.array.my_array);
        sel=getResources().getString(R.string.sel);
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(sel);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                      //  editcategory.setText("");
                        code=String.valueOf(items[0]);

                        break;
                    case 1:
                       // editcategory.setText("");
                        code=String.valueOf(items[1]);


                        break;
                    case 2:
                       // editcategory.setText("");

                        code=String.valueOf(items[2]);
                        break;
                    case 3:
                       // editcategory.setText("");
                        code=String.valueOf(items[3]);
                        break;
                    case 4:
                     //   editcategory.setText("");

                        code=String.valueOf(items[4]);
                        break;
                    case 5:
                     //   editcategory.setText("");
                        code=String.valueOf(items[5]);
                        break;
                    case 6:
                       // editcategory.setText("");

                        code=String.valueOf(items[6]);
                        break;
                    case 7:
                      //  editcategory.setText("");
                        code=String.valueOf(items[7]);
                        break;
                    case 8:
                      //  editcategory.setText("");
                        code=String.valueOf(items[8]);
                        break;

                    case 9:
                       // editcategory.setText("");
                        code=String.valueOf(items[9]);
                        break;
                }
                dialog.dismiss();
                Toast.makeText(addevent.this, ""+code+""+"selected", Toast.LENGTH_LONG).show();
                //editcategory.setText(code);


            }
        });

        dialog = builder.create();
        dialog.show();

    }






}
