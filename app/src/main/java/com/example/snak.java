package com.example;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rp.listview.R;
import com.txusballesteros.SnakeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link snak.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link snak#newInstance} factory method to
 * create an instance of this fragment.
 */

public class snak extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private float[] values = new float[] { 60, 70, 80, 90, 100,
            150, 150, 160, 170, 175, 180,
            170, 140, 130, 110, 90, 80, 60};

    private TextView text;
    private SnakeView snakeView;
    private int position = 0;
    private boolean stop = false;
    private ImageView img_eve;
    private TextView editeve;
    private  Dialog dialog;
    private String code;
    int userid;
    ArrayList<String> title_s ;
    ArrayList<String> seats;
    private OnFragmentInteractionListener mListener;

    public snak() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment snak.
     */
    // TODO: Rename and change types and number of parameters
    public static snak newInstance(String param1, String param2) {
        snak fragment = new snak();
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
        title_s=new ArrayList<>();
        seats=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.snak_, container, false);
        getActivity().getWindow().setBackgroundDrawable(null);
        text = (TextView)view.findViewById(R.id.text);
        img_eve = (ImageView) view.findViewById(R.id.img_eve);
        editeve=(TextView)view.findViewById(R.id.etxt_eve);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        snakeView = (SnakeView)view.findViewById(R.id.snake);
        snakeView.setMinValue(0);
        snakeView.setMaxValue(80);
       // getData();
        getWidget();


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
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }
    @Override
    public void onStart() {
        super.onStart();
        stop = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        stop = true;
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

    private void generateValue() {
        if (position < (values.length - 1)) {
            position++;
        } else {            position =0;
        }
        float value = (float) 75.0;
        snakeView.addValue(1);
        snakeView.addValue(2);
        snakeView.addValue(5);
        snakeView.addValue(10);
        snakeView.addValue(15);
        snakeView.addValue(20);
        snakeView.addValue(30);
        snakeView.addValue(value);

        text.setText(Integer.toString((int)value));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!stop) {
//                    generateValue();
//                }
//            }
//        }, 500);
    }


    public void getWidget(){

        img_eve.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 show();

            }
        });

    }

    private void getData() {

        // final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching data...", false, false);


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.Get_seats,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {




                            JSONArray array = new JSONArray(response);
                            for (int i=0; i < array.length(); i++) {
                                JSONObject  json_data = array.getJSONObject(i);

                                title_s.add((json_data.getString("title")));


                            }




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
//       Toast.makeText(getActivity(),title_s.get(0), Toast.LENGTH_LONG).show();
    }


    private void show(){
        final CharSequence[] items = { "open reading","Graduation Day",
                "New Year Party","wedding"};
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.fleek_2);
        builderSingle.setTitle("Select One Event:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
        for( int i =0;i<items.length;i++) {
            arrayAdapter.add((String) items[i]);

        }
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        editeve.setText(strName);
                        generateValue();

                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();

    }

}
