package com.example.kleog.fitnessapp.Views;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.ViewModels.DailyUserInfoViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class newGraphActivity extends AppCompatActivity {

    //graph view
    GraphView graph;
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
    public static class CalorieGraphFragment extends Fragment implements OnItemSelectedListener {

        // view model
        private DailyUserInfoViewModel dailyUserInfoViewModel;

        private GraphView calorieGraph;

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
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calorie_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.calorieGraphTextView);
            textView.setText("calorie graph goes here");

            dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);
            
            // create spinner
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.graphTimeArray, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            
            
            
            
             calorieGraph = (GraphView) rootView.findViewById(R.id.calorieGraphView);

            // generate Dates
            //change the Calender.DATE to Calender.MONTH or Calender.YEAR and change the second parameter (determioning a point in time)
            // e.g. Calender.DATE, 1 = tomorrow and Calender.MONTH, -1 = one month ago
            Calendar calendar = Calendar.getInstance();
            Date d1 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d3 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);

            // insert test data for dailyuserinfomodels
            // parameters: Date date, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat, Double weight
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d1, 200.0, 300.0, 400.0, 500.0, 20.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d2, 250.0, 450.0, 700.0, 550.0, 18.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d3, 350.0, 777.0, 777.0, 777.0, 15.0));

            List<DailyUserInfoModel> dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());

            Log.d("size", "" + dailyUserInfoModels.size());

            for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
                Log.d("stuff", "" + dailyUserInfoModel.getTotalCalories());
                series.appendData(new DataPoint(dailyUserInfoModel.getDate(), dailyUserInfoModel.getTotalCalories()), true, dailyUserInfoModels.size());
            }

            // sets date chosen so they're not null
            firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate();
            secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();

            calorieGraph.addSeries(series);
            
            // sets the titles of the axis on the graph
            calorieGraph.getGridLabelRenderer().setVerticalAxisTitle("Calories");
            calorieGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
                
            // set date label formatter
            calorieGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            calorieGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

            // set manual x bounds to have nice steps
            // TODO: use this to set what is viewed on the graph between 2 dates that are specified by the user
            // this piece of code is so if there is less than 3 dates in the database, it will fill the missing dates by itself
//            if(dailyUserInfoModels.size() < 3){
//                for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
//                    d1 = calendar.getTime();
//                    calendar.add(Calendar.DATE, dailyUserInfoModels.size());
//                }
//                caloreGraph.getViewport().setMinX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate().getTime());
//                caloreGraph.getViewport().setMaxX(d1.getTime());
//            }else {
//                caloreGraph.getViewport().setMinX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate().getTime());
//                caloreGraph.getViewport().setMaxX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate().getTime());
//            }

            calorieGraph.getViewport().setXAxisBoundsManual(true);
            calorieGraph.getViewport().setMinX(firstDateChosen.getTime());
            calorieGraph.getViewport().setMaxX(secondDateChosen.getTime());

            // enable scaling and scrolling
            calorieGraph.getViewport().setScalable(true);
            calorieGraph.getViewport().setScalableY(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            calorieGraph.getGridLabelRenderer().setHumanRounding(false);
            // end of data test
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////


            // this allows data points to be tapped. Information is shown about whichever one they tap.
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(getActivity(), "Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
                }
            });


            return rootView;
        }


        /**
         * uses the starting and ending date the user picks using datepicker to determine
         * what data should be shown on screen - minX = firstDateChosen, maxX = secondDateChosen
         */
        public void moveViewport(){
            calorieGraph.getViewport().setXAxisBoundsManual(true);
            calorieGraph.getViewport().setMinX(firstDateChosen.getTime());
            calorieGraph.getViewport().setMaxX(secondDateChosen.getTime());

            calorieGraph.onDataChanged(false, false);
        }
        
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(position)

            switch (position) {
                case 0:
                    firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 2).getDate();
                    secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
                    break;
                case 1:
                    firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 8).getDate();
                    secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
                    break;
                case 2:
                    firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 32).getDate();
                    secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
                    break;
                case 3:
                    firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 365).getDate();
                    secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
                    break;
                default:
                    break;
            }
            
            // after changing the values, update the viewport
            moveViewport();

