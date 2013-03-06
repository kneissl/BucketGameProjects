package net.sourcedreams;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import net.sourcedreams.ComponentActor.ComponentPin;

public class Wire {
	
	ComponentPin startPin;
	ComponentPin stopPin;
	
	static float offset = 20;
	static Vector2 pointA = new Vector2();
	static Vector2 pointB = new Vector2();
	
	public void draw(ShapeRenderer renderer) {
		renderer.line(	startPin.getX(), 
				startPin.getY(), 
				stopPin.getX(), 
				stopPin.getY());
	}

}
