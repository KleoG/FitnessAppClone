package com.example.kleog.fitnessapp.Views;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    public static final int DAY = 2;
    public static final int WEEK = 8;
    public static final int MONTH = 32;
    public static final int YEAR = 365;

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
    public static class CalorieGraphFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

        private List<DailyUserInfoModel> dailyUserInfoModels;

        public CalorieGraphFragment() {
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(15);
            series.setThickness(10);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calorie_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.calorieGraphTextView);
            textView.setText("Calorie Graph");

            dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);
            
            // create spinner
            Spinner spinner = (Spinner) rootView.findViewById(R.id.calorieGraphSpinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
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
            Date d4 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d5 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d6 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d7 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);
            Date d8 = calendar.getTime();
            calendar.add(Calendar.DATE, 4);

            // insert test data for dailyuserinfomodels
            // parameters: Date date, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat, Double weight
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d1, 200.0, 300.0, 400.0, 500.0, 20.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d2, 250.0, 450.0, 700.0, 550.0, 18.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d3, 350.0, 777.0, 777.0, 777.0, 15.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d4, 450.0, 777.0, 777.0, 777.0, 15.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d5, 350.0, 777.0, 777.0, 777.0, 15.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d6, 777.0, 777.0, 777.0, 777.0, 15.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d7, 520.0, 777.0, 777.0, 777.0, 15.0));
            dailyUserInfoViewModel.insert(new DailyUserInfoModel(d8, 350.0, 777.0, 777.0, 777.0, 15.0));

            dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());

            Log.d("size", "" + dailyUserInfoModels.size());

            series.resetData(generateData());

//            //TODO: This needs to only load once or it will try to reload all of the data, when the graph is re-entered
//            for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
//                Log.d("date", "" + dailyUserInfoModel.getDate());
//                Log.d("stuff", "" + dailyUserInfoModel.getTotalCalories());
//                series.appendData(new DataPoint(dailyUserInfoModel.getDate(), dailyUserInfoModel.getTotalCalories()), true, dailyUserInfoModels.size());
//            }

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
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("move view port", "" + parent.getItemAtPosition(position).toString());
                    switch (position) {
                        case 0:
                            changeDate(DAY);
                            break;
                        case 1:
                            changeDate(WEEK);
                            break;
                        case 2:
                            changeDate(MONTH);
                            break;
                        case 3:
                            changeDate(YEAR);
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
                public void onNothingSelected(AdapterView<?> adapterView) {
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

        // resets and generates updated data
        public DataPoint[] generateData(){
            DataPoint[] values = new DataPoint[dailyUserInfoModels.size()];
            for(int i = 0; i < values.length; i++){
                DataPoint v = new DataPoint(dailyUserInfoModels.get(i).getDate(), dailyUserInfoModels.get(i).getTotalCalories());
                values[i] = v;
            }
            return values;
        }

        /**
         * will check to see if the date is outof range, not allowing them to select a certain date
         * from spinner
         */
        public boolean dateOutOfRange(int timeFrame){
            if(dailyUserInfoModels.size() < timeFrame) {
                return true;
            }
            else
                return false;
        }

        public void changeDate(int timeFrame){
            if(!dateOutOfRange(timeFrame)) {
                firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - timeFrame).getDate();
                secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
            }
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

        private List<DailyUserInfoModel> dailyUserInfoModels;

        public WeightGraphFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_weight_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.weightgraphTextView);
            textView.setText("Weight Graph");

            dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);

            // create spinner
            Spinner spinner = (Spinner) rootView.findViewById(R.id.weightGraphSpinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.graphTimeArray, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);


            weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);

            dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());

            Log.d("size", "" + dailyUserInfoModels.size());

            series.resetData(generateData());

