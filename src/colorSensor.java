import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;


public class colorSensor extends ColorSensor implements UpdatingSensor{

	
	private int Lightvalue;
	public colorSensor(SensorPort port) {
		super(port);
		Lightvalue = 0;
	}

	@Override
	public void updateState() {
		
	}
	public void addListener(SensorListener Sl)
	{
		
	}
	
	

}
