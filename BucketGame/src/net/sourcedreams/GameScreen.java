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
	GridActor grid;
	
	public GameScreen(ScreenHandler pScreenHandler) {
		super("GameScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		table = new Table();
		table.setFillParent(true);
		table.debug();
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
		
		int gridSize = Math.min((int)stage.getWidth(), (int)stage.getHeight());
		gridSize = (int)(((float)gridSize * 0.8f) + .5f);
		
		grid = new GridActor();
		grid.setSize(gridSize, gridSize);
		
		grid.setPosition((stage.getWidth()-gridSize)/2f, (stage.getHeight()-gridSize)/2f);
		grid.addListener(grid.getGridInputListener());
		
		stage.addActor(grid);
		
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
		
	}
}
