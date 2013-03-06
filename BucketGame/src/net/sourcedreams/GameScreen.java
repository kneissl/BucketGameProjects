package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreen extends AbstractScreen {

	Table table;
	TextButton buttonBack;
	
	Label statusMessage;
	
	ButtonGroup buttonGroup;
	Button buttonBandingBox;
	TextButton buttonWire;
	
	Gamefield gamefield;
	
	public GameScreen(ScreenHandler pScreenHandler) {
		super("GameScreen", pScreenHandler, GameResources.getSpriteBatch());
		
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
		
		statusMessage = new Label("", GameResources.getSkin());
		
		buttonBandingBox = new Button(new Image(GameResources.getGameTextures().bandingBoxIcon),
										GameResources.getSkin(),
										"toggle");
		
		buttonBandingBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gamefield.onBandingBoxToggle(((Button)actor).isChecked());
			}
		});
		
		buttonWire = new TextButton("Wire", GameResources.getSkin(), "toggle");
		buttonWire.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gamefield.onWireButtonToggle(((Button)actor).isChecked());
				
			}
		});
		
		buttonGroup = new ButtonGroup(buttonBandingBox, buttonWire);
		buttonGroup.setMinCheckCount(0);
		
		table.row().spaceBottom(15f);
		table.top().pad(5f);
		table.add().width(80).left();
		table.add(statusMessage).expandX();
		table.add(buttonBack).width(80).right();
		table.row();
		table.add(buttonBandingBox).width(80f).height(60).left();
		table.row();
		table.add(buttonWire).width(80f).height(60f).left();
		
		
		int gridWidth = (int) (stage.getWidth() -200f);
		int gridHeight = (int) (stage.getHeight()-130f);
		
		
		gamefield = new Gamefield(gridWidth, gridHeight, this);
		
		gamefield.setPosition((stage.getWidth()-gridWidth)/2f, (stage.getHeight()-gridHeight)/2f);
		
		stage.addActor(gamefield);
		gamefield.toBack();
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
	
	public void setStatusMessage(String msg){
		statusMessage.setText(msg);
	}
}
