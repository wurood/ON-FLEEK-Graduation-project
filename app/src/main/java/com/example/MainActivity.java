package com.example;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
    public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

        private BottomNavigationView profile_nav_par;
        private Bitmap bitmap;
        private ImageView upload_pro_image,profile_image,add_event,add_cat;
        private int PICK_IMAGE_REQUEST = 1;
        private TextView user_name, user_email;

        private String profile_image_url ,username, string_image ,password ,email,phone,category;
        private Toolbar myToolbar;
        private FragmentTransaction transaction;
        private FragmentManager fragmentManager;
        private Typeface font1, font2;
        private Integer ID;
        private FirebaseAuth firebaseAuth;
       private FirebaseUser user;
        static String LoggedIn_User_Email;
        String flag,home;


        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            FirebaseApp.initializeApp(this);
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
            FirebaseApp.initializeApp(this);
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            fragmentManager = getSupportFragmentManager();
//            transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.content_frame, new event()).commit();
            myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            upload_pro_image = (ImageView) findViewById(R.id.btn_lod_pic);
            profile_image=(ImageView) findViewById(R.id.img_main);
            add_event=(ImageView) findViewById(R.id.add_event);
            add_cat=(ImageView) findViewById(R.id.img_category);
            user_email = (TextView) findViewById(R.id.etxt_main_desc);
            profile_nav_par = (BottomNavigationView) findViewById(R.id.pronav);
            user_name = (TextView) findViewById(R.id.etxt_main_name);
           LoggedIn_User_Email = user.getEmail();

            flag="false";
            if( getIntent().getStringExtra("EXTRA_SESSION_ID") != null)
            {
                flag= getIntent().getStringExtra("EXTRA_SESSION_ID");
                if(flag.equals("true")){
                    forceRTLIfSupported();
                }//do here
            }

            LoggedIn_User_Email =user.getEmail();
            OneSignal.sendTag("User_ID", LoggedIn_User_Email);

            font1 = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Montserrat-Bold.ttf");
            font2 = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Montserrat-Regular.ttf");
            username=SharedPrefManager.getInstance(this).getUsername().trim();
            ID=SharedPrefManager.getInstance(this).getUserid();
            password=SharedPrefManager.getInstance(this).getUserpass();
            email=SharedPrefManager.getInstance(this).getUserEmail();
            phone=SharedPrefManager.getInstance(this).getUserPhone();
            category=SharedPrefManager.getInstance(this).getUserCategory();
            Picasso.with(this).load(Constants.EVENT_IMGS+SharedPrefManager.getInstance(this).getUserimg()).error(R.drawable.default_user).into(profile_image);
            category="cultural";
            OneSignal.sendTag("User_ID", LoggedIn_User_Email);
            setSupportActionBar(myToolbar);


            if (category.equals("personal")){
                add_cat.setImageResource(R.drawable.ic_person_pro);

            }
            else if  (category.equals("education")){
               add_cat.setImageResource(R.drawable.ic_edu);

            }
            else if  (category.equals("business")){
                add_cat.setImageResource(R.drawable.ic_busniess);

            }
            else if  (category.equals("party")){
                add_cat.setImageResource(R.drawable.ic_pf);

            }
            else if  (category.equals("cultural")){
                add_cat.setImageResource(R.drawable.ic_cal);

            }
            else if  (category.equals("food")){
                add_cat.setImageResource(R.drawable.ic_food);

            }
            else if  (category.equals("music")){
                add_cat.setImageResource(R.drawable.ic_music);

            }
            else if  (category.equals("art")){
                add_cat.setImageResource(R.drawable.ic_art);

            }
            else if  (category.equals("festival")){
                add_cat.setImageResource(R.drawable.ic_pf);

            }
            else if  (category.equals("health")){
                add_cat.setImageResource(R.drawable.ic_doc);

            }
            user_name.setText(SharedPrefManager.getInstance(this).getUsername());
            user_email.setText(SharedPrefManager.getInstance(this).getUserEmail());
            user_name.setTypeface(font1);
            upload_pro_image.setOnClickListener(this);
            add_event.setOnClickListener(this);


            profile_nav_par.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        transaction.replace(R.id.content_frame, new snak()).commit();

                    }

                    return true;
                }

            });



        }
        private BroadcastReceiver dataChangeReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                profile_image_url=Constants.EVENT_IMGS+"/"+SharedPrefManager.getInstance(MainActivity.this).getUserimg();

            }
        };
        protected void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(dataChangeReceiver);

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

                    Intent i =new Intent(this,home_activity.class);
                    i.putExtra("EXTRA_SESSION_ID", flag);
                    startActivity(i);
                    return true;
                case R.id.search:

                    startActivity(new Intent(this,BasicActivity.class));
                    return true;




            }
            return true;

        }
        private void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Picasso.with(this).load(Constants.EVENT_IMGS+SharedPrefManager.getInstance(this).getUserimg()).error(R.drawable.default_user).into(profile_image);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri filePath = data.getData();


               try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                   profile_image.setImageBitmap(bitmap);
                    uploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onClick(View v) {
            if (v == upload_pro_image) {

                showFileChooser();
            }
            if (v == add_event){

                finish();
                startActivity(new Intent(this, addevent.class));
            }

        }
        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        private void uploadImage(){



            final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PRO_EDIT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {

                                JSONArray array = new JSONArray(s);
                                profile_image_url= Constants.EVENT_IMGS+array.getString(0) ;
                                SharedPrefManager.getInstance(getApplicationContext()).userupdateimg(array.getString(0));
                               // Toast.makeText(MainActivity.this, SharedPrefManager.getInstance(getApplicationContext()).getUserimg(), Toast.LENGTH_LONG).show();
                                Picasso.with(MainActivity.this).load(profile_image_url).error(R.drawable.default_user).into(profile_image);
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
                            Toast.makeText(MainActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    string_image = getStringImage(bitmap);
                    Map<String,String> params = new Hashtable<String, String>();
                    params.put("username", username);
                    params.put("ID",String.valueOf(ID));

                    params.put("image", string_image);

                    return params;
                }
            };



            RequestQueue requestQueue = Volley.newRequestQueue(this);


            requestQueue.add(stringRequest);





        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private void forceRTLIfSupported()
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }



    }
