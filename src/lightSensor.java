import java.util.ArrayList;

import lejos.nxt.ADSensorPort;
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
 */

public class lightSensor extends LightSensor implements UpdatingSensor {

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
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Lightvalue = 0;
	}

	/**
	 * Handles the update to the listeners
	 */
	public void updateState() {
		int newLightValue = this.getValue();
		if (Lightvalue != newLightValue) {
			if (this.listenerList.size() > 0) {
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

	/**
	 * Calibrates the normalized value of the lightsensor
	 */
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
			return 100 * (readNormalizedValue() - _low) / (_high - _low);
	}
}
