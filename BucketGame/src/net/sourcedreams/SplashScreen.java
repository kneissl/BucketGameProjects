package net.sourcedreams;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;

public class SplashScreen extends AbstractScreen {
		
	private TextActor splashText;
	
	public SplashScreen(ScreenHandler pScreenHandler){
		super("SplashScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		//splashText = new TextActor(GameResources.getSkin().getFont("splash-font"));
		splashText = new TextActor("splash-font", GameResources.getSkin());
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
					run(new RunnableSendMessage(this, null))
			)
		);

		stage.addActor(splashText);
				
	}
	
	@Override
	public void onShow(){
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	}
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();		
		
		// Allows early bypass of SplashScreen
		if (Gdx.input.justTouched()){
			sendMessageToHandler(null);
		}
	}
}
