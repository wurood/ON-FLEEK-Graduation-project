package com.example;

/**
 * Created by wurood on 11/18/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.rp.listview.R;

public class test_main extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListView;
    private Context mContext;
    private BlurLayout mSampleLayout;
    private Uri imageUri;
    String[] countryNames = {"Australia", "Brazil", "China", "France", "Germany"};
    int[] countryFlags = {  R.drawable.festival,
            R.drawable.hallwen,
            R.drawable.newyear,
            R.drawable.grad,
            R.drawable.anaj
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.hover_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mListView = (ListView) findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(test_main.this, countryNames, countryFlags);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View hover, int i, long l) {
                Toast.makeText(test_main.this, "Registration successful", Toast.LENGTH_LONG).show();
                BlurLayout.setGlobalDefaultDuration(450);
                mSampleLayout = (BlurLayout)findViewById(R.id.blur_layout);
                hover = LayoutInflater.from(mContext).inflate(R.layout.hover1, null);
                hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);

                    }
                });
                hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Swing)
                                .duration(550)
                                .playOn(v);
                        String text="Halween Events By nasma";
                        imageUri = Uri.parse("android.resource://" + getPackageName()
                                + "/drawable/" + "festival");
                        Intent myint=new Intent (Intent.ACTION_SEND);
                        myint.putExtra(Intent.EXTRA_TEXT,text);
                        myint.putExtra(Intent.EXTRA_STREAM,imageUri);
                        myint.setType("image/jpeg");
                        myint.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(myint,"Share using"));

                    }
                });
                mSampleLayout.setHoverView(hover);
                mSampleLayout.setBlurDuration(550);
                mSampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
                mSampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
                mSampleLayout.addChildAppearAnimator(hover, R.id.more, Techniques.FlipInX, 550, 500);

                mSampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
                mSampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
                mSampleLayout.addChildDisappearAnimator(hover, R.id.more, Techniques.FlipOutX, 550, 0);

                mSampleLayout.addChildAppearAnimator(hover, R.id.description, Techniques.FadeInUp);
                mSampleLayout.addChildDisappearAnimator(hover, R.id.description, Techniques.FadeOutDown);




            }
        });
    }





}
