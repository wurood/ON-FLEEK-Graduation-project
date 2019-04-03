package com.example;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link editprofile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editprofile extends Fragment {
    private EditText editTextUsername, editTextEmail, editTextphone;
    private Bitmap bitmap;
    private ImageView profile_img , image_change;
    private int ID;
    private Button submit ;
    private int PICK_IMAGE_REQUEST = 1;

    private String profile_img_url ,username,password ,new_phone , new_email,imagee,profile_image_url;
    private Typeface font1;

    private ProgressDialog progressDialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public editprofile() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editprofile.
     */
    // TODO: Rename and change types and number of parameters
    public static editprofile newInstance(String param1, String param2) {
        editprofile fragment = new editprofile();
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
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        image_change = (ImageView) view.findViewById(R.id.btn_sett_lod_pic);
        submit= (Button) view.findViewById(R.id.sub_sett);
        editTextUsername = (EditText)view.findViewById(R.id.etxt_sett_name);
        editTextEmail = (EditText)view.findViewById(R.id.etxt_sett_email);
        editTextphone = (EditText) view.findViewById(R.id.etxt_sett_phone);
        profile_img=(ImageView)view.findViewById(R.id.img_sett);
        profile_img_url = SharedPrefManager.getInstance(getActivity()).getUserimg();
        font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Bold.ttf");
        Picasso.with(getActivity()).load(profile_img_url).error(R.drawable.default_user).into(profile_img);
        editTextUsername.setText(SharedPrefManager.getInstance(getActivity()).getUsername());
        editTextEmail.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        editTextphone.setText("");
        font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Bold.ttf");
        editTextUsername.setTypeface(font1);
        editTextEmail.setTypeface(font1);
        editTextphone.setTypeface(font1);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadImage();
                olduser();
            }
        });
        profile_image_url=Constants.EVENT_IMGS+"/"+SharedPrefManager.getInstance(view.getContext()).getUserimg();
        Log.v("test",profile_image_url);
        Picasso.with(view.getContext()).load(profile_image_url).error(R.drawable.default_user).into(profile_img);
        image_change .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFileChooser();

            }
        });


        if (!SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }



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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        editprofile.this.startActivityForResult(intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        // startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            Toast.makeText(getActivity(), "hiiiiiii", Toast.LENGTH_LONG).show();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                profile_img.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
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
        final String username = editTextUsername.getText().toString().trim();
        final  Integer ID=SharedPrefManager.getInstance(getActivity()).getUserid();
        final  String  email_p=editTextEmail.getText().toString().trim();
        final String phone_p = editTextphone.getText().toString().trim();
        imagee = getStringImage(bitmap);
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PRO_UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        JSONArray array = null;
                        try {
                            array = new JSONArray(s);
                            profile_image_url= Constants.EVENT_IMGS+"/"+array.getString(0) ;
                            SharedPrefManager.getInstance(getContext()).userupdateimg("//"+array.getString(0));
                            Toast.makeText(getContext(), SharedPrefManager.getInstance(getContext()).getUserimg(), Toast.LENGTH_LONG).show();
                            Picasso.with(getContext()).load(profile_image_url).error(R.drawable.default_user).into(profile_img);
                            loading.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();
                params.put("username", username);
                params.put("ID",String.valueOf(ID));
                imagee = getStringImage(bitmap);
                params.put("image", imagee);
                params.put("email", email_p);
                params.put("phone", phone_p);

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);

    }

    private void olduser(){
        final String username =editTextUsername.getText().toString().trim();

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
                                SharedPrefManager.getInstance(getActivity().getApplicationContext())
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
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
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
                                getActivity(). getApplicationContext(),
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

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}



