package com.example;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;



public class GridViewAdapter extends BaseAdapter {


    private final Context context;
    String id;
    String separated[];
    ArrayList<String>  urls;
 public ImageView view;
      public GridViewAdapter(Context context, ArrayList<String> images) {
        this.context = context;
         urls =images;




    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        Log.v("ttest",String.valueOf(position));
        view = (ImageView) convertView;



            view = new ImageView(context);
            view.setLayoutParams(new GridView.LayoutParams(300,300));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setPadding(0, 0,0,0);
            String url = Constants.EVENT_IMGS+getItem(position);

            Log.v("oko2",url);
            Picasso.with(context)
                    .load(url)
                    .fit()
                    .tag(context)
                    .into(view);
            view.setTag(getItem(position));



//        String url = Constants.EVENT_IMGS+getItem(position);
//
//        Log.v("oko2",url);
//        Picasso.with(context)
//                .load(url)
//                .fit()
//                .tag(context)
//                .into(view);


        separated= String.valueOf(view.getTag()).split("\\.");
        //Log.v("oko2",separated[0]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Log.v("uuu", view.getTag().toString());

                                                    Log.v("ttest",String.valueOf(position));
                                                    id = String.valueOf(view.getTag()).split("\\.")[0];
                                                    Intent intent = new Intent(context,register_event.class);
                                                    Log.v("oko",id);
                                                    intent.putExtra("message",id);
                if(context instanceof MainActivity){
                    intent.putExtra("source","1");
                }
                   else {
                    intent.putExtra("source","0");
                }
                                                    context.startActivity(intent);

                                                }
                                            });








        return view;
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {

        return position;
    }
}