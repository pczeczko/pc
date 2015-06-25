package com.example.f1controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ClientTask clientTask;
	private String message, IP="192.168.1.100";
	private int port=20000;
	private byte[] B;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	
	public void connect(View view) {
		TextView hello = (TextView)findViewById(R.id.Hello);
		EditText editIP = (EditText)findViewById(R.id.edit_IP);
		EditText editPort = (EditText)findViewById(R.id.edit_port);
		
		//clientTask = new ClientTask(this);
		//clientTask.setParameters(editIP.getText().toString(), Integer.parseInt(editPort.getText().toString()));
		//clientTask.execute();
		//clientTask = null;
		
		Intent intent = new Intent(this,SteeringActivity.class);
		intent.putExtra("ip",editIP.getText().toString());
		intent.putExtra("port",editPort.getText().toString());
		startActivity(intent);
	}
	
	public void showMessage(String message) {
		ConnectionDialog connectionDialog = new ConnectionDialog(message);
		connectionDialog.show(getFragmentManager(), "tag");
	}
	
	public void showMessage(int imessage) {
		String message = String.valueOf(imessage);
		ConnectionDialog connectionDialog = new ConnectionDialog(message);
		connectionDialog.show(getFragmentManager(), "tag");
	}
	
}
