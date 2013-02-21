package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class GameScreen extends AbstractScreen {

	Table table;
	TextButton buttonBack;
	
	
	public GameScreen(ScreenHandler pScreenHandler) {
		super("GameScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		table = new Table();
		table.setFillParent(true);
//		table.debug();
		stage.addActor(table);
		
		buttonBack = new TextButton("Back", GameResources.getSkin());
		buttonBack.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("MENU");
			}
		});
		
		table.top();
		table.add(buttonBack).expandX().left().padTop(5f).padLeft(5f);
			
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
		
//		Table.drawDebug(stage);
		
	}
}