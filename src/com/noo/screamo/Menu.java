package com.noo.screamo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity{
	
	private Button chooseAudio;
	private TextView audio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		chooseAudio = (Button) findViewById(R.id.b_chooseAudio);
		audio = (TextView) findViewById(R.id.tv_audio);
		
		chooseAudio.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent pickMedia = new Intent(Intent.ACTION_GET_CONTENT);
		        pickMedia.setType("audio/*");
		        startActivityForResult(pickMedia,1);
			}
		});
		
		
	}

}
