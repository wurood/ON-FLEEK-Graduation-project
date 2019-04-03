package com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.rp.listview.R;

/**
 * Created by wurood on 11/3/2017.
 */

public class GuideFragment extends Fragment {

    private int bgRes;
    private ImageView imageView;
    private TextView tex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgRes = getArguments().getInt("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) getView().findViewById(R.id.image_frag);
        imageView.setBackgroundResource(bgRes);

    }
}