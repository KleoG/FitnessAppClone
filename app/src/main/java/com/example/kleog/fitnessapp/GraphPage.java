package com.example.kleog.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    // the single graph view
    GraphView graph;

    // the actual data and type of graph for the first graph
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_page);

        Spinner spinner = (Spinner) findViewById(R.id.graphs_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.graphs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        graph = (GraphView) findViewById(R.id.graph);
        graph.addSeries(series);

    }

    // is to display on the page the first graph (calories graph)
    public void displayGraphOne(){

        double x,y;
        x = -7.0;

        DataPoint[] data = new DataPoint[500];

        for(int i = 0; i < 500; i++){
            x = x + 0.1;
            y = Math.sin(x);
            data[i] = new DataPoint(x, y);
            //series.appendData(new DataPoint(x, y), true, 500);
        }
        //graph.addSeries(series);
        series.resetData(data);

    }

    // is to display on the page the second graph
    public void displayGraphTwo(){

//        // the single graph view
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//
//        if(!series.isEmpty() ){
//            graph.removeAllSeries();
//        }
//
//        double x,y;
//        x = -5.0;
//
//        for(int i = 0; i < 500; i++){
//            x = x + 0.3;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 500);
//        }
//        graph.addSeries(series);

        double x,y;
        x = -5.0;

        DataPoint[] data = new DataPoint[500];

        for(int i = 0; i < 500; i++){
            x = x + 0.3;
            y = Math.sin(x);
            data[i] = new DataPoint(x, y);
            //series.appendData(new DataPoint(x, y), true, 500);
        }
        //graph.addSeries(series);
        series.resetData(data);
        //graph.addSeries(series);


    }

    // is to display on the page the third graph
    public void displayGraphThree(){

//        // the single graph view
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//
//        if(!series.isEmpty() ){
//            graph.removeAllSeries();
//        }
//
//        double x,y;
//        x = -2.0;
//
//        for(int i = 0; i < 500; i++){
//            x = x + 0.7;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 500);
//        }
//        graph.addSeries(series);


        double x,y;
        x = -3.0;

        DataPoint[] data = new DataPoint[500];

        for(int i = 0; i < 500; i++){
            x = x + 0.7;
            y = Math.sin(x);
            data[i] = new DataPoint(x, y);
            //series.appendData(new DataPoint(x, y), true, 500);
        }
        series.resetData(data);
       // graph.addSeries(series);


    }

    // when the back button is pressed the graph is disposed of
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(position)

        switch(position){
            case 0:
                displayGraphOne();
                break;
            case 1:
                displayGraphTwo();
                break;
            case 2:
                displayGraphThree();
                break;
            default:
                break;
        }

//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
