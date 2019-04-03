package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.rp.listview.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Language_.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Language_#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Language_ extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_CODE = 1;
    Spinner spinnerctrl;
    Button btn;
    Locale myLocale;
    ImageView img;
    String flag;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Language_() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Language_.
     */
    // TODO: Rename and change types and number of parameters
    public static Language_ newInstance(String param1, String param2) {
        Language_ fragment = new Language_();
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
        final String str1,str2;
        str1=getResources().getString(R.string.ara);
        str2=getResources().getString(R.string.era);
        flag="false";

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language_, container, false);
        img = (ImageView) view.findViewById(R.id.img_lan);
        spinnerctrl = (Spinner) view.findViewById(R.id.spinner1);

        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {
                    flag="true";
//                    Toast.makeText(parent.getContext(),
//                            str1, Toast.LENGTH_SHORT)
//                            .show();
                    setLocale("ar");

                } else if (pos == 2) {

//                    Toast.makeText(parent.getContext(),
//                            str2, Toast.LENGTH_SHORT)
//                            .show();
                    setLocale("en");
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
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

    public void setLocale(String lang) {


        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(getActivity(), home_activity.class);
        refresh.putExtra("EXTRA_SESSION_ID", flag);
        startActivity(refresh);



    }
}
