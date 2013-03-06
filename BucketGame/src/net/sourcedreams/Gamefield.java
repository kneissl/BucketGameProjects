package net.sourcedreams;

import net.sourcedreams.ComponentActor.ComponentPin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class Gamefield extends Group {

	private ShapeRenderer renderer;
	private final GamefieldGestureListener gamefieldGestureListener;
	
	private final int nCols = 40;
	private final int nRows;
	
	final GameScreen gameScreen;
	
	private final float colWidth;
	private final float rowHeight;
	
	private Image bandingBoxImage;
	private boolean isBandingBoxEnabled;	
	private boolean isWireModeEnabled;
	
	private Wire wireInProgress;
	
	private Array<ComponentActor> components;
	private Array<ComponentActor> selectedComponents;
	private Array<Wire>	wires;
	
	
	public Gamefield(int pWidth, int pHeight, GameScreen gameScreen){
		this.gameScreen = gameScreen;
		renderer = new ShapeRenderer();
		gamefieldGestureListener = new GamefieldGestureListener(this);
		this.addListener(gamefieldGestureListener);
		
		float aspect = (float)pHeight/(float)(pWidth);
		nRows = Math.round(nCols*aspect);
		Gdx.app.log("Gamefield", "Aspect: " + aspect + "["+nRows+", "+nCols+"]");
		
		this.setSize(pWidth, pHeight);
		colWidth = pWidth/(float)nCols;
		rowHeight = pHeight/(float)nRows;
				
		selectedComponents = new Array<ComponentActor>();
		components = new Array<ComponentActor>();
		wires = new Array<Wire>();
		
		for (int i=0; i<4; i++){
			
			ComponentActor testComponent = new NTransistor(this, colWidth*4, rowHeight*4);
			testComponent.setPosition((float)Math.random()*pWidth*.8f, (float)Math.random()*pHeight*.8f);
//			testComponent.setColor(Color.WHITE);
//			testComponent.setWidth(colWidth);
//			testComponent.setHeight(rowHeight);
			Gdx.app.log("testComponent #" + i, testComponent.getWidth() + ", " + testComponent.getHeight());
			
			components.add(testComponent);
			this.addActor(testComponent);
		}
		
		bandingBoxImage = new Image(new NinePatchDrawable(GameResources.getGameTextures().selection));
		bandingBoxImage.setVisible(false);
		this.addActor(bandingBoxImage);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {		
		batch.end();
		
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX(), getY(), 0);
				
		renderer.begin(ShapeType.Line);
		
		renderer.setColor(.3f,.3f,.3f,1f);
		
		for (int c = 0; c <= nCols; c++){
			renderer.line(c*colWidth, 0, c*colWidth, getHeight());
		}
		for (int r = 0; r <= nRows; r++){
			renderer.line(0, r*rowHeight, getWidth(), r*rowHeight);
		}
		
		renderer.setColor(Color.WHITE);
		
		for (Wire w : wires){
			w.draw(renderer);
		}
		
		renderer.end();
		
		batch.begin();
		
		super.draw(batch, parentAlpha);
	}
	
	private void clearComponentSelection(){
		for (ComponentActor c : selectedComponents){
			c.deselect();
		}
		selectedComponents.clear();
	}
	
	private void addToComponentSelection(ComponentActor c){
		if (selectedComponents.contains(c, true)) return;
		selectedComponents.add(c);
		c.select();
	}
	
	public void onBandingBoxToggle(boolean checked) {
		isBandingBoxEnabled = checked;
		if (isBandingBoxEnabled){
			gameScreen.setStatusMessage("Selection Mode: Drag to select Components.");
		} else {
			gameScreen.setStatusMessage("");
		}
	}
	
	public void onWireButtonToggle(boolean checked) {
		isWireModeEnabled = checked;
		if (isWireModeEnabled){
			gameScreen.setStatusMessage("Wire Mode: Select the first Component.");
		} else {
			gameScreen.setStatusMessage("");
		}
	}

	public void onPinConnection(ComponentPin pin){
		if (pin == null) return;
		if (wireInProgress == null){
			wireInProgress = new Wire();
			wireInProgress.startPin = pin;
			gameScreen.setStatusMessage("Wire Mode: Select the second Component.");
		} else {
			wireInProgress.stopPin = pin;
			wires.add(wireInProgress);
			gameScreen.setStatusMessage("Wire Mode: Select the first Component.");
//			Gdx.app.log("WIRE", "Added: " + wireInProgress.startPin + ", " + wireInProgress.stopPin);
			wireInProgress = null;
		}
	}
//	private static void assertComponentActor(Actor a){
//		assert(a instanceof ComponentActor);
//	}
	
	private static class GamefieldGestureListener extends ActorGestureListener{
		private Gamefield gamefield;
		private Vector2 touchDownCoordinates;
		private Vector2 minCorner;
		private Vector2 maxCorner;
		
		
		public GamefieldGestureListener(Gamefield field){
			super(10, 0.4f, 1.1f, 0.15f);
			this.gamefield = field;
			touchDownCoordinates = new Vector2();
			minCorner = new Vector2();
			maxCorner = new Vector2();
		}
		
		@Override
		public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
			if (gamefield.isBandingBoxEnabled){
				touchDownCoordinates.set(x, y);
			}
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if (gamefield.isBandingBoxEnabled){
				gamefield.bandingBoxImage.setVisible(false);
			}
		}
		
		@Override
		public void tap(InputEvent event, float x, float y, int count, int button) {
			Gdx.app.log("Gamefield", "tap: " + getTouchDownTarget());		
			
			gamefield.clearComponentSelection();
			
			if (gamefield.isWireModeEnabled){
				if (getTouchDownTarget() != gamefield){
					((ComponentActor)getTouchDownTarget()).showWireDialog();						
				}
				return;
			}
				
			if (getTouchDownTarget() != gamefield){
				gamefield.addToComponentSelection((ComponentActor)getTouchDownTarget());
			}
		}
		
		@Override
		public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
			
			if (getTouchDownTarget() != gamefield){
				if (!gamefield.selectedComponents.contains((ComponentActor)getTouchDownTarget(), true)){
					gamefield.clearComponentSelection();
					gamefield.addToComponentSelection((ComponentActor)getTouchDownTarget());
				}
				for (ComponentActor c : gamefield.selectedComponents){
					c.translate(deltaX, deltaY);
				}
			} else if (gamefield.isBandingBoxEnabled){
//				Gdx.app.log("Banding", touchDownCoordinates.toString() + " " + (x-touchDownCoordinates.y) + ", " + (y-touchDownCoordinates.y));
				float width = x-touchDownCoordinates.x;
				float height = y-touchDownCoordinates.y;
				minCorner.set(touchDownCoordinates.x, touchDownCoordinates.y);
				
				if (width < 0){
					minCorner.x = touchDownCoordinates.x + width;
					width = -width;
				}
				if (height < 0){
					minCorner.y = touchDownCoordinates.y + height;
					height = -height;
				}
				
				maxCorner.set(minCorner.x + width, minCorner.y + height);
				
				gamefield.bandingBoxImage.setPosition(minCorner.x, minCorner.y);
				gamefield.bandingBoxImage.setSize(width, height);
				gamefield.bandingBoxImage.invalidate();
				gamefield.bandingBoxImage.setVisible(true);
				
				gamefield.clearComponentSelection();
				for (ComponentActor c : gamefield.components){
					if (c.getX() >= minCorner.x){
						if (c.getX()+c.getWidth() <= maxCorner.x){
							if (c.getY() >= minCorner.y){
								if (c.getY()+c.getHeight() <= maxCorner.y){
									gamefield.addToComponentSelection(c);
								}
							}
						}
					}
				}
			} 
		}
	}
}
