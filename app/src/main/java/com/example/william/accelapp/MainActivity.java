package com.example.william.accelapp;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.os.SystemClock.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText,yText,zText,timeView,textviewCounter,textviewSamplingFrequency;
    private LinearLayout ll1;
    private Button clear,record;
    private Sensor mySensor;
    private SensorManager SM;
    private static int size = 100000;
    private int counter = 0;
    private float xavg=0;
    private float yavg=0;
    private float zavg=0;
    private float[] xarray = new float[size];
    private float[] yarray = new float[size];
    private float[] zarray = new float[size];
    private float[] time = new float[size];
    private boolean recordIsPressed = FALSE;
    private long initialTime;
    private long currentTime;
    private GraphView graph;
    private DataPoint[] series = new DataPoint[size];
    private LineGraphSeries<DataPoint> points;

    //Arrays.fill(array, 1F);

    @Override
    public void onSensorChanged(SensorEvent event) {

        xText.setText("X: " + (float)Math.round(event.values[0] * 1000) / 1000);
        if (Math.abs(event.values[0]) > Math.abs(8.5)){
            xText.setTextColor(Color.parseColor("#ff4444"));
        }
        else if (Math.abs(event.values[0]) > Math.abs(4.5)){
            xText.setTextColor(Color.parseColor("#ccff00"));
        }
        else{
            xText.setTextColor(Color.parseColor("#006699"));
        }

        yText.setText("Y: " + (float)Math.round(event.values[1] * 1000) / 1000);
        if (Math.abs(event.values[1]) > Math.abs(8.5)){
            yText.setTextColor(Color.parseColor("#ff4444"));
        }
        else if (Math.abs(event.values[1]) > Math.abs(4.5)){
            yText.setTextColor(Color.parseColor("#ccff00"));
        }
        else{
            yText.setTextColor(Color.parseColor("#006699"));
        }

        zText.setText("Z: " + (float)Math.round(event.values[2] * 1000) / 1000);

        if (Math.abs(event.values[2]) > 8.5){
            zText.setTextColor(Color.parseColor("#ff4444"));
        }
        else if (Math.abs(event.values[2]) > Math.abs(4.5)){
            zText.setTextColor(Color.parseColor("#ccff00"));
        }
        else{
            zText.setTextColor(Color.parseColor("#006699"));
        }

        //update time value
        timeView.setText("Recording Time: " + (currentTime-initialTime)/1000.0);

        //if record button was pressed once, record
        if (recordIsPressed) {
            currentTime = elapsedRealtime(); //keep track of elapsed time
            time[counter] = (currentTime - initialTime)/1000;
            xarray[counter] = event.values[0];
            yarray[counter] = event.values[1];
            zarray[counter] = event.values[2];
            textviewCounter.setText("Counter: " + counter);
            counter = counter + 1;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register sensor Listener
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_FASTEST);

        //Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        timeView = (TextView)findViewById(R.id.timeView);
        ll1 = (LinearLayout)findViewById(R.id.ll1);
        clear = (Button)findViewById(R.id.clear);
        record = (Button)findViewById(R.id.record);
        graph = (GraphView) findViewById(R.id.graph);
        textviewCounter = (TextView) findViewById(R.id.textviewCounter);
        textviewSamplingFrequency = (TextView) findViewById(R.id.textviewSamplingFrequency);
        points = new LineGraphSeries<DataPoint>();

        record.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //stop recording
                if (recordIsPressed) {
                    recordIsPressed = FALSE;
                    record.setText("Record");

/*                    DataPoint temp = new DataPoint(0,0);
                    for (int i = 0; i < counter/100; i++)
                    {
                        temp = new DataPoint(time[counter],xarray[counter]);
                        series[i] = temp;
                    }*/
                    //points.appendData(series[1],true,counter);
                    LineGraphSeries<DataPoint> points = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint((double)time[0],(double)zarray[0]),

                            new DataPoint((double)time[1],(double)zarray[1]),
                            new DataPoint((double)time[2],(double)zarray[2]),
                            new DataPoint((double)time[3],(double)zarray[3]),
                            new DataPoint((double)time[4],(double)zarray[4]),
                            new DataPoint((double)time[5],(double)zarray[5]),
                            new DataPoint((double)time[6],(double)zarray[6]),
                            new DataPoint((double)time[7],(double)zarray[7]),
                            new DataPoint((double)time[8],(double)zarray[8]),
                            new DataPoint((double)time[9],(double)zarray[9]),
                            new DataPoint((double)time[10],(double)zarray[10]),
                            new DataPoint((double)time[11],(double)zarray[11]),
                            new DataPoint((double)time[12],(double)zarray[12]),
                            new DataPoint((double)time[13],(double)zarray[13]),
                            new DataPoint((double)time[14],(double)zarray[14]),
                            new DataPoint((double)time[15],(double)zarray[15]),
                            new DataPoint((double)time[16],(double)zarray[16]),
                            new DataPoint((double)time[17],(double)zarray[17]),
                            new DataPoint((double)time[18],(double)zarray[18]),
                            new DataPoint((double)time[19],(double)zarray[19]),
                            new DataPoint((double)time[20],(double)zarray[20])
                    });
                    graph.addSeries(points);
                }
                //start recording
                else{
                    recordIsPressed = TRUE;
                    record.setText("Stop");
                    initialTime = elapsedRealtime();
                    currentTime = elapsedRealtime();
                    //textviewSamplingFrequency.setText("Avg. Sampling Freq: " + (double) (counter+1)/(time[counter]*1000));
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //creates new empty arrays, clearing all data
                xarray = new float[size];
                yarray = new float[size];
                zarray = new float[size];
                //resets time data
                initialTime = elapsedRealtime();
                currentTime = elapsedRealtime();
                //resets counter
                counter = 0;
                textviewCounter.setText("Counter: " + counter);
                //textviewCounter.setText("Avg. Sampling Freq: 0");
                //clear data points
                graph.removeAllSeries();
            }
        });
    }
}
