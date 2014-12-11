package com.noo.screamo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;


public class Test extends Activity implements SensorEventListener{
	
	private TextView tv_screamed;
	private SensorManager mSensorManager;
	private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        

        tv_screamed = (TextView) findViewById(R.id.tv_screamed);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if ( mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){
        	mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        	Log.d("SENSOR", "NO ACCELEROMETER");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onSensorChanged(SensorEvent event) {
		if( event.values[1] > 5)
			tv_screamed.setText("" + event.values[1]);
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
