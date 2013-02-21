package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class MenuScreen extends AbstractScreen {
	
	Table table;
	TextButton buttonTest1;
	TextButton buttonTest2;
	TextButton buttonExit;
	
	public MenuScreen(ScreenHandler pScreenHandler) {
		super("MenuScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		table = new Table();
		table.setFillParent(true);
//		table.debug();
		stage.addActor(table);
		
		buttonTest1 = new TextButton("Test 1", GameResources.getSkin());
		buttonTest2 = new TextButton("Test 2", GameResources.getSkin());
		
		buttonExit = new TextButton("Exit", GameResources.getSkin());
		buttonExit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("EXIT");
			}
		});
		
		buttonTest1.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("TEST1");
			}
		});
		
		table.defaults().width(200f).spaceBottom(20f);
		table.add(buttonTest1);
		table.row();
		table.add(buttonTest2);
		table.row();
		table.add(buttonExit);
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
