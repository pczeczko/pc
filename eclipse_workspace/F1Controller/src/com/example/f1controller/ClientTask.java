package com.example.f1controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class ClientTask extends AsyncTask<Void, Void, Void> {

	private String message, IP;
	private int port;
	private byte[] B;
	MainActivity main;
	
	ClientTask(MainActivity main) {
		this.main = main;
	}
	
	void setParameters(String IP, int port) {
		this.IP = IP;
		this.port = port;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		Socket socket = null;
		B = new byte[5];
		B[0]=12;
		B[1]=23;
		B[2]=33;
		
		try {
		    socket = new Socket(IP,port);
		    socket.setSoTimeout(100);
		    DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		    while (true) {
		    	outToServer.write(B);
		    	B[0]++;
		    }
		    //socket = null;
		    
		    } catch (SocketException e) {
			   	main.showMessage(e.getMessage());
		    } catch (IOException e) {
				Log.d("chuj", e.getMessage());
		    } finally {
			   if(socket != null){
				   try {
					   socket.close();
					   } catch (IOException e) {
						   e.printStackTrace();
						   }
				   }
			   }
		
		return null;
		}
}
