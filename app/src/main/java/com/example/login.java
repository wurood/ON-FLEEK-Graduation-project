package com.example;



import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 23/09/2017.
 */

public class login {


    public static boolean  userLogin(final String passwordv , final String usernamev , final Context context){




        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(null)
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email"),
                                                obj.getString("proimgurl"),
                                                obj.getString("password"),
                                                obj.getString("phone"),
                                                obj.getString("category")


                                        );

                            }else{

                            }
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", usernamev);
                params.put("password", passwordv);
                return params;
            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        return true;
    }


}
