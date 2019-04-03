package com.example;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tickets.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tickets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tickets extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  int userid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   public List<ticketmodle> ticketlist;

    //the recyclerview
    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public tickets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tickets.
     */
    // TODO: Rename and change types and number of parameters
    public static tickets newInstance(String param1, String param2) {
        tickets fragment = new tickets();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_tickets, container, false);
        userid=SharedPrefManager.getInstance(getActivity()).getUserid();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getfollowevent();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getData(final String id) {

        StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, Constants.EVENT_GET_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                ticketlist.add(
                                        new ticketmodle(
                                                1,
                                                obj.getString("title"),
                                                obj.getString("desc"),
                                                obj.getString("end_dt"),
                                                userid,
                                                id,
                                               Constants.EVENT_IMGS+"/"+obj.getString("img") ));

                                ticketadapter adapter = new ticketadapter(getActivity(),ticketlist);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            } catch (JSONException e) {

                                e.printStackTrace();
                                Log.v("test","title");
                            }}},
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    return params;
                }

            };


            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


            requestQueue.add(stringRequest);

        }

    private void getfollowevent() {
        ticketlist = new ArrayList<>();
                StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.URL_get_EVENT_Follow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                getData(array.getString(i));
                                Log.v("test",String.valueOf(ticketlist.size()));
                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "hiiiii", Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);
    }


}
