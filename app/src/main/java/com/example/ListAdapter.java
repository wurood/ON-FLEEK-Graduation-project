package com.example;

/**
 * Created by wurood on 10/23/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rp.listview.R;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<CalendarCollection>{

    private final Context context;
    private final ArrayList<CalendarCollection> values;
    private ViewHolder viewHolder;
    private final int resourceId;

    public ListAdapter(Context context, int resourceId,ArrayList<CalendarCollection> values) {
        super(context, resourceId, values);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.values = values;
        this.resourceId = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);


            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_event = (TextView) convertView.findViewById(R.id.tv_event);


            convertView.setTag(viewHolder);


        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CalendarCollection list_obj=values.get(position);
        viewHolder.tv_date.setText(list_obj.date);
        viewHolder.tv_event.setText(list_obj.event_message);

        return convertView;
    }





    public class ViewHolder {

        TextView tv_event;
        TextView tv_date;

    }

}