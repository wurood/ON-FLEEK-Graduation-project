package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rp.listview.R;
import com.example.rp.listview.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link scanQR.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link scanQR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class scanQR extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView scan_btn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private IntentIntegrator qrScan;


    public scanQR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment scanQR.
     */
    // TODO: Rename and change types and number of parameters
    public static scanQR newInstance(String param1, String param2) {
        scanQR fragment = new scanQR();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_qr, container, false);
        scan_btn = (ImageView) view.findViewById(R.id.centerImage);
        final RippleBackground rippleBackground=(RippleBackground)view.findViewById(R.id.content);
        qrScan = new IntentIntegrator(getActivity());
        rippleBackground.startRippleAnimation();


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });


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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Result" , "reqCode : " + requestCode + ", resultCode" + resultCode);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Intent i = new Intent(getActivity(), register_event.class);
                    JSONObject obj = new JSONObject(result.getContents());
                    Bundle bundle = new Bundle();
                    bundle.putString("message",result.getContents());
                    i.putExtras(bundle);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(getActivity(), "hhhhhhh",Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
