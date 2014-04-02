import java.util.ArrayList;

import lejos.nxt.I2CPort;
import lejos.nxt.UltrasonicSensor;

/**
 * @author René Keijzer <>
<<<<<<< HEAD
 * @author tom Verloop <Tom_Verloop@live.nl>
=======
 * @author tom Verloop <Tom_Verloop@live.nl><hoofdauteur tom>
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Container class of the Ultrasonic Sensor
 */
public class ultrasonicSensor extends UltrasonicSensor implements
		UpdatingSensor {

	private int Distance; // /< the distance of the sensor to an object
	private ArrayList<SensorListener> listenerList; // /< a list of sensor
													// listeners

	public ultrasonicSensor(I2CPort port) {
		super(port);
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Distance = 0;
	}

	/**
	 * Handles the update to the listeners
	 */
<<<<<<< HEAD
	@Override
=======
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	public void updateState() {
		int newDistance = this.getDistance();
		if (Distance != newDistance) {
			if (this.listenerList.size() != 0) {
				for (SensorListener sl : listenerList) {
					sl.stateChanged(this, Distance, newDistance);
					Distance = newDistance;
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
	 * adds a sensor to the list
	 * 
	 * @param updatingSensor
	 *            is the sensor to add to the list
	 */
	@Override
	public String toString() {
		return "Ultrasonic sensor";
	}

<<<<<<< HEAD
=======
	public void Calibrate() {
		// TODO Auto-generated method stub

	}

>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
}
