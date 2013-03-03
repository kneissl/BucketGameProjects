package net.sourcedreams;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.visible;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;

public class SplashScreen extends AbstractScreen {
		
	private TextActor splashText;
	private TextActor loadingText;
	
	private boolean musicStarted = false;
	private boolean loadingComplete = false;
	private boolean readyToShowMenu = false;
	private boolean splashComplete = false;
	
	public SplashScreen(ScreenHandler pScreenHandler){
		super("SplashScreen", pScreenHandler, GameResources.getSpriteBatch());
		
		splashText = new TextActor(GameResources.getSplashFont());
		splashText.setText("BUCKET", true);
		splashText.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
		splashText.setColor(Color.WHITE);
//		splashText.setScale(.65f);
//		splashText.setColor(1f, 1f, 1f, .25f);
//		
//		splashText.addAction( 
//			sequence(
//					parallel(
//							scaleTo(1f, 1f, 3f, Interpolation.pow2Out),
//							fadeIn(2f, Interpolation.pow2Out)
//							),							
//					delay(1f),
//					fadeOut(2f, Interpolation.pow2Out),
//					run(new Runnable() {
//						
//						@Override
//						public void run() {
//							splashComplete = true;
//						}
//					})
//			)
//		);

		loadingText = new TextActor(GameResources.getDefaultFont());
		loadingText.setText("Loading... 0%", true);
		loadingText.setPosition(Gdx.graphics.getWidth()/2f, loadingText.getHeight());
		loadingText.setColor(1f,1f,1f,.6f);
		
		stage.addActor(splashText);
		stage.addActor(loadingText);				
	}
	
	@Override
	public void onShow(){
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	}
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (!musicStarted){
			Music music = GameResources.getMusic(); 
			if(music!= null){
				music.setLooping(true);
				music.play();
				musicStarted = true;
				Gdx.app.log(screenName, "Starting Music");
			}
		}
			
		
		if(!loadingComplete && GameResources.LoadStep()){
			// loading is finished
			loadingComplete = true;
			loadingText.setText("Loading Complete. Click to Continue.", true);
		} 
		
		if (!loadingComplete){
			float percentLoaded = GameResources.getPercentLoaded();
			Gdx.app.log("AssetManager: ", percentLoaded + " %");
			loadingText.setText("Loading... " + percentLoaded + "%", true);
		}
		
		stage.act(delta);
		stage.draw();		
		
		// Allows early bypass of SplashScreen
		if (Gdx.input.justTouched()){
			if (loadingComplete){
				sendMessageToHandler("MENU");
			}
		}
//		} else if (splashComplete && loadingComplete){
//			sendMessageToHandler("MENU");
//		}
	}
	
}
