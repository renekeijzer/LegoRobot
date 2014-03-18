import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;


public class colorSensor extends ColorSensor implements UpdatingSensor{

	
	public int value = 0;
		
	public colorSensor(SensorPort port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateState() {
		
	}
	public void addListener(SensorListener Sl)
	{
		
	}
	
	

}
