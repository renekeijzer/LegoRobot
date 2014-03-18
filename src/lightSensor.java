import java.util.ArrayList;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;

/**
 * @author      René Keijzer <>
 * @author 		tom Verloop <Tom_Verloop@live.nl>
 * @version     1.0
 * @since       18-3-2014
 *
 * Container class of the Light Sensor
 */

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

	/**
	 * Handles the update to the listeners
	 */
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
	/**
	 * Adds a Sensorlistener to the ListenerList
	 * 
	 * @param Sl
	 *            The listener to add
	 */
	public void addListener(SensorListener SL) {
		this.listenerList.add(SL);
	}
	/**
	 * @return returns the name of the sensor
	 */
	@Override
	public String toString() {
		return "Light sensor";
	}

}
