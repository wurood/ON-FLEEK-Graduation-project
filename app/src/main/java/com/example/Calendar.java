package com.example;

import android.content.Context;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.rp.listview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Calendar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Calendar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calendar extends Fragment  implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    final CharSequence[] title = { "wedding","open reading","Ana_jawal",
            "New Year Party", "OUR Graduation"};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Calendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static Calendar newInstance(String param1, String param2) {
        Calendar fragment = new Calendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_base, container, false);
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

       setupDateTimeInterpreter(false);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(java.util.Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " AM" : (hour == 0 ? "12 PM" : hour + " PM");
            }
        });
    }

    protected String getEventTitle(java.util.Calendar time) {

        return String.format("Event of %02d:%02d %s/%d", time.get(java.util.Calendar.HOUR_OF_DAY), time.get(java.util.Calendar.MINUTE), time.get(java.util.Calendar.MONTH)+1, time.get(java.util.Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getActivity(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(java.util.Calendar time) {
        Toast.makeText(getActivity(), "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        java.util.Calendar startTime = java.util.Calendar.getInstance();
        startTime.set(java.util.Calendar.HOUR_OF_DAY,12);
        startTime.set(java.util.Calendar.MINUTE, 0);
        startTime.set(java.util.Calendar.MONTH, newMonth - 1);
        startTime.set(java.util.Calendar.YEAR, newYear);
        java.util.Calendar endTime = (java.util.Calendar) startTime.clone();
        endTime.add(java.util.Calendar.HOUR, 3);
        endTime.set(java.util.Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, "New Year Party/Bitlahem", startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);




        startTime = java.util.Calendar.getInstance();
        startTime.set(java.util.Calendar.DAY_OF_MONTH, 13);
        startTime.set(java.util.Calendar.HOUR_OF_DAY, 3);
        startTime.set(java.util.Calendar.MINUTE, 0);
        startTime.set(java.util.Calendar.MONTH, newMonth-1);
        startTime.set(java.util.Calendar.YEAR, newYear);
        endTime = (java.util.Calendar) startTime.clone();
        endTime.set(java.util.Calendar.DAY_OF_MONTH, 13);
        endTime.set(java.util.Calendar.HOUR_OF_DAY, 8);
        event = new WeekViewEvent(8," Wedding/Nablus", startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);


        startTime = java.util.Calendar.getInstance();
        startTime.set(java.util.Calendar.DAY_OF_MONTH, 19);
        startTime.set(java.util.Calendar.HOUR_OF_DAY, 2);
        startTime.set(java.util.Calendar.MINUTE, 0);
        startTime.set(java.util.Calendar.MONTH, newMonth-1);
        startTime.set(java.util.Calendar.YEAR, newYear);
        endTime = (java.util.Calendar) startTime.clone();
        endTime.set(java.util.Calendar.DAY_OF_MONTH, 22);
        endTime.set(java.util.Calendar.HOUR_OF_DAY, 6);
        event = new WeekViewEvent(8," open reading/Tulkarem Library", startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = java.util.Calendar.getInstance();
        startTime.set(java.util.Calendar.DAY_OF_MONTH, 3);
        startTime.set(java.util.Calendar.HOUR_OF_DAY, 2);
        startTime.set(java.util.Calendar.MINUTE, 10);
        startTime.set(java.util.Calendar.MONTH, newMonth-1);
        startTime.set(java.util.Calendar.YEAR, newYear);
        endTime = (java.util.Calendar) startTime.clone();
        endTime.set(java.util.Calendar.DAY_OF_MONTH,3);
        endTime.set(java.util.Calendar.HOUR_OF_DAY, 6);
        event = new WeekViewEvent(3," Ana_jawwal/Birzeit University", startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_05));
        events.add(event);



        startTime = java.util.Calendar.getInstance();
        startTime.set(java.util.Calendar.DAY_OF_MONTH, 25);
        startTime.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startTime.set(java.util.Calendar.MINUTE, 10);
        startTime.set(java.util.Calendar.MONTH, newMonth-1);
        startTime.set(java.util.Calendar.YEAR, newYear);
        endTime = (java.util.Calendar) startTime.clone();
        endTime.set(java.util.Calendar.DAY_OF_MONTH,25);
        endTime.set(java.util.Calendar.HOUR_OF_DAY, 10);
        event = new WeekViewEvent(5,"GraduationDay/Najah University", startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);



        return events;
    }


}
