import java.util.ArrayList;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;

public class lightSensor extends LightSensor implements UpdatingSensor {

	private int Lightvalue;
	private ArrayList<SensorListener> listenerList;

	public lightSensor(ADSensorPort port) {
		super(port);
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Lightvalue = 0;
	}

	@Override
	public void updateState() {
		int newLightValue = this.getLightValue();
		if (Lightvalue != newLightValue) {
			if (this.listenerList.size() != 0) {
				for (SensorListener sl : listenerList) {
					sl.stateChanged(this, Lightvalue, newLightValue);
					Lightvalue = newLightValue;
				}
			}
		}
	}

	public void addListener(SensorListener SL) {
		this.listenerList.add(SL);
	}

	@Override
	public String toString() {
		return "Light sensor";
	}

}
