package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class OptionsScreen extends AbstractScreen {
	
	Table table;
	TextButton buttonMusic;
	TextButton buttonMenu;
	Slider sliderMusicVolume;
	
	private boolean music = true;
	
	public OptionsScreen(ScreenHandler pScreenHandler) {
		super("OptionsScreen", pScreenHandler, GameResources.getSpriteBatch());
		
		table = new Table();
		table.setFillParent(true);
//		table.debug();
		stage.addActor(table);
		
		buttonMusic = new TextButton("Music: ON", GameResources.getSkin());
		buttonMusic.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				music = !music;
				if (music){
					GameResources.getMusic().play();
					buttonMusic.setText("Music: ON");
				} else {
					GameResources.getMusic().stop();
					buttonMusic.setText("Music: OFF");
				}
			}
		});
		
		sliderMusicVolume = new Slider(0, 1, .01f, false, GameResources.getSkin());
		sliderMusicVolume.setValue(1f);
		
		sliderMusicVolume.addListener(new ChangeListener(){
		
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameResources.getMusic().setVolume(((Slider)actor).getValue());
			}
			
		});
				
		buttonMenu = new TextButton("Back", GameResources.getSkin());
		buttonMenu.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sendMessageToHandler("MENU");
			}
		});
		
		
		table.defaults().width(200f).spaceBottom(20f).spaceRight(20f);
		table.add(buttonMusic);
		table.add(sliderMusicVolume);
		table.row();
		table.add(buttonMenu).colspan(2).spaceTop(40f);
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
