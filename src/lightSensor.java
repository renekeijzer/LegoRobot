import java.util.ArrayList;

import lejos.nxt.ADSensorPort;
<<<<<<< HEAD
import lejos.nxt.LightSensor;

/**
 * @author      René Keijzer <>
 * @author 		tom Verloop <Tom_Verloop@live.nl>
 * @version     1.0
 * @since       18-3-2014
 *
 * Container class of the Light Sensor
=======
import lejos.nxt.Button;
import lejos.nxt.LightSensor;

/**
 * 
 * @author René Keijzer <>
 * @author tom Verloop <Tom_Verloop@live.nl><hoofdauteur>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Container class of the Light Sensor
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
 */

public class lightSensor extends LightSensor implements UpdatingSensor {

<<<<<<< HEAD
	private int Lightvalue;
	private ArrayList<SensorListener> listenerList;

	public lightSensor(ADSensorPort port) {
		super(port);
=======
	private int _high = 0;
	private int _low = 1023;
	private int Lightvalue; // /< keeps the value of the colorsensor
	private ArrayList<SensorListener> listenerList; // /< keeps a list of
													// Sensorlisteners

	/**
	 * Initiating the lightsensor object
	 * @param port of the light sensor
	 */
	public lightSensor(ADSensorPort port) {
		super(port);
		this.setFloodlight(true);
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Lightvalue = 0;
	}

	/**
	 * Handles the update to the listeners
	 */
<<<<<<< HEAD
	@Override
	public void updateState() {
		int newLightValue = this.getLightValue();
		if (Lightvalue != newLightValue) {
			if (this.listenerList.size() != 0) {
=======
	public void updateState() {
		int newLightValue = this.getValue();
		if (Lightvalue != newLightValue) {
			if (this.listenerList.size() > 0) {
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
				for (SensorListener sl : listenerList) {
					sl.stateChanged(this, Lightvalue, newLightValue);
					Lightvalue = newLightValue;
				}
			}
		}
	}
<<<<<<< HEAD
=======

>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	/**
	 * Adds a Sensorlistener to the ListenerList
	 * 
	 * @param Sl
	 *            The listener to add
	 */
	public void addListener(SensorListener SL) {
		this.listenerList.add(SL);
	}
<<<<<<< HEAD
=======

>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	/**
	 * @return returns the name of the sensor
	 */
	@Override
	public String toString() {
		return "Light sensor";
	}

<<<<<<< HEAD
=======
	public void Calibrate() {
		int value = this.readNormalizedValue();
		if (value < _low) {
			_low = value;
		}
		if (value > _high)
			_high = value;
	}

	/**
	 *  Gets the non-calibrated-value of the lightsensor
	 * @return returns the non-calibrated light value
	 */
	public int getValue()
	{
		// LCD.drawInt(_low, 0, 2);

		// LCD.drawInt(_high, 0, 3);
			return 100 * (readNormalizedValue() - _low) / (_high - _low);
		

	}
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
}
