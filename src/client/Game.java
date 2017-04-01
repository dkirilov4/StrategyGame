package client;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import managers.ObjectManager;
import managers.ScreenManager;
import managers.StageManager;

public class Game extends Canvas implements Runnable
{
	private static Game instance = null;
	public static final int WIDTH = 640, HEIGHT = 480;
	
	public static Graphics g;

	public enum STATE {MainMenu, CharacterMenu, PauseMenu, HelpMenu, Playing, Inventory, Victory}
	public STATE gameState;
	
	private boolean running;
	public static boolean paused;
	
	private Thread thread;
	
	public Game()
	{
		gameState = STATE.MainMenu;
		running = false;
		paused  = false;
		
		AudioPlayer.load();

	}
	
	private void tick()
	{
		ObjectManager.get().tick();
		ScreenManager.get().tickActiveScreen();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		ScreenManager.get().renderActiveScreen();
		
		g.dispose();
		bs.show();
	}
	
	public synchronized void start()
	{
		new Window(WIDTH, HEIGHT, "War Wars", this);
     		this.addKeyListener(ScreenManager.get());
		
		thread = new Thread(this);
		thread.start();
		
		running = true;
	}
	
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
                            
            if(System.currentTimeMillis() - timer > 1000)
            {
                 timer += 1000;
                 //System.out.println("FPS: "+ frames);
                 frames = 0;
             }
        }
        stop();
	}
	
	public static void main (String args[])
	{
		Game.get().start();
	}
	
	public static Game get()
	{
		if (instance == null)
			instance = new Game();
		
		return instance;
	}

}
