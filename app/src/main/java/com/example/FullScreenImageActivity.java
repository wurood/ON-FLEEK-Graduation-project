package com.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rp.listview.R;

public class FullScreenImageActivity extends AppCompatActivity {
String event_id;
    TextView txt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        Bundle bundle = getIntent().getExtras();
        event_id = bundle.getString("message");
        txt = (TextView)findViewById(R.id.ttt);
        txt.setText(event_id);
    }
}
