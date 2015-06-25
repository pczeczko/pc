import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JFrame;


public class MainFrame extends JFrame implements Runnable {
	
	private GamePanel gamePanel;
	private GameEngine gameEngine;
	//private OtherPanel otherPanel;

	public MainFrame() throws HeadlessException {
		super("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1400, 800);
		this.setLocation(50, 50);
		
		gamePanel = new GamePanel();
		add(gamePanel);
		setLayout(new GridLayout());
		setVisible(true);
		
	}

	public MainFrame(GraphicsConfiguration gc) {
		super(gc);
		
	}

	public MainFrame(String title) throws HeadlessException {
		super(title);
		
	}

	public MainFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		
	}

	public static void main(String[] args) {
		new MainFrame();
		
	}
	
	public GameEngine getGameEngine() {
		return this.gameEngine;
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				if(true||gameEngine.getUpdatedState()) {
					this.gamePanel.setPlayer(this.gameEngine.getPlayer());
					this.gamePanel.repaint();
					Thread.sleep(15);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
