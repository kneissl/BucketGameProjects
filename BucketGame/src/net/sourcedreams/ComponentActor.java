package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ComponentActor extends Actor {
		
	protected final Gamefield gamefield;
	protected final Texture regionOff;
	protected final Texture regionOn;
	
	private boolean isOn = false;
	private boolean isSelected = false;
	private String name;
	private Vector2 pinScale;
	
//	public ComponentActor(String name, TextureRegion regionOff, TextureRegion regionOn, float width, float height, Gamefield gamefield){
	public ComponentActor(String name, Texture regionOff, Texture regionOn, float width, float height, Gamefield gamefield){
		this.name = name;
		this.regionOff = regionOff;
		this.regionOn = regionOn;
		this.gamefield = gamefield;
		float scaleX = width/regionOff.getWidth();
		float scaleY = height/regionOff.getHeight();
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
        if (isOn){
        	batch.draw(regionOn, getX(), getY(), getWidth(), getHeight());
//	        batch.draw(regionOn, getX(), getY(), getOriginX(), getOriginY(),
//	                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else {
        	batch.draw(regionOff, getX(), getY(), getWidth(), getHeight());
//        	batch.draw(regionOff, getX(), getY(), getOriginX(), getOriginY(),
//	                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        
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
		
//		final boolean testHorizontal;
//		boolean testRight;
		
		public ComponentPin(String name, ComponentActor component, Vector2 pinOffset){
			this.name = name;
			this.component = component;
			this.pinOffset = new Vector2(pinOffset);
			
//			testRight = false;
//			
//			if(pinOffset.x >= .5f*component.getWidth()){
//				if (pinOffset.x/component.getWidth() > .75f){
//					testHorizontal = true;
//					testRight = true;
//				} else {
//					testHorizontal = false;
//				}
//			} else if (pinOffset.x/component.getWidth() < .25f){
//				testHorizontal = true;
//				testRight = false;
//			} else testHorizontal = false;
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
