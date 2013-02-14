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
		
		GameResources.Initialize();
		
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		
		this.setScreen(splashScreen);
		
//		loadAssets();
		
//		bucket = new DynamicSprite(bucketImage);
//		bucket.setPosition(viewportWidth/2f, 50f);
//		
//		rainMusic.setLooping(true);
//		rainMusic.play();
		
		fpsLogger = new FPSLogger();
	}

	private void loadAssets() {
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	}
	
	@Override
	public void onScreenCompletion(Screen scr) {
		Gdx.app.log(logTag, "Screen " + scr + " completed.");

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
		
		GameResources.Dispose();
	}

	@Override
	public void render() {		
		super.render();
//		Gdx.gl.glClearColor(.56f, .71f, .8f, 1);
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		
//		camera.update();
//		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		bucket.drawCentered(batch);
//		batch.end();
		
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
