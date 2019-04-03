package com.example;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rp.listview.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wurood on 10/6/2017.
 */

public class search extends AppCompatActivity  {
    private String urlAddress;
    private String new_user,old_user;
    private SearchView sv;
    private  ListView lv;
    private ImageView noDataImg,noNetworkImg;
    private Toolbar toolbar;
    private  Dialog dialog;
    int id;
    private static final String KEY_USER = "username";
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar_ser);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lv= (ListView) findViewById(R.id.lv);
        sv= (SearchView) findViewById(R.id.sv);
        noDataImg= (ImageView) findViewById(R.id.nodataImg);
        noNetworkImg= (ImageView) findViewById(R.id.noserver);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        show();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                SenderReceiver sr=new SenderReceiver(search.this,urlAddress,query,lv,noDataImg,noNetworkImg);
                sr.execute();
                return false;
            }

            public boolean onQueryTextChange(String query) {
                SenderReceiver sr=new SenderReceiver(search.this,urlAddress,query,lv,noDataImg,noNetworkImg);
                sr.execute();
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView <?> parentAdapter, View view, int position,
                                    long id) {
//                int item_pos=position;
                String data=(String)parentAdapter.getItemAtPosition(position);
                new_user=data;
                userserach();
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                //  long data=lv.getItemIdAtPosition(position);

//                startActivity(intent);


                //  Place code here with the action

            }
        });
    }
    private void userserach(){
        final String username =new_user.toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_sepro,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPreSearch.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email"),
                                                obj.getString("proimgurl"),
                                                obj.getString("password")
                                        );
                                startActivity(new Intent(getApplicationContext(), SearchProfile.class));
                                finish();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);

                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void show(){
        String tity,cat,gen;
        tity=getResources().getString(R.string.tity);
        gen=getResources().getString(R.string.cat);
        cat=getResources().getString(R.string.gen);

        final CharSequence[] items = {gen,cat};
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tity);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        urlAddress=Constants.urlAddress;
                        break;
                    case 1:
                        urlAddress=Constants.category;

                        break;
                }
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();

    }
}
