package client;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class AudioPlayer {
	
	public static Map<String, Sound> soundMap = new HashMap<String, Sound> ();
	public static Map<String, Music> musicMap = new HashMap<String, Music> ();

	public static void load()
	{
		try {
			musicMap.put("menuMusic", new Music("res/sounds/menu.wav"));
			soundMap.put("menuSelect", new Sound("res/sounds/menuclick.wav"));
			soundMap.put("cursorMove", new Sound("res/sounds/cursorMove.wav"));
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key)
	{
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key)
	{
		return soundMap.get(key);
	}
}
