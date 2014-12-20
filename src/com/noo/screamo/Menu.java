package com.noo.screamo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity{
	
	private Button chooseAudio;
	public TextView audio, recentFall, totalFall, numFalls;
	private FreeFallManager ffm;
	
	public double totalDistance;
	public int totalFalls;
	
	private SharedPreferences sharedPref;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
//		totalDistance = 0;
//		totalFalls = 0;
		
		sharedPref = ((Context)this).getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
		
		
		
		chooseAudio = (Button) findViewById(R.id.b_chooseAudio);
		audio = (TextView) findViewById(R.id.tv_audio);
		recentFall = (TextView) findViewById(R.id.tv_recentFall);
		totalFall = (TextView) findViewById(R.id.tv_totalFall);
		numFalls = (TextView) findViewById(R.id.tv_numFalls);
		
		if( sharedPref.contains( getString(R.string.total_distance_key) ) ){
			readAndDisplay();
		}else{
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt( getString(R.string.total_distance_key), 0);
			editor.putInt( getString(R.string.falls_key),0);
			totalFalls = 0;
			totalDistance = 0;
			editor.commit();
		}
		
		
//		totalFall.setText(totalDistance + " ft");
//		numFalls.setText( totalFalls + " falls");
		
		
		ffm = new FreeFallManager(this);
		
		chooseAudio.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent audioIntent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(audioIntent,0);
			}
		});
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		save();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		save();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		readAndDisplay();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		readAndDisplay();
	}
	
	private void save(){
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt( getString(R.string.total_distance_key), (int) Math.floor(totalDistance) );
		editor.putInt( getString(R.string.falls_key), totalFalls);
		editor.commit();
	}
	
	private void readAndDisplay(){
		totalDistance = sharedPref.getInt( getString(R.string.total_distance_key), 0);
		totalFalls = sharedPref.getInt( getString(R.string.falls_key), 0);
		totalFall.setText(totalDistance + " ft");
		numFalls.setText( totalFalls + " falls");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if( resultCode == RESULT_OK	){
			Log.d("test","result_ok");
			//TODO: change song that is going to be played
			String songUri = data.getData().getPath();
			File song = new File(songUri);
			Log.d("test","created file");

			ffm.newSong(this, data.getData());
			Log.d("test","created media player");
			audio.setText( song.getName()  );
		}
	}

}
