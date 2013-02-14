package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen extends AbstractScreen {

	ScreenHandler handler;
	
	public MenuScreen(ScreenHandler pScreenHandler) {
		super(pScreenHandler);
		
		handler = pScreenHandler;
	}

	public void render(float delta) {		
		Gdx.gl.glClearColor(1f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.justTouched()){
			handler.onScreenCompletion(this);
		}
	}
}
