package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen {
	
	protected final ScreenHandler screenHandler;
	protected final String screenName;
	
	protected final Stage stage;
	protected final SpriteBatch spriteBatch;
	
	
	public AbstractScreen(String screenName, ScreenHandler screenHandler, SpriteBatch spriteBatch){
		this.screenHandler = screenHandler;
		this.screenName = screenName;
	
		this.spriteBatch = spriteBatch;
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
	}
	
	@Override
	public void render(float delta) {}

	@Override
	public final void show() {
		Gdx.input.setInputProcessor(stage);
		onShow();
	}

	protected void onShow(){}
	
	@Override
	public final void hide() {
		onHide();
		Gdx.input.setInputProcessor(null);
	}
	
	protected void onHide(){}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	protected void onCompletion() {}
	
	@Override
	public final void resize(int width, int height) {
		if (onResize(width, height)) return;
		stage.setViewport(width, height, true);
	}
	
	/**
	 * Override this if you want to change the default resize behavior.
	 * @param width
	 * @param height
	 * @return true if implementation handled resize, false otherwise.
	 */
	protected boolean onResize(int width, int height){ 
		return false;
	}
	
	@Override
	public final void dispose() {
		onDispose();
		//As of libgdx 0.9.8, stage.dispose() only calls one method:
		// if it owns the SpriteBatch, it disposes it.
		// Since we've given it a SpriteBatch, it does not own it,
		// thus stage.dispose does nothing. Still call it here, in
		// case future behavior is added.
		stage.dispose();
	}
	
	protected void onDispose(){}

	@Override
	public String toString(){
		return screenName;
	}
	
	protected final void notifyScreenHandlerOfCompletion(){
		this.onCompletion();
		screenHandler.onScreenCompletion(this);
	}
	
	protected static class RunnableCompletionMessage implements Runnable{
		private AbstractScreen aScreen;
		public RunnableCompletionMessage(AbstractScreen screen){
			aScreen = screen;
		}
		
		@Override
		public void run() {
			aScreen.notifyScreenHandlerOfCompletion();
		}	
	}
}
