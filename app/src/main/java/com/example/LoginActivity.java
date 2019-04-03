package com.example;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
public class LoginActivity extends AppCompatActivity implements View.OnClickListener ,View.OnFocusChangeListener {

    private EditText editTextUsername, editTextPassword,err_n,err_p;
    private TextView textViewLogin;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if( getIntent().getStringExtra("EXTRA_SESSION_ID") != null)
        {
            reg= getIntent().getStringExtra("EXTRA_SESSION_ID");
            if(reg.equals("true")){
                forceRTLIfSupported();
            }//do here
        }
        editTextUsername = (EditText) findViewById(R.id.etxt_signin);
        editTextPassword = (EditText) findViewById(R.id.etxt_signin_pass);
        buttonLogin = (Button) findViewById(R.id.btn_log);
        textViewLogin = (TextView) findViewById(R.id.linkSignin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        buttonLogin.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        editTextUsername.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
    public void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        (firebaseAuth.signInWithEmailAndPassword(username, password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email"),
                                                obj.getString("proimgurl"),
                                                obj.getString("password"),
                                                obj.getString("phone"),
                                                obj.getString("category")
                                        );
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                params.put("email", username);
                params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }





    @Override
    public void onClick(View view) {

        if(view == buttonLogin)
            userLogin();
        if(view == textViewLogin)
            startActivity(new Intent(this, MainActivitymain.class));
    }

    @Override
    public void onFocusChange(View view, boolean hasfocus) {

        if (view == editTextUsername && (hasfocus==false)) {
            if (editTextUsername.getText().length() == 0) {
                editTextUsername.setError("please enter name ");
            } else {
                editTextUsername.setError(null);
            }

        }
        if (view == editTextPassword &&( hasfocus==false)) {
            if (editTextPassword.getText().length() == 0) {
                editTextPassword.setError("please enter your password ");
            } else {
                editTextPassword.setError(null);
            }
        }



    }

}
