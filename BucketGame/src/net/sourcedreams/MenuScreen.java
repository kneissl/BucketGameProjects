package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MenuScreen extends AbstractScreen {
	
	public MenuScreen(ScreenHandler pScreenHandler) {
		super("MenuScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
	}
	
	@Override
	protected void onShow() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	}
		
	@Override
	public void render(float delta) {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		
		Table.drawDebug(stage);
		
		if (Gdx.input.justTouched()){
			notifyScreenHandlerOfCompletion();
		}
	}
	
}
