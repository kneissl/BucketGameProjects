package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

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
		assetManager = new AssetManager();
		spriteBatch = new SpriteBatch();
		splashFont = new BitmapFont(Gdx.files.internal("ui/AgencyFB-160.fnt"), false);
		defaultFont = new BitmapFont(Gdx.files.internal("ui/AgencyFB-32.fnt"), false);
		
		assetManager.load("audio/beat.ogg", Music.class);
		assetManager.load("ui/uiskin.json", Skin.class);

//		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));		
//		beat = Gdx.audio.newMusic(Gdx.files.internal("audio/beat.ogg"));
		
	}
	
	private final AssetManager assetManager;
	private final SpriteBatch spriteBatch;
	private Skin skin;
	private BitmapFont defaultFont;
	private BitmapFont splashFont;
	private Music beat;
	
	public static AssetManager getAssetManager(){
		Initialize();
		return instance.assetManager; 
	}
	
	public static SpriteBatch getSpriteBatch(){
		Initialize();
		return instance.spriteBatch;
	}
	
	public static Music getMusic(){
		Initialize();
		if (instance.beat == null){
			if (instance.assetManager.isLoaded("audio/beat.ogg")){
				instance.beat = instance.assetManager.get("audio/beat.ogg");
			} else return null;
		} 
		return instance.beat;
	}	
	
	public static Skin getSkin(){
		Initialize();
		if (instance.skin == null){
			if (instance.assetManager.isLoaded("ui/uiskin.json")){
				instance.skin = instance.assetManager.get("ui/uiskin.json");
			} else return null;
		}
		return instance.skin;
	}
	
	public static BitmapFont getDefaultFont(){
		Initialize();
		return instance.defaultFont;
	}
	
	public static BitmapFont getSplashFont(){
		Initialize();
		return instance.splashFont;
	}
 
	@Override
	public void dispose() {
		assetManager.dispose();

//		assumedly taken care of by assetManager.dispose()
//		skin.dispose();
//		beat.dispose();
		
		defaultFont.dispose();
		splashFont.dispose();
		
		spriteBatch.dispose();
		
		instance = null;
		
		Gdx.app.log("GameResources", "Disposed!");
	}

}
