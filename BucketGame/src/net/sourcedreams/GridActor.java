package net.sourcedreams;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GridActor extends Actor {
	
	private ShapeRenderer renderer;
	private final GridInputListener gridInputListener;
	
	
	public GridActor(){
		renderer = new ShapeRenderer();
		gridInputListener = new GridInputListener();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX(), getY(), 0);
		
		renderer.setColor(Color.BLUE);
		renderer.begin(ShapeType.FilledRectangle);
		
		renderer.filledRect(0, 0, getWidth(), getHeight());
		
		renderer.end();
		
		renderer.setColor(Color.RED);
		
		renderer.begin(ShapeType.Line);
		
		renderer.line(0, gridInputListener.tempY, getWidth(), gridInputListener.tempY);
		renderer.line(gridInputListener.tempX, 0, gridInputListener.tempX, getHeight());
		
		renderer.end();
		
		batch.begin();
	}

	public EventListener getGridInputListener() {
		return gridInputListener;
	}
	
	public static class GridInputListener extends InputListener{
		private float tempX, tempY;
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			tempX = x;
			tempY = y;
			return true;
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			tempX = x;
			tempY = y;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			tempX = x;
			tempY = y;
		}
	}
}
