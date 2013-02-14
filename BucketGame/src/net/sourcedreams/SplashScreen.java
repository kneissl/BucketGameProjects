package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends AbstractScreen {
		
	private Stage stage;
	private TextActor splashText;
	
	public SplashScreen(ScreenHandler pScreenHandler){
		super(pScreenHandler);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, GameResources.getInstance().spriteBatch);
		
		splashText = new TextActor(GameResources.getInstance().agency_160);
		splashText.setText("BUCKET", true);
		splashText.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
		splashText.setScale(.75f);
		splashText.setColor(1f, 1f, 1f, .5f);
		
		splashText.addAction( 
			sequence(
					parallel(
							scaleTo(1f, 1f, 2f, Interpolation.pow2Out),
							fadeIn(1f, Interpolation.pow2Out)
							),							
					delay(1f),
					fadeOut(1f, Interpolation.pow2Out),
					run(new CompletionMessage(pScreenHandler, this))
			)
		);

		stage.addActor(splashText);
				
	}
	
	@Override
	public void show(){
		GameResources.getInstance().beat.setLooping(true);
		GameResources.getInstance().beat.play();
	}
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();		
	}
	
	@Override
	public void dispose(){
		stage.dispose();
	}
	
	private static class CompletionMessage implements Runnable{
		private ScreenHandler aHandler;
		private SplashScreen aScreen;
		public CompletionMessage(ScreenHandler handler, SplashScreen screen){
			aHandler = handler;
			aScreen = screen;
		}
		@Override
		public void run() {
			aHandler.onScreenCompletion(aScreen);
		}
		
	}

}