//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        }
    }



    /**
     * fragment for the weight graph page
     */
    public static class WeightGraphFragment extends Fragment {
        // view model
        private DailyUserInfoViewModel dailyUserInfoViewModel;

        private GraphView weightGraph;

        private BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();


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

        public WeightGraphFragment() {


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_weight_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.weightgraphTextView);
            textView.setText("weight graph goes here");

            dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);

            // data for the graph
            //DataPoint[] data = new DataPoint[500];

//             for (int i = 0; i < 3; i++) {
//                 //data[i] = new DataPoint(new Date(), dailyUserInfoModels.get(i).getTotalCalories());
//                 series.appendData(new DataPoint(new Date(), dailyUserInfoModels.get(i).getTotalCalories()), true, 500);
//             }
//             //series.moveViewport(data);
            weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);
//             caloreGraph.addSeries(series);


            // generate Dates
            //change the Calender.DATE to Calender.MONTH or Calender.YEAR and change the second parameter (determioning a point in time)
            // e.g. Calender.DATE, 1 = tomorrow and Calender.MONTH, -1 = one month ago
            Calendar calendar = Calendar.getInstance();
            Date d1 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d3 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);

            // insert test data for dailyuserinfomodels
            // parameters: Date date, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat, Double weight
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d1, 200.0, 300.0, 400.0, 500.0, 20.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d2, 250.0, 450.0, 700.0, 550.0, 18.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d3, 350.0, 777.0, 777.0, 777.0, 15.0));

            List<DailyUserInfoModel> dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());

            Log.d("size", "" + dailyUserInfoModels.size());

            for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
                Log.d("stuff", "" + dailyUserInfoModel.getWeight());
                series.appendData(new DataPoint(dailyUserInfoModel.getDate(), dailyUserInfoModel.getWeight()), true, dailyUserInfoModels.size());
            }

            // sets date chosen so they're not null
            firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate();
            secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();

            weightGraph.addSeries(series);

            // sets the titles of the axis on the graph
            weightGraph.getGridLabelRenderer().setVerticalAxisTitle("Calories");
            weightGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");

            // set date label formatter
            weightGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            weightGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

            // set manual x bounds to have nice steps
            // TODO: use this to set what is viewed on the graph between 2 dates that are specified by the user
            // this piece of code is so if there is less than 3 dates in the database, it will fill the missing dates by itself
//            if(dailyUserInfoModels.size() < 3){
//                for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
//                    d1 = calendar.getTime();
//                    calendar.add(Calendar.DATE, dailyUserInfoModels.size());
//                }
//                caloreGraph.getViewport().setMinX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate().getTime());
//                caloreGraph.getViewport().setMaxX(d1.getTime());
//            }else {
//                caloreGraph.getViewport().setMinX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate().getTime());
//                caloreGraph.getViewport().setMaxX(dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate().getTime());
//            }
            weightGraph.getViewport().setXAxisBoundsManual(true);
            weightGraph.getViewport().setMinX(firstDateChosen.getTime());
            weightGraph.getViewport().setMaxX(secondDateChosen.getTime());

            // enable scaling and scrolling
            weightGraph.getViewport().setScalable(true);
            weightGraph.getViewport().setScalableY(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            weightGraph.getGridLabelRenderer().setHumanRounding(false);
            // end of data test
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////

            //dates selected at bottom of screen
            firstDate = (EditText) rootView.findViewById(R.id.weightGraphFirstDate);
            secondDate = (EditText) rootView.findViewById(R.id.weightGraphSecondDate);

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
                            int day = datepicker.getDayOfMonth();
                            int month = datepicker.getMonth();
                            int year =  datepicker.getYear();
                            // must plus one to the month just because months start from 0 (January = 0)
                            firstDate.setText(day + "/" + (month + 1) + "/" + year);
                            mcurrentDate.set(year, month, day);

                            firstDateChosen = mcurrentDate.getTime();
                            //setXLimits();
                            //Log.d("firstDateChosen", "" + firstDateChosen);
                            moveViewport();
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
                            int day = datepicker.getDayOfMonth();
                            int month = datepicker.getMonth();
                            int year =  datepicker.getYear();
                            //TODO set variable secondDateChosen to the date the the user selects
                            secondDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);

                            mcurrentDate.set(year, month, day);

                            secondDateChosen = mcurrentDate.getTime();

                            moveViewport();
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();
                }
            });

            // this allows data points to be tapped. Information is shown about whichever one they tap.
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(getActivity(), "Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        /**
         * uses the starting and ending date the user picks using datepicker to determine
         * what data should be shown on screen - minX = firstDateChosen, maxX = secondDateChosen
         */
        public void moveViewport(){
            weightGraph.getViewport().setXAxisBoundsManual(true);
            weightGraph.getViewport().setMinX(firstDateChosen.getTime());
            weightGraph.getViewport().setMaxX(secondDateChosen.getTime());

            weightGraph.onDataChanged(false, false);
        }

    }
    
