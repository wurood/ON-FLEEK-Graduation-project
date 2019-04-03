package com.example;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String KEY_EVENNT_lat = "lat";
    private static final String KEY_EVENNT_lan = "lan";
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_IMG = "proimgurl";
    private static final String KEY_USER_PASS = "password";
    private static final String KEY_USER_cat = "category";
    private static final String KEY_USER_ph = "phone";

    private static final String KEY_EVENNT_ID = "id";
    private static final String KEY_EVENNT_title = "title";
    private static final String KEY_EVENNT_description = "description";
    private static final String KEY_EVENNT_start_dt= "start_dt";
    private static final String KEY_EVENNT_end_tm = "end_tm";
    private static final String KEY_EVENNT_end_dt = "end_dt";
    private static final String KEY_EVENNT_city= "city";
    private static final String KEY_EVENNT_address = "address";
    private static final String KEY_EVENNT_seat_num = "seat_num";
    private static final String KEY_EVENNT_USERID = "USERID";
    private static final String KEY_EVENNT_type = "type";
    private static final String KEY_EVENNT_QR = "QR";
    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email, String proimgurl, String password ,String phone ,String category ) {


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_IMG, proimgurl);
        editor.putString(KEY_USER_PASS, password);
        editor.putString(KEY_USER_ph, phone);
        editor.putString(KEY_USER_cat, category);

        editor.apply();

        return true;
    }
    public boolean userupdateimg( String proimgurl) {


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_IMG, proimgurl);


        editor.apply();

        return true;
    }


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserpass() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PASS, null);
    }

    public String getUserimg() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_IMG, null);
    }

    public int getUserid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 0);

    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
    public String getUserPhone() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ph, null);
    }
    public String getUserCategory() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_cat, null);
    }
    public String getUserlat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EVENNT_lat, null);
    }
    public String getUserlan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EVENNT_lan, null);
    }
    public boolean eventdata(int id, String title, String description, String start_dt, String end_tm ,
                             String end_dt ,String city,String address,Integer seat_num ,String type,
                             Integer USERID,String QR,String lat,String lan ) {


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_EVENNT_ID, id);
        editor.putString(KEY_EVENNT_title, title);
        editor.putString(KEY_EVENNT_description, description);
        editor.putString(KEY_EVENNT_start_dt, start_dt);
        editor.putString(KEY_EVENNT_end_tm, end_tm);
        editor.putString(KEY_EVENNT_end_dt, end_dt);
        editor.putString(KEY_EVENNT_city, city);
        editor.putString(KEY_EVENNT_address, address);
        editor.putInt(KEY_EVENNT_seat_num,seat_num);
        editor.putString(KEY_EVENNT_type, type);
        editor.putInt(KEY_EVENNT_USERID, USERID);
        editor.putString(KEY_EVENNT_QR, QR);
        editor.putString(KEY_EVENNT_lat, lat);
        editor.putString(KEY_EVENNT_lan, lan);


        editor.apply();

        return true;
    }


}
