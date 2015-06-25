package com.example.f1controller;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SteeringActivity extends Activity {
	
	private boolean steering[];
	private ClientService clientService;

	public SteeringActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steering);
		final Intent serviceIntent = new Intent(this, ClientService.class);
		startService(serviceIntent);
		bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		steering = new boolean[]{false,false,false,false};
    	Log.d("debuger", "activity!");
		
		ImageButton up = (ImageButton)findViewById(R.id.imageButtonUp);
		ImageButton down = (ImageButton)findViewById(R.id.imageButtonDown);
		ImageButton left = (ImageButton)findViewById(R.id.imageButtonLeft);
		ImageButton right = (ImageButton)findViewById(R.id.imageButtonRight);
		Button stopButton = (Button)findViewById(R.id.StopButton);
		stopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopService(serviceIntent);
			}
		});
		up.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						steering[0]=true;
						return true;
					case MotionEvent.ACTION_UP:
						steering[0]=false;
						return true;
				}
				return false;
			}
		});
		down.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						steering[1]=true;
						return true;
					case MotionEvent.ACTION_UP:
						steering[1]=false;
						return true;
				}
				return false;
			}
		});
		left.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						steering[2]=true;
						return true;
					case MotionEvent.ACTION_UP:
						steering[2]=false;
						return true;
				}
				return false;
			}
		});
		right.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						steering[3]=true;
						return true;
					case MotionEvent.ACTION_UP:
						steering[3]=false;
						return true;
				}
				return false;
			}
		});
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {
			clientService = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			ClientService.MyBinder binder = (ClientService.MyBinder) service;
			clientService = binder.getClientService();
			clientService.st = steering;
		}
	};
	
}