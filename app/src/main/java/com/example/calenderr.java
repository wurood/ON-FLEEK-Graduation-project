package com.example;

/**
 * Created by wurood on 11/28/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.rp.listview.R;


/**
 * The launcher activity of the sample app. It contains the links to visit all the example screens.
 * Created by Raquib-ul-Alam Kanak on 7/21/2014.
 * Website: http://alamkanak.github.io
 */
public class calenderr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calenderr);

        findViewById(R.id.buttonBasic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calenderr.this, BasicActivity.class);
                startActivity(intent);
            }
        });


    }

}