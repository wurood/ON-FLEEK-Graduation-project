package com.example;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 02/11/2017.
 */

public class ticketmodle {
    private int id,userid;
    private String title,eventid;
    private String desc;
    private String date;
    private String image;
    private String tkt;
    private Context mCtx;

    public ticketmodle(int id, String title, String desc, String date, int userid, String eventid, String img) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.image = img;
        this.userid= userid;
        this.eventid=eventid;
        this.mCtx=mCtx;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getdesc() {
        return desc;
    }

    public String getdate() {
        return date;
    }


    public String getImage() {
        return image;
    }

    public String gettkt() {

        String tkt = Constants.EVENT_GET_TKTS+userid+eventid+".png";


        return tkt;
    }
}

