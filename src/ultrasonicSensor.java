import java.util.ArrayList;

import lejos.nxt.I2CPort;
import lejos.nxt.UltrasonicSensor;


public class ultrasonicSensor extends UltrasonicSensor implements UpdatingSensor {
	
	public int value = 0;
	public ArrayList<SensorListener> listenerList;
	public ultrasonicSensor(I2CPort port) {
		super(port);
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
	}

	@Override
	public void updateState() {
		int newDistance = this.getDistance();
		if(value != newDistance)
		{
			if(this.listenerList.size() != 0)
			{
				for(SensorListener sl : listenerList)
				{
					sl.stateChanged(this, value, newDistance);
					value = newDistance;
				}
			}
		}	
	}
	public void addListener(SensorListener SL)
	{
		this.listenerList.add(SL);
	}
	
	@Override
	public String toString()
	{
		return "Ultrasonic sensor";
	}

}
