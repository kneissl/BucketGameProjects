package net.sourcedreams;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor{
	
	private Sprite sprite;
	
	public SpriteActor(Texture pTexture){
		sprite = new Sprite(pTexture);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}
	
	@Override
	public void setColor (float r, float g, float b, float a){
		super.setColor(r, g, b, a);
		sprite.setColor(r, g, b, a);
	}
	
	@Override
	public void setColor (Color color){
		super.setColor(color);
		sprite.setColor(color);
	}
}
