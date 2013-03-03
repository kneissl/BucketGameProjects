package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public abstract class ComponentActor extends Actor {
		
	protected final Gamefield gamefield;
	protected final TextureRegion region;
	private boolean isSelected = false;
	private String name;
	private Vector2 pinScale;
	
	public ComponentActor(String name, TextureRegion region, float width, float height, Gamefield gamefield){
		this.name = name;
		this.region = region;
		this.gamefield = gamefield;
		float scaleX = width/region.getRegionWidth();
		float scaleY = height/region.getRegionHeight();
		Gdx.app.log("ComponentActor", scaleX + ", " + scaleY);
//		this.setScale(scaleX, scaleY);
		pinScale = new Vector2(scaleX, scaleY);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        
        if (isSelected){
        	batch.setColor(Color.WHITE);
        	GameResources.getGameTextures().selection.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
	}
	
	public void select(){
		isSelected = true;
	}
	
	public void deselect() {
		isSelected = false;	
	}

	public abstract void showWireDialog();
	
	@Override
	public String toString(){
		return name + "[" + getX() +", " + getY() + "]";
	}
	
	public static class ComponentPin{
		final String name;
		final ComponentActor component;
		final Vector2 pinOffset;
		
		public ComponentPin(String name, ComponentActor component, Vector2 pinOffset){
			this.name = name;
			this.component = component;
			this.pinOffset = new Vector2(pinOffset);
		}
		
		@Override
		public String toString(){
			return component.toString() + "@" + name;
		}

		public float getX() {
			return component.getX() + pinOffset.x*component.pinScale.x;
		}
		
		public float getY() {
			return component.getY() + pinOffset.y*component.pinScale.y;
		}
	}

}
