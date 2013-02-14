package net.sourcedreams;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	
	protected final ScreenHandler handler;
	
	public AbstractScreen(ScreenHandler pScreenHandler){
		this.handler = pScreenHandler;
	}
	
	@Override
	public void render(float delta) {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
