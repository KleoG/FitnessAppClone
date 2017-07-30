package com.example.kleog.fitnessapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class newGraphActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //radio group for the graphs
    private RadioGroup graphRG;

    //graph view
    GraphView graph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_graph);
        setTitle("Graphs");

        //radio buttons initialised
        graphRG = (RadioGroup) findViewById(R.id.radioGroupGraphs);

        //sets all radio buttons to unclickable
        for (int i = 0; i < graphRG.getChildCount(); i++) {
            graphRG.getChildAt(i).setEnabled(false);
        }


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //when swiping from left to write the radio buttons will update accordingly
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //sets the position of the radio button to checked depending on which fragment number the swipeview is currently on
                ((RadioButton) graphRG.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    /**
     * fragment for the calorie graph page
     */
    public static class CalorieGraphFragment extends Fragment {

        private GraphView caloreGraph;

        private LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();


        private EditText firstDate;
        //stores selected dates for the first date
        private int firstDateYear;
        private int firstDateMonth;
        private int firstDateDay;
        //the date chosen stored in Date format
        private Date firstDateChosen;

        private EditText secondDate;
        //stores selected dates for the first date
        private int secondDateYear;
        private int secondDateMonth;
        private int secondDateDay;
        //the date chosen stored in Date format
        private Date secondDateChosen;

        public CalorieGraphFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calorie_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.calorieGraphTextView);
            textView.setText("calorie graph goes here");

            caloreGraph = (GraphView) rootView.findViewById(R.id.calorieGraphView);
            caloreGraph.addSeries(series);


            //dates selected at bottom of screen
            firstDate = (EditText) rootView.findViewById(R.id.calorieGraphFirstDate);
            secondDate = (EditText) rootView.findViewById(R.id.calorieGraphSecondDate);

            //allows the view to be clicked but not edited by the user
            firstDate.setFocusable(false);
            firstDate.setClickable(true);

            /**
             * on click listenser for the first date box will popup a date picker for the user to select from
             */

            firstDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //To show current date in the datepicker
                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            //TODO set variable firstDateChosen to the date the the user selects
                            firstDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();
                }
            });


            //allows the view to be clicked but not edited by the user
            secondDate.setFocusable(false);
            secondDate.setClickable(true);

            /**
             * on click listenser for the first date box will popup a date picker for the user to select from
             */

            secondDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //To show current date in the datepicker
                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            //TODO set variable secondDateChosen to the date the the user selects

                            secondDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();
                }
            });






            return rootView;
        }
    }

    /**
     * fragment for the weight graph page
     */
    public static class WeightGraphFragment extends Fragment {
        private GraphView weightGraph;

        private LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        public WeightGraphFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_weight_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.weightgraphTextView);
            textView.setText("weight graph goes here");

            //not yet created
            //weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new CalorieGraphFragment();
                    break;
                case 1:
                    fragment = new WeightGraphFragment();
                    break;
                case 2:
                    fragment = new CalorieGraphFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
