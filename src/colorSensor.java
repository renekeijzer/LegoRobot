import java.util.ArrayList;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

/**
 * @author René Keijzer <>
 * @author tom Verloop <Tom_Verloop@live.nl>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Container class of the Color Sensor
 */

public class colorSensor extends ColorSensor implements UpdatingSensor {

	private int _high = 0;
	private int _low = 1023;
	private int Lightvalue; // /< keeps the value of the lightsensor
	private ArrayList<SensorListener> listenerList; // /< keeps a list of
													// Sensorlisteners

	public colorSensor(SensorPort port) {
		super(port);
		_high = getRawLightValue();
		_low = getRawLightValue();
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
	public void addListener(SensorListener Sl) {
		listenerList.add(Sl);
	}

	/**
	 * @return returns the name of the sensor
	 */
	@Override
	public String toString() {
		return "Color sensor";
	}

	@Override
	public void Calibrate() {
		int value = getRawLightValue();
		if (value < _low) {
			_low = value;
		}
		if (value > _high)
			_high = value;
	}

	public int getLightValue()

	{
		//LCD.drawInt(_low, 0, 2);
		//LCD.drawInt(_high, 0, 3);
		return 100 * (getRawLightValue() - _low) / (_high - _low);

	}

}