//            for(DailyUserInfoModel dailyUserInfoModel : dailyUserInfoModels){
//                Log.d("stuff", "" + dailyUserInfoModel.getWeight());
//                series.appendData(new DataPoint(dailyUserInfoModel.getDate(), dailyUserInfoModel.getWeight()), true, dailyUserInfoModels.size());
//            }

            // sets date chosen so they're not null
            firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate();
            secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();

            weightGraph.addSeries(series);

            // sets the titles of the axis on the graph
            weightGraph.getGridLabelRenderer().setVerticalAxisTitle("Weight");
            weightGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");

            // set date label formatter
            weightGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            weightGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

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


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("move view port", "" + parent.getItemAtPosition(position).toString());
                    switch (position) {
                        case 0:
                            changeDate(DAY);
                            break;
                        case 1:
                            changeDate(WEEK);
                            break;
                        case 2:
                            changeDate(MONTH);
                            break;
                        case 3:
                            changeDate(YEAR);
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
                public void onNothingSelected(AdapterView<?> adapterView) {
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

        // resets and generates updated data
        public DataPoint[] generateData(){
            DataPoint[] values = new DataPoint[dailyUserInfoModels.size()];
            for(int i = 0; i < values.length; i++){
                DataPoint v = new DataPoint(dailyUserInfoModels.get(i).getDate(), dailyUserInfoModels.get(i).getTotalCalories());
                values[i] = v;
            }
            return values;
        }


        /**
         * will check to see if the date is outof range, not allowing them to select a certain date
         * from spinner
         */
        public boolean dateOutOfRange(int timeFrame){
            if(dailyUserInfoModels.size() < timeFrame) {
                return true;
            }
            else
                return false;
        }

        public void changeDate(int timeFrame){
            if(!dateOutOfRange(timeFrame)) {
                firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - timeFrame).getDate();
                secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
            }
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



    
     /**
      * fragment for the weight graph page
      */
     public static class ProteinGraphFragment extends Fragment {

         private DailyUserInfoViewModel dailyUserInfoViewModel;

         private GraphView proteinGraph;

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

         List<DailyUserInfoModel> dailyUserInfoModels;

         public ProteinGraphFragment() {
             series.setDrawDataPoints(true);
             series.setDataPointsRadius(15);
             series.setThickness(10);
         }

         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
             View rootView = inflater.inflate(R.layout.fragment_protein_graph, container, false);
             TextView textView = (TextView) rootView.findViewById(R.id.proteingraphTextView);
             textView.setText("Protein Graph");
            
             dailyUserInfoViewModel = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);

             // create spinner
             Spinner spinner = (Spinner) rootView.findViewById(R.id.proteinGraphSpinner);
             // Create an ArrayAdapter using the string array and a default spinner layout
             ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                     R.array.graphTimeArray, android.R.layout.simple_spinner_item);
             // Specify the layout to use when the list of choices appears
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             // Apply the adapter to the spinner
             spinner.setAdapter(adapter);

             dailyUserInfoModels = dailyUserInfoViewModel.loadBetweenDates(new Date(), new Date());


             proteinGraph = (GraphView) rootView.findViewById(R.id.proteinGraphView);

             series.resetData(generateData());

             // sets date chosen so they're not null
             firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 3).getDate();
             secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();

             proteinGraph.addSeries(series);

             // sets the titles of the axis on the graph
             proteinGraph.getGridLabelRenderer().setVerticalAxisTitle("Protein");
             proteinGraph.getGridLabelRenderer().setHorizontalAxisTitle("Date");

             // set date label formatter
             proteinGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
             proteinGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

             proteinGraph.getViewport().setXAxisBoundsManual(true);
             proteinGraph.getViewport().setMinX(firstDateChosen.getTime());
             proteinGraph.getViewport().setMaxX(secondDateChosen.getTime());

             // enable scaling and scrolling
             proteinGraph.getViewport().setScalable(true);
             proteinGraph.getViewport().setScalableY(true);

             // as we use dates as labels, the human rounding to nice readable numbers
             // is not necessary
             proteinGraph.getGridLabelRenderer().setHumanRounding(false);
             // end of data test
             ///////////////////////////////////////////////////////////////////////////////////////////////////////////
             spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     Log.d("move view port", "" + parent.getItemAtPosition(position).toString());
                     switch (position) {
                         case 0:
                             changeDate(DAY);
                             break;
                         case 1:
                             changeDate(WEEK);
                             break;
                         case 2:
                             changeDate(MONTH);
                             break;
                         case 3:
                             changeDate(YEAR);
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
                 public void onNothingSelected(AdapterView<?> adapterView) {
                 }
             });



             // this allows data points to be tapped. Information is shown about whichever one they tap.
             series.setOnDataPointTapListener(new OnDataPointTapListener() {
                 @Override
                 public void onTap(Series series, DataPointInterface dataPoint) {
                     Toast.makeText(getActivity(), "Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
                 }
             });


             //not yet created
             //weightGraph = (GraphView) rootView.findViewById(R.id.weightGraphView);
             return rootView;
         }


         // resets and generates updated data
         public DataPoint[] generateData(){
             DataPoint[] values = new DataPoint[dailyUserInfoModels.size()];
             for(int i = 0; i < values.length; i++){
                 DataPoint v = new DataPoint(dailyUserInfoModels.get(i).getDate(), dailyUserInfoModels.get(i).getTotalProtein());
                 values[i] = v;
             }
             return values;
         }


         /**
          * will check to see if the date is outof range, not allowing them to select a certain date
          * from spinner
          */
         public boolean dateOutOfRange(int timeFrame){
             if(dailyUserInfoModels.size() < timeFrame) {
                 return true;
             }
             else
                 return false;
         }

         public void changeDate(int timeFrame){
             if(!dateOutOfRange(timeFrame)) {
                 firstDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - timeFrame).getDate();
                 secondDateChosen = dailyUserInfoModels.get(dailyUserInfoModels.size() - 1).getDate();
             }
         }

         /**
          * uses the starting and ending date the user picks using datepicker to determine
          * what data should be shown on screen - minX = firstDateChosen, maxX = secondDateChosen
          */
         public void moveViewport(){
             proteinGraph.getViewport().setXAxisBoundsManual(true);
             proteinGraph.getViewport().setMinX(firstDateChosen.getTime());
             proteinGraph.getViewport().setMaxX(secondDateChosen.getTime());

             proteinGraph.onDataChanged(false, false);
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
                    fragment = new ProteinGraphFragment();
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
