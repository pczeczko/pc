import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GamePanel extends JPanel {

	private BufferedImage playerImage;
	private BufferedImage trackImage;
	private PlayerObject player;
	private Rectangle mapBounds;
	private ArrayList<OpponentObject> opponents;
	
	public GamePanel() {
		File playerImageFile = new File("f1.png");
		try {
			playerImage = ImageIO.read(playerImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File trackImageFile = new File("track.jpg");
		try {
			trackImage = ImageIO.read(trackImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GamePanel(LayoutManager layout) {
		super(layout);

	}

	public GamePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public GamePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void setPlayer(PlayerObject player) {
		this.player = player;
	}
	
	public void setOpponents(ArrayList<OpponentObject> opponents) {
		this.opponents = opponents;
	}
	
	public BufferedImage getTrackImage() {
		return this.trackImage;
	}
	
	public void setMapBounds(Rectangle mapBounds) {
		this.mapBounds = mapBounds;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		rh=null;
		
		g2d.drawImage(trackImage, 0, 0, this);
		g2d.draw(mapBounds);
		g2d.setColor(new Color(255,50,20));
		g2d.setStroke(new BasicStroke(3));
		
		AffineTransform trans = new AffineTransform();
		trans.translate(player.bounds.getX(),player.bounds.getY());
		trans.rotate(player.getAngle(),player.bounds.getWidth()/6,player.bounds.getHeight()/2);
		g2d.drawImage(playerImage,trans,this);
		trans = null;
		
		g2d.drawImage(player.rgbtarmac, 0, 0, this);
		
		g2d.setColor(new Color((int)player.rgb));
		g2d.fillRect(1000, 10, 50, 50);
		
		g2d=null;
	}

}
