package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class GameResources implements Disposable {
	
	private static GameResources instance;
	
	/**
	 * Forces initialization of GameResources, otherwise they will be lazily 
	 * instantiated (all at once) upon first call to GameResources.getInstance().
	 */
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
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		splashFont = skin.getFont("splash-font");
		defaultFont = skin.getFont("default-font");
		
		beat = Gdx.audio.newMusic(Gdx.files.internal("audio/beat.ogg"));
	}
	
	protected final SpriteBatch spriteBatch;
	private final Skin skin;
	protected final BitmapFont defaultFont;
	protected final BitmapFont splashFont;
	protected final Music beat;
 
	@Override
	public void dispose() {
		spriteBatch.dispose();
		skin.dispose();

//		assumedly taken care of by skin.dispose()
//		defaultFont.dispose();
//		splashFont.dispose();

		beat.dispose();
		
		instance = null;
		
		Gdx.app.log("GameResources", "Disposed!");
	}

	static Skin getSkin() {
		return getInstance().skin;
	}
	 
}
