package com.noo.screamo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;


public class Test extends Activity implements SensorEventListener, OnCompletionListener{
	
	private TextView tv_screamed;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private MediaPlayer mp;
	private boolean playing = false;
	int count = 0;
	private final float ERROR = .981f, GRAVITY = 9.81f ;

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
        
        mp = MediaPlayer.create(this,  R.raw.phone );
        mp.setOnCompletionListener(this);
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
    
    
    private void playAudio(){
    	if( playing)
    		return;
    	else{
    		mp.start();
    		playing = true;
    	}
    	
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		double accel = Math.sqrt( (double) event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2] );
		
		if( accel < ERROR){
			tv_screamed.setText("" + event.values[1] + " " + event.values[0] + " " + event.values[2]);
			playAudio();
			
//			Intent intent = getPackageManager().getLaunchIntentForPackage("com.noo.screamo");
//			if(intent!=null){
//				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(intent);
//				finish();
//			}
		}
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		playing = false;
		
	}
}
