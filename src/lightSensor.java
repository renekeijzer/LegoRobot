import java.util.ArrayList;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;


public class lightSensor extends LightSensor implements UpdatingSensor {
	
	public int value = 0;
	public ArrayList<SensorListener> listenerList;
	
	public lightSensor(ADSensorPort port) {
		super(port);
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
	}
	
	

	@Override
	public void updateState() {
		int newLightValue = this.getLightValue();
		if(value != newLightValue)
		{
			if(this.listenerList.size() != 0)
			{
				for(SensorListener sl : listenerList)
				{
					sl.stateChanged(this, value, newLightValue);
					value = newLightValue;
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
		return "Light sensor";
	}

}
