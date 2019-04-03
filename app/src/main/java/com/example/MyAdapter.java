package com.example;

/**
 * Created by wurood on 11/18/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.rp.listview.R;

public class MyAdapter extends ArrayAdapter<String> {

    String[] names;
    int[] flags;
    Context mContext;

    public MyAdapter(Context context, String[] countryNames, int[] countryFlags) {
        super(context, R.layout.activity_hover);
        this.names = countryNames;
        this.flags = countryFlags;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_hover, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.source);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mFlag.setImageResource(flags[position]);


        return convertView;
    }

    static class ViewHolder {
        ImageView mFlag;

    }
}