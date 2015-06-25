
public class Threads {
	


	public Threads() {
	}

	public static void main(String[] args) {
		
		GameEngine gameEngine = new GameEngine();
		Thread engineThread = new Thread(gameEngine);
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setGameEngine(gameEngine);
		mainFrame.getGamePanel().setPlayer(gameEngine.getPlayer());
		mainFrame.getGamePanel().setOpponents(gameEngine.getOpponents());
		gameEngine.setMap(mainFrame.getGamePanel().getTrackImage());
		mainFrame.getGamePanel().setMapBounds(gameEngine.getMapBounds());
		Thread graphicsThread = new Thread(mainFrame);
		
		Server controlServer = new Server();
		controlServer.setControls(gameEngine.getControls());
		Thread serverThread = new Thread(controlServer);
		
		serverThread.start();
		engineThread.start();
		graphicsThread.start();
		
	}

}
