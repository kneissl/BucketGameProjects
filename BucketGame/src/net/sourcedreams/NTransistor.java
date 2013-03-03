package net.sourcedreams;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class NTransistor extends ComponentActor {

	static Dialog wireDialog;
	static NTransistor wireDialogComponent;
	
	private static void InitializeWireDialog(){
		wireDialog = new Dialog("Select Pin", GameResources.getSkin(), "dialog"){
			@Override
			protected void result(Object object) {
				if (object == null) {
					wireDialogComponent.gamefield.onPinConnection(null);
				} else {
					wireDialogComponent.onReturnFromWireDialog((PinType)object);
				}
				wireDialogComponent = null;
			}
		};
		wireDialog.button("Input", PinType.IN);
		wireDialog.button("Output", PinType.OUT);
		wireDialog.button("Gate", PinType.GATE);
		wireDialog.button("Cancel", null);
	}
	
	private static enum PinType{
		IN, OUT, GATE
	}
	
	private ComponentPin pinIn, pinOut, pinGate;
	
	public NTransistor(Gamefield gamefield, float width, float height) {
		super("NTransistor", GameResources.getGameTextures().NTransistorOFF, 
				GameResources.getGameTextures().NTransistorON, width, height, gamefield);
		Vector2 offsets = new Vector2();
		pinIn = new ComponentPin("Input", this, offsets.set(62,31));
		pinOut = new ComponentPin("Output", this, offsets.set(2,31));
		pinGate = new ComponentPin("Gate", this, offsets.set(31,31));
	}

	@Override
	public void showWireDialog() {
		if (wireDialog == null){
			InitializeWireDialog();
		}
		wireDialogComponent = this;
		wireDialog.show(gamefield.stage);
	}
	
	private void onReturnFromWireDialog(PinType type){
		ComponentPin toReturn = null;
		switch(type){
		case IN:
			toReturn = pinIn;
			break;
		case OUT:
			toReturn = pinOut;
			break;
		case GATE:
			toReturn = pinGate;
			break;
		}
		gamefield.onPinConnection(toReturn);
	}

}
