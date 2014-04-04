import java.util.ArrayList;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

/**
 * @author Ricardo methai<><hoofd auteur>
 * @author René Keijzer <>
 * @author tom Verloop <Tom_Verloop@live.nl>
 * @version 1.2
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
		this.setFloodlight(true);
		_high = getRawLightValue();
		_low = getRawLightValue();
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Lightvalue = 0;
	}

	/**
	 * Handles the update to the listeners
	 * for each listener in the list statechanged is called
	 */
	public void updateState() {
		int newLightValue = this.getValue();
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

	/**
	 * Calibrating the color sensor
	 */
	public void Calibrate() {
		int value = getNormalizedLightValue();
		if (value < _low) {
			_low = value;
		}
		if (value > _high)
			_high = value;
	}

	/**
	 * returns the normalized value between 0-100
	 * @return returns the calibrated lightvalue between 0-100
	 */
	public int getValue()
	{
		return 100 * (getNormalizedLightValue() - _low) / (_high - _low);
	}

}
