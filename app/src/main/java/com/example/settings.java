package com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.rp.listview.R;
import com.txusballesteros.SnakeView;

public class settings extends AppCompatActivity {
    private float[] values = new float[] { 60, 70, 80, 90, 100,
            150, 150, 160, 170, 175, 180,
            170, 140, 130, 110, 90, 80, 60};
    private TextView text;
    private SnakeView snakeView;
    private int position = 0;
    private boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snak);
        getWindow().setBackgroundDrawable(null);
        text = (TextView)findViewById(R.id.text);
        snakeView = (SnakeView)findViewById(R.id.snake);
        snakeView.setMinValue(0);
        snakeView.setMaxValue(80);
    }

    @Override
    protected void onStart() {
        super.onStart();
        stop = false;
        generateValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop = true;
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
}