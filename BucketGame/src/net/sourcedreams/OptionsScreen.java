package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class OptionsScreen extends AbstractScreen {
	
	Table table;
	TextButton buttonMusic;
	TextButton buttonMenu;
	
	private boolean music = true;
	
	public OptionsScreen(ScreenHandler pScreenHandler) {
		super("OptionsScreen", pScreenHandler, GameResources.getInstance().spriteBatch);
		
		table = new Table();
		table.setFillParent(true);
//		table.debug();
		stage.addActor(table);
		
		buttonMusic = new TextButton("Music: ON", GameResources.getSkin());
		
		buttonMenu = new TextButton("Back", GameResources.getSkin());
		buttonMenu.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("MENU");
			}
		});
		
		buttonMusic.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				music = !music;
				if (music){
					GameResources.getInstance().beat.play();
					buttonMusic.setText("Music: ON");
				} else {
					GameResources.getInstance().beat.stop();
					buttonMusic.setText("Music: OFF");
				}
			}
		});
		
		table.defaults().width(200f).spaceBottom(20f);
		table.add(buttonMusic);
		table.row();
		table.add(buttonMenu);
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
