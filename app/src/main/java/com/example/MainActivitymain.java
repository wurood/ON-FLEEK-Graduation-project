package com.example;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.rp.listview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivitymain extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener {


    private EditText editTextUsername, editTextEmail, editTextPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button buttonRegister ,btn;
    private ProgressDialog progressDialog;
    private AwesomeValidation awesomeValidation;
    private String PATTERN,name_pat,code;
    private Matcher matcher;
    private Pattern pattern,name_test;
    private   String email,username,password,category;
    private  Dialog dialog;
    private ImageView img_category;
    private TextView editcategory;
    private    int id,type;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(R.layout.activity_main_activitymain);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, home_activity.class));
            return;
        }

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
//    showdiag();
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int childCount = group.getChildCount();
//                for (int x = 0; x < childCount; x++) {
//                    RadioButton btn = (RadioButton) group.getChildAt(x);
//                    if (btn.getId() == checkedId) {
//                        Log.e("selected RadioButton->",btn.getText().toString());
//
//                    }
//                }
//            }
//        });

    }

    private void init (int layout) {
        setContentView(layout);
        loadViews();
        setListeners();
    }

    private void setListeners() {
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
        editTextUsername.setOnFocusChangeListener(this);
        img_category.setOnClickListener(this);
    }

    private void registerUser() {
        email = editTextEmail.getText().toString().trim();
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        (firebaseAuth.createUserWithEmailAndPassword(email, password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivitymain.this, "Registration successful", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());

                        }
                    }
                });
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivitymain.this, "Please wait...", "Processing...", true);
        category=code;
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("category", category);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    private void loadViews () {
        PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        name_pat="^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
        pattern = Pattern.compile(PATTERN);
        name_test=name_test.compile(name_pat);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        this.inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        this.inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        this.inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        this. editTextEmail = (EditText) findViewById(R.id.etxt_reg_email);
        this.editTextUsername = (EditText) findViewById(R.id.etxt_reg_name);
        this.editTextPassword = (EditText) findViewById(R.id.etxt_reg_pass);
        this.buttonRegister = (Button) findViewById(R.id.btn_reg);
        this.img_category = (ImageView) findViewById(R.id.img_cat_reg);
        this.editcategory=(TextView)findViewById(R.id.etxt_category);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.activity_register);
        awesomeValidation.addValidation(this, R.id.etxt_reg_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etxt_reg_email, Patterns.EMAIL_ADDRESS, R.string.nameerror);

    }
    public boolean validateEmail(final Editable hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
    public boolean validatename(final Editable hex) {
        matcher = name_test.matcher(hex);
        return matcher.matches();
    }
   @Override
    public void onClick(View view) {

        if (view == buttonRegister)
            registerUser();
        if (view==img_category){
            show();



        }

    }
    public void onFocusChange(View view, boolean hasfocus) {

        if (view == editTextEmail && (hasfocus==false)) {
            if (editTextEmail.getText().length() == 0) {
                editTextEmail.setError("please enter email ");
            } else if (!validateEmail(editTextEmail.getText())) {
                editTextEmail.setError("please enter a valid email ");
            } else {
                editTextUsername.setError(null);
            }
        }
        if (view == editTextUsername && (hasfocus==false)) {
            if (editTextUsername.getText().length() == 0) {
                editTextUsername.setError("please enter your name  ");
            } else if (!validatename(editTextUsername.getText())) {
                editTextUsername.setError("please enter a valid name ");
            } else {
                editTextUsername.setError(null);
            }
        }
        if (view == editTextPassword && (hasfocus==false)){
            if (editTextPassword.getText().length() == 0) {
                editTextPassword.setError("please enter password ");
            }
            else if (editTextPassword.getText().length()<7){
                editTextPassword.setError(" your password must be more than 7 character  ");
            }
            else {
                editTextPassword.setError(null);
            }
        }
    }



    //private  void  showdiag(){
//    List<String> stringList=new ArrayList<>();  // here is list
//    for(int i=0;i<5;i++) {
//        stringList.add("RadioButton ");
//        stringList.add("RadioButton 2");
//    }
//    RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
//
//    for(int i=0;i<stringList.size();i++){
//        RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
//        rb.setText(stringList.get(i));
//        rg.addView(rb);
//    }
//
//    dialog.show();
//}
    private void show(){
         String sel;
        final CharSequence[] items = getResources().getStringArray(R.array.my_array);
        sel=getResources().getString(R.string.sel);
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(sel);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        editcategory.setText("");
                        code=String.valueOf(items[0]);

                        break;
                    case 1:
                        editcategory.setText("");
                        code=String.valueOf(items[1]);


                        break;
                    case 2:
                        editcategory.setText("");

                        code=String.valueOf(items[2]);
                        break;
                    case 3:
                        editcategory.setText("");
                        code=String.valueOf(items[3]);
                        break;
                    case 4:
                        editcategory.setText("");

                        code=String.valueOf(items[4]);
                        break;
                    case 5:
                        editcategory.setText("");
                        code=String.valueOf(items[5]);
                        break;
                    case 6:
                        editcategory.setText("");

                        code=String.valueOf(items[6]);
                        break;
                    case 7:
                        editcategory.setText("");
                        code=String.valueOf(items[7]);
                        break;
                    case 8:
                        editcategory.setText("");
                        code=String.valueOf(items[8]);
                        break;

                    case 9:
                        editcategory.setText("");
                        code=String.valueOf(items[9]);
                        break;
                }
                dialog.dismiss();
                Toast.makeText(MainActivitymain.this, ""+code+""+"selected", Toast.LENGTH_LONG).show();
                editcategory.setText(code);


            }
        });

        dialog = builder.create();
        dialog.show();

    }



}


