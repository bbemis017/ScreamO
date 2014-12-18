package com.noo.screamo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;

public class FreeFallManager implements SensorEventListener, OnCompletionListener{
	
	private final float ERROR = .981f;
	private boolean playing;
	private MediaPlayer mp;
	private Sensor mSensor;
	private SensorManager mSensorManager;
	
	public FreeFallManager(Activity act){
		
		setUp(act);
		mp = MediaPlayer.create(act, R.raw.phone);
		mp.setOnCompletionListener(this);
	}
	
	public FreeFallManager(Activity act, Uri uri){
		
		setUp(act);
		//create mediaplayer with selected mp3 file
		mp = MediaPlayer.create(act, uri);
		Log.d("test","created MediaPlayer");
		mp.setOnCompletionListener(this);
	}
	
	public void newSong(Activity act, Uri uri){
		mp = MediaPlayer.create(act,uri);
		mp.setOnCompletionListener(this);
	}
	
	private void setUp(Activity act){
		playing = false;
		
		mSensorManager = (SensorManager) act.getSystemService(Context.SENSOR_SERVICE);
        if ( mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){
        	mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        	Log.d("SENSOR", "NO ACCELEROMETER");
	}
	
	private void playAudio(){
		if(playing)
			return;
		else{
			mp.start();
			playing = true;
		}
	}
	
	public void close(){
		
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		playing = false;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		double accel = Math.sqrt( (double) event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2] );
		if ( accel < ERROR){//if phone is in freefall
			playAudio();
			//TODO: do some stuff
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
		
	}

}