//     /**
//      * fragment for the weight graph page
//      */
//     public static class CarbsGraphFragment extends Fragment {
//         private GraphView carbsGraph;

//         private LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

//         public CarbsGraphFragment() {


//         }

//         @Override
//         public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                  Bundle savedInstanceState) {
//             View rootView = inflater.inflate(R.layout.fragment_weight_graph, container, false);
//             TextView textView = (TextView) rootView.findViewById(R.id.weightgraphTextView);
//             textView.setText("weight graph goes here");
            
//             dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);

//             List<DailyUserInfoModel> dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());

//             // data for the graph
//             //DataPoint[] data = new DataPoint[500];

// //             for (int i = 0; i < 3; i++) {
// //                 //data[i] = new DataPoint(new Date(), dailyUserInfoModels.get(i).getTotalCalories());
// //                 series.appendData(new DataPoint(new Date(), dailyUserInfoModels.get(i).getTotalCalories()), true, 500);
// //             }
// //             //series.moveViewport(data);
//              weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);
// //             caloreGraph.addSeries(series);
            
//             // generate Dates
//             //change the Calender.DATE to Calender.MONTH or Calender.YEAR and change the second parameter (determioning a point in time)
//             // e.g. Calender.DATE, 1 = tomorrow and Calender.MONTH, -1 = one month ago
//             Calendar calendar = Calendar.getInstance();
//             Date d1 = calendar.getTime();
//             calendar.add(Calendar.DATE, 1);
//             Date d2 = calendar.getTime();
//             calendar.add(Calendar.DATE, 1);
//             Date d3 = calendar.getTime();
            
            
//             // insert test data for dailyuserinfomodels
//             // parameters: Date date, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat, Double weight
//             dailyUserInfoModels.insert(new DailyUserInfoModel(d1, 200, 300, 400, 500, 20));
//             dailyUserInfoModels.insert(new DailyUserInfoModel(d2, 250, 450, 700, 550, 18));
//             dailyUserInfoModels.insert(new DailyUserInfoModel(d3, 350, 777, 777, 777, 17));
           

//             //GraphView graph = (GraphView) findViewById(R.id.graph);

//             // you can directly pass Date objects to DataPoint-Constructor
//             // this will convert the Date to double via Date#getTime()
// //             LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
// //                 new DataPoint(d1, 1),
// //                 new DataPoint(d2, 5),
// //                 new DataPoint(d3, 3)
// //             });
//             for (int i = 0; i < 3; i++) {
//                 //data[i] = new DataPoint(new Date(), dailyUserInfoModels.get(i).getTotalCalories());
//                 series.appendData(new DataPoint(d1, dailyUserInfoModels.get(i).getWeight()), true, 500);
//                 series.appendData(new DataPoint(d2, dailyUserInfoModels.get(i).getWeight()), true, 500);
//                 series.appendData(new DataPoint(d3, dailyUserInfoModels.get(i).getWeight()), true, 500);
//             }

//             carbsGraph.addSeries(series);

//             // set date label formatter
//             carbsGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
//             carbsGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

//             // set manual x bounds to have nice steps
//             carbsGraph.getViewport().setMinX(d1.getTime());
//             carbsGraph.getViewport().setMaxX(d3.getTime());
//             carbsGraph.getViewport().setXAxisBoundsManual(true);

//             // as we use dates as labels, the human rounding to nice readable numbers
//             // is not necessary
//             carbsGraph.getGridLabelRenderer().setHumanRounding(false);
//             // end of data test
//             ///////////////////////////////////////////////////////////////////////////////////////////////////////////

//             //not yet created
//             //weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);
//             return rootView;
//         }
//     }

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
