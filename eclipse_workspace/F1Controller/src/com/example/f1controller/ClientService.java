package com.example.f1controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ClientService extends Service {

	Socket socket;
	PrintStream printStream;
	boolean[] st;
	private final IBinder binder = new MyBinder();
	Runnable connectSocket;
	
	public ClientService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		socket = new Socket();
    	Log.d("debuger", "create service!");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		connectSocket = new ConnectionSocket();
		new Thread(connectSocket).start();		
		return super.onStartCommand(intent, flags, startId);
	}
	
	class ConnectionSocket implements Runnable {
		
		@Override
		public void run() {
			SocketAddress socketAddress = new InetSocketAddress("192.168.1.103",20000);
			try {
				socket.connect(socketAddress);
			    DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
				while (true) {
					Thread.sleep(1);
					outToServer.write( ((st[0])? 1 : 0) + ((st[1])? 2 : 0) + ((st[2])? 4 : 0) + ((st[3])? 8 : 0) );
					Log.d("debuger", "write to socket!"+st[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("debuger", e.getMessage());
			} catch (InterruptedException e) {}
		}
	}
	
	public class MyBinder extends Binder {
		ClientService getClientService() {
			return ClientService.this;
		}
	}
	
}
