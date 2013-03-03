package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends AbstractScreen {
	
	Table table;
	TextButton buttonPlay;
	TextButton buttonOptions;
	TextButton buttonExit;
	
	public MenuScreen(ScreenHandler pScreenHandler) {
		super("MenuScreen", pScreenHandler, GameResources.getSpriteBatch());
		
		table = new Table();
		table.setFillParent(true);
//		table.debug();
		stage.addActor(table);
		
		buttonPlay = new TextButton("Play", GameResources.getSkin());
		buttonOptions = new TextButton("Options", GameResources.getSkin());
		
		buttonExit = new TextButton("Exit", GameResources.getSkin());
		buttonExit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("EXIT");
			}
		});
		
		buttonPlay.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("PLAY");
			}
		});
		
		buttonOptions.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("OPTIONS");
			}
		});

		table.defaults().width(200f).spaceBottom(20f);
		table.add(buttonPlay);
		table.row();
		table.add(buttonOptions);
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
