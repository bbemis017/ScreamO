package com.noo.screamo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class FreeFallManager implements SensorEventListener, OnCompletionListener{
	
	private final float ERROR = .981f;
	private boolean playing;
	private MediaPlayer mp;
	
	public FreeFallManager(Context context){
		
		playing = false;
		mp = MediaPlayer.create(context, R.raw.phone);
		mp.setOnCompletionListener(this);
	}
	
	public FreeFallManager(Context context, Uri uri){
		
		playing = false;
		//create mediaplayer with selected mp3 file
		mp = MediaPlayer.create(context, uri);
		mp.setOnCompletionListener(this);
	}
	
	private void playAudio(){
		if(playing)
			return;
		else{
			mp.start();
			playing = true;
		}
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
