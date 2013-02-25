package net.sourcedreams;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BucketGame extends Game implements ScreenHandler {
	public static final String logTag = "BucketGame";
	
	private FPSLogger fpsLogger;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	private SpriteActor bucket;
	
	private SplashScreen splashScreen;
	private MenuScreen menuScreen;
	private OptionsScreen optionsScreen;
	private GameScreen gameScreen;
	
	@Override
	public void create() {		
		Gdx.app.log(logTag, "create()");
		fpsLogger = new FPSLogger();
		
		GameResources.Initialize();
		
		splashScreen = new SplashScreen(this);
		
		this.setScreen(splashScreen);
	}
	
	@Override
	public void receiveMessageFromScreen(final Screen scr, String msg) {
		if (msg!=null){
			Gdx.app.log(logTag, "Received Message \"" + msg + "\" from " + scr.toString());
		} else {
			Gdx.app.log(logTag, "Received Null Message from " + scr.toString());
		}

		if (scr == splashScreen){
			
			if (msg.equals("ASSETSLOADED")){
				loadScreens();
				((SplashScreen)scr).onReadyToShowMenu();
				Gdx.app.log(logTag, "All Screens Loaded");
				
			} else if (msg.equals("MENU")){
				setScreen(menuScreen);
			} else {
				unknownMessage(scr, msg);
			}

		} else if (scr == menuScreen){
			
			if (msg.equals("EXIT")){
				Gdx.app.exit();
			} else if (msg.equals("PLAY")){
				setScreen(gameScreen);
			} else if (msg.equals("OPTIONS")){
				setScreen(optionsScreen);
			} else {
				unknownMessage(scr, msg);		
			}
		
		} else if (scr == optionsScreen){ 
			
			if (msg.equals("MENU")){
				setScreen(menuScreen);
			} else {
				unknownMessage(scr, msg);
			}
		
		} else if (scr == gameScreen){
			
			if (msg.equals("MENU")){
				setScreen(menuScreen);
			} else {
				unknownMessage(scr, msg);
			}
			
		} else {
			Gdx.app.log(logTag, "Unknown Screen: " + scr);
		}
	}

	
	private void loadScreens() {
		menuScreen = new MenuScreen(this);
		optionsScreen = new OptionsScreen(this);
		gameScreen = new GameScreen(this);
	}

	private void unknownMessage(Screen scr, String msg) {
		Gdx.app.log(logTag, "Unknown Message from " + scr + ": " + msg);
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(logTag, "dispose()");
		splashScreen.dispose();
		menuScreen.dispose();
		GameResources.Dispose();
	}

	@Override
	public void render() {		
		super.render();
		fpsLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.app.log(logTag, "resize("+width+", "+height+")");
	}

	@Override
	public void pause() {
		super.pause();
		Gdx.app.log(logTag, "pause()");
	}

	@Override
	public void resume() {
		GameResources.Initialize();
		super.resume();
		Gdx.app.log(logTag, "resume()");
	}


}
