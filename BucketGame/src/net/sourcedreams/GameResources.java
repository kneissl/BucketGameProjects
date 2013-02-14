package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class GameResources implements Disposable {
	
	private static GameResources instance;
	
	public static void Initialize(){
		if (instance == null){
			instance = new GameResources();
		}
	}
	
	public static GameResources getInstance(){
		Initialize();
		return instance;
	}
	
	public static void Dispose(){
		if (instance != null){
			instance.dispose();
		}
	}
	
	private GameResources(){
		spriteBatch = new SpriteBatch();
//		arial_15 = new BitmapFont();
//		berlin_42 = new BitmapFont(Gdx.files.internal("BerlinSansFBDemi-42.fnt"), false);
//		berlin_80 = new BitmapFont(Gdx.files.internal("BerlinSansFBDemi-80.fnt"), false);
		agency_160 = new BitmapFont(Gdx.files.internal("data/AgencyFB-160.fnt"), false);
		beat = Gdx.audio.newMusic(Gdx.files.internal("data/beat.wav"));
	}
	
	protected final SpriteBatch spriteBatch;
//	protected final BitmapFont arial_15;
//	protected final BitmapFont berlin_42;
//	protected final BitmapFont berlin_80;
	protected final BitmapFont agency_160;
	protected final Music beat;
 
	@Override
	public void dispose() {
		spriteBatch.dispose();
//		arial_15.dispose();
//		berlin_42.dispose();
//		berlin_80.dispose();
		agency_160.dispose();
		beat.dispose();
		Gdx.app.log("GameResources", "Disposed!");
	}
	 
}
