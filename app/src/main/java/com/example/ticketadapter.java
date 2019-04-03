package com.example;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rp.listview.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 02/11/2017.
 */



 public class ticketadapter extends RecyclerView.Adapter<ticketadapter.ticketViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    ImageView img;
    SimpleDateFormat curFormater ;
    //we are storing all the products in a list
    private List<ticketmodle> ticketList;

    ;
    //getting the context and product list with constructor
    public ticketadapter(Context mCtx, List<ticketmodle> ticketList) {
        this.mCtx = mCtx;
        this.ticketList = ticketList;



    }

    @Override
    public ticketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.ticketlist, null);

        return new ticketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ticketViewHolder holder, int position) {

        final ticketmodle ticket = ticketList.get(position);
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        curFormater = new SimpleDateFormat("yyyy-MM-dd");

        try {

            boolean pass = printDifference(curFormater.parse(ticket.getdate()));

            if(pass)
            {
                Log.v("test","hiiiiiiiii");
                holder.passed.setImageResource(R.drawable.ic_passed);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewTitle.setText(ticket.getTitle());
        holder.textViewdate.setText(ticket.getdate());
        holder.textViewTitle.setText(ticket.getTitle());
        holder.textViewShortDesc.setText(ticket.getdesc());


        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(mCtx);
                Bitmap bmp = null;
                new RetrieveFeedTask(image).execute(ticket.gettkt());


                AlertDialog.Builder builder =
                        new AlertDialog.Builder(mCtx).
                                setMessage("Event Ticket").
                                setPositiveButton("OK",  listener).
                                setView(image);
                builder.create().show();

            }});
                Picasso.with(mCtx)
                .load(ticket.getImage())
                .fit()
                .tag(mCtx)
                .into(holder.imageView);



    }


    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    class ticketViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc,textViewdate;
        ImageView imageView,passed;
        Button show;

        public ticketViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDes);
            textViewdate = itemView.findViewById(R.id.textdate);
            show = itemView.findViewById(R.id.show);

            imageView = itemView.findViewById(R.id.imageView);
            passed= itemView.findViewById(R.id.passed);
        }
    }
    private boolean printDifference( Date endDate) {

        Calendar c = Calendar.getInstance();
        Date d =  c.getTime();


        long differented = endDate.getTime() - d.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDaysed = differented / daysInMilli;
        differented = differented % daysInMilli;
        long elapsedHoursed = differented / hoursInMilli;
        differented = differented % hoursInMilli;
        long elapsedMinutesed = differented / minutesInMilli;
        differented = differented % minutesInMilli;
        long elapsedSecondsed = differented / secondsInMilli;
        if(elapsedDaysed<=0 &&elapsedHoursed<=0){
          return true;
        }

return false;

    }

}