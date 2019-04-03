package com.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class event extends Fragment {

    int userid;

    int ide;
    public static final String TAG_IMAGE_URL = "URL";
private String[] URL;

    private GridView gridView;


    private ArrayList<String> images;
   private Activity user_activity;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public event() {

    }


    public static event newInstance(String param1, String param2) {
        event fragment = new event();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(getActivity() instanceof MainActivity) {
            userid = SharedPrefManager.getInstance(getActivity()).getUserid();
        }else if (getActivity() instanceof SearchProfile){
            userid = SharedPreSearch.getInstance(getActivity()).getUserid();
        }
        images = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview_third);


        getData();

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getData() {

       // final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching data...", false, false);


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.GET_IMAGES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray array = new JSONArray(response);


                          //  loading.dismiss();

                            Log.v("test", array.toString());

                            showGrid(array);


                        } catch (JSONException e) {

                         //   loading.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  loading.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", String.valueOf(userid));

                return params;
            }

        };
        ;


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        requestQueue.add(stringRequest);
    }

    private void showGrid(JSONArray array) {


        for (int i = 0; i < array.length(); i++) {

            JSONObject obj = null;
            try {
                images.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        gridView.setAdapter(new GridViewAdapter(getActivity().getApplicationContext(), images));


    }


}
