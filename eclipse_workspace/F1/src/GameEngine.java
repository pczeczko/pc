import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;


public class GameEngine implements Runnable {

	private PlayerObject player, playerLastState;
	private ArrayList<OpponentObject> opponents;
	private Controls controls;
	private BufferedImage map;
	private Rectangle mapBounds;
	private boolean isUpdated = false;
	
	public GameEngine() {
		controls = new Controls();
		player = new PlayerObject(new Rectangle2D.Double(150,300,60,31));
		copyPlayerState();
		opponents = new ArrayList<OpponentObject>();
		mapBounds = new Rectangle(5,5,1280,600);
		for (int i=0; i<10; i++)
		{
			Rectangle2D.Double tempRect = new Rectangle2D.Double(50*i,10*i,20,25);
			OpponentObject tempOpponent = new OpponentObject(tempRect);
			opponents.add(tempOpponent);
		}
	}

	public PlayerObject getPlayer() {
		return this.playerLastState;
	}

	public ArrayList<OpponentObject> getOpponents() {
		return this.opponents;
	}

	public Controls getControls() {
		return this.controls;
	}
	
	public void update() {
		
		Rectangle2D.Double tempBounds = new Rectangle2D.Double(
				player.bounds.getX(),
				player.bounds.getY(),
				player.bounds.getWidth(),
				player.bounds.getHeight());
		double tempAngle = player.angle;
		double tempVelocity = player.velocity;
		double tempGrip = player.grip;
		player = null;
		player = new PlayerObject(tempBounds);
		player.velocity = tempVelocity;
		player.angle = tempAngle;
		player.grip = tempGrip;
		tempBounds = null;
		
		steer();
		
		AffineTransform trans = new AffineTransform();
		trans.translate((int)player.bounds.getX(),(int)player.bounds.getY());
		trans.rotate(player.getAngle(),player.bounds.getWidth()/6,player.bounds.getHeight()/2);
		player.shape=null;
		player.shape = trans.createTransformedShape(player.shadow);
		trans=null;
		
		if(!mapBounds.contains(player.shape.getBounds())) {
			if(player.getVelocity()>0.1) {
				player.moveObject(-player.getVelocity()*4*Math.cos(player.getAngle()),-player.getVelocity()*4*Math.sin(player.getAngle()));
			}
			player.setVelocity(-player.getVelocity()/2);
		}
		
		try {
			
			byte[] tarmac = new byte[12000];
			tarmac = ((DataBufferByte)map.getData(player.scope.getBounds()).getDataBuffer()).getData();

			int psgw=(int)player.scope.getBounds().getWidth();
			int psgh=(int)player.scope.getBounds().getHeight();
			
			for (int i=0; i<psgw; i++)
			{
				for (int j=0; j<psgh; j++)
				{
					int index = j+i*psgh;
					int red = tarmac[3*index];
					int green = tarmac[3*index+1];
					int blue = tarmac[3*index+2];
					int rgb = ((int)red << 16) | ((int)green << 8) | (int)blue;
					player.rgbtarmac.setRGB(j, i, rgb);
				}
			}
			
			for (int i=0; i<10; i++);
			
			trans = new AffineTransform();
			trans.translate(43,38); //taka geometria, trzeba uogólnić
			trans.rotate(player.getAngle(),player.bounds.getWidth()/6,player.bounds.getHeight()/2);
			player.scopeShape = trans.createTransformedShape(player.shadow);
			trans=null;

			int pts=0, r=0, g=0, b=0;
			for (int i=0; i<psgw; i++)
			{
				for (int j=0; j<psgh; j++)
				{
					if(player.scopeShape.contains(j, i)) {
						int index = j+i*psgh;
						r += tarmac[3*index];
						g += tarmac[3*index+1];
						b += tarmac[3*index+2];
						if (Math.abs(tarmac[3*index]-70)<10 && Math.abs(tarmac[3*index+1]-80)<10 && Math.abs(tarmac[3*index+2]-80)<10) player.grip++;
						pts++;
					}
				}
			}
			r/=pts;
			g/=pts;
			b/=pts;
			player.rgb=(r << 16) | (g << 8) | b;
			player.grip/=pts;
			if(player.grip<0.5) player.grip=0.5;
			tarmac=null;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Scope out of map!");
		}
		
	}
	
	public void steer() {
		
		if(Math.abs(player.getVelocity())<0.001) player.setVelocity(0);
		
		if(player.grip<0.7) player.setVelocity(player.getVelocity()-0.0025);
		
		if (player.getVelocity()>0) player.setVelocity(player.getVelocity()-0.005);
		else if(player.getVelocity()<0) player.setVelocity(player.getVelocity()+.005);
		
		if(controls.getThrottle()) {
			if (player.getVelocity()>0) player.setVelocity(player.getVelocity()+.02*player.grip);
			else player.setVelocity(player.getVelocity()+.05);
		}
		if(controls.getBrake()) {
			if(player.getVelocity()>0) player.setVelocity(player.getVelocity()-.05);
			else player.setVelocity(player.getVelocity()-.01*player.grip);
		}
		if(controls.getSteeringLeft()) player.setAngle(player.getAngle()+.02);
		if(controls.getSteeringRight()) player.setAngle(player.getAngle()-.02);
		if(controls.getHandbrake()) player.setVelocity(0);
		
		player.moveObject(player.getVelocity()*Math.cos(player.getAngle()), player.getVelocity()*Math.sin(player.getAngle()));
		
	}
	
	public void setMap(BufferedImage map) {
		this.map = map;
	}
	
	public Rectangle getMapBounds() {
		return this.mapBounds;
	}
	
	public boolean getUpdatedState() {
		return this.isUpdated;
	}
	
	public void copyPlayerState() {
		isUpdated = false;
		playerLastState = null;
		playerLastState = player;
		//playerLastState = player.copy();
		isUpdated = true;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				long start=System.nanoTime();
				update();
				copyPlayerState();
				long stop=System.nanoTime();
				long delta=(stop-start)/1000000;
				if (delta<6) Thread.sleep(6-delta);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
