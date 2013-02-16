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
	
	@Override
	public void create() {		
		Gdx.app.log(logTag, "create()");
		fpsLogger = new FPSLogger();
		
		GameResources.Initialize();
		
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		
		this.setScreen(splashScreen);
		
	}
	
	@Override
	public void onScreenCompletion(Screen scr) {
		Gdx.app.log(logTag, scr.toString() + " completed.");

		if (scr == splashScreen){
			
			setScreen(menuScreen);

		} else if (scr == menuScreen){
			
			this.setScreen(null);
			Gdx.app.exit();
			
		}
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
		super.resume();
		Gdx.app.log(logTag, "resume()");
	}


}
