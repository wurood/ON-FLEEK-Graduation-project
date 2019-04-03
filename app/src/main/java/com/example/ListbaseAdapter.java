package com.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rp.listview.R;

import java.util.ArrayList;

/**
 * Created by Rp on 3/16/2016.
 */
public class ListbaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Beanclass> beans;


    public ListbaseAdapter(Context context, ArrayList<Beanclass> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size() ;
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;





        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
          viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listview,null);


            viewHolder.image= (ImageView)convertView.findViewById(R.id.img_list);
            viewHolder.title= (TextView)convertView.findViewById(R.id.etxt_list_title);
            viewHolder.desc= (TextView)convertView.findViewById(R.id.etxt_list_desc);
            viewHolder.date= (TextView)convertView.findViewById(R.id.etxt_list_date);

            viewHolder.title.setTypeface(type);
            viewHolder.desc.setTypeface(type);
            viewHolder.date.setTypeface(type);


            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();

        }


        Beanclass beans = (Beanclass)getItem(position);

        viewHolder.image.setImageResource(beans.getImage());
        viewHolder.title.setText(beans.getTitle());
        viewHolder.desc.setText(beans.getDesc());
        viewHolder.date.setText(beans.getDate());

        return convertView;
    }



    private class ViewHolder{
        ImageView image;
        TextView title;
        TextView desc;
        TextView date;

    }





}
