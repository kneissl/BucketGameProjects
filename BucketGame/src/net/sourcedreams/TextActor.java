package net.sourcedreams;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TextActor extends Group {

	private BitmapFontCache cache;
	private TextChild textChild;

	public TextActor(BitmapFont pFont){
		this.cache = new BitmapFontCache(pFont);
		textChild = new TextChild(this);
		this.addActor(textChild);
	}
	
	public void setText(String text, boolean center){
		cache.setText(text, 0, 0);
		if (center){
			cache.setPosition(-cache.getBounds().width/2f, cache.getBounds().height/2f);
		}
	}
	
//	@Override
//	public void setColor (float r, float g, float b, float a){
//		super.setColor(r, g, b, a);
//		cache.setColor(r, g, b, a);
//	}
//	
//	@Override
//	public void setColor (Color color){
//		super.setColor(color);
//		cache.setColor(color);
//	}
	
//	public void centerAt(float cX, float cY){
//		this.setPosition(cX-textChild.getWidth()/2f, cY+textChild.getHeight()/2f);
//	}
	
//	@Override
//	public void setX(float pX){
//		super.setX(pX);
//		cache.setPosition(this.getX(), this.getY());
//	}
//	
//	@Override
//	public void setY(float pY){
//		super.setY(pY);
//		cache.setPosition(this.getX(), this.getY());
//	}
	
	private static class TextChild extends Actor{
		private TextActor parent;
		
		public TextChild(TextActor parent){
			this.parent = parent;
		}
		
		@Override
		public void draw (SpriteBatch batch, float parentAlpha){
			parent.cache.setColor(parent.getColor());
			parent.cache.draw(batch, parentAlpha);
		}
	}
}
