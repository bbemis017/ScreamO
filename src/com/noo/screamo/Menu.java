package com.noo.screamo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		
		 
		
		chooseAudio = (Button) findViewById(R.id.b_chooseAudio);
		audio = (TextView) findViewById(R.id.tv_audio);
		recentFall = (TextView) findViewById(R.id.tv_recentFall);
		totalFall = (TextView) findViewById(R.id.tv_totalFall);
		numFalls = (TextView) findViewById(R.id.tv_numFalls);
		
		totalDistance = 0;
		totalFalls = 0;
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
