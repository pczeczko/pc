import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JDialog;


public class Server extends JDialog implements KeyListener, Runnable {

	private Controls controls;
	private ServerSocket serverSocket;
	private Socket connectionSocket;
	InputStream stream;
	byte[] data;
	
	public Server() throws HeadlessException {
		addKeyListener(this);
		this.setSize(200, 200);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		data = new byte[5];
		try {
			serverSocket = new ServerSocket(20000);
		} catch (IOException e) {}
		InputStream stream;
	}

	@Override
	public void run() {
		try {
			connectionSocket = serverSocket.accept();
		    stream = connectionSocket.getInputStream();
		    while (true) {
			    stream.read(data);
			    System.out.println("Received: " + " " + data[0] + (data[0]&0x2));
			    Thread.sleep(1);
			    controls.setThrottle( ((data[0]&0x1)==1)?true:false );
			    controls.setBrake( ((data[0]&0x2)==2)?true:false );
			    controls.setSteeringRight( ((data[0]&0x4)==4)?true:false );
			    controls.setSteeringLeft( ((data[0]&0x8)==8)?true:false );
		    }
		    //stream.close();
		    //connectionSocket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
			case 39: { controls.setSteeringLeft(true); break; }
			case 38: { controls.setThrottle(true); break; }
			case 37: { controls.setSteeringRight(true); break;}
			case 40: { controls.setBrake(true); break; }
			case 32: { controls.setHandbrake(true); break; }
			default: break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
			case 39: { controls.setSteeringLeft(false); break; }
			case 38: { controls.setThrottle(false); break; }
			case 37: { controls.setSteeringRight(false); break;}
			case 40: { controls.setBrake(false); break; }
			case 32: { controls.setHandbrake(false); break; }
		default: break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	public void setControls(Controls controls) {
		this.controls = controls;
	}
	
	public Controls getControls() {
		return this.controls;
	}

}
