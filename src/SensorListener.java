/**
<<<<<<< HEAD
 * @author      René Keijzer <>
=======
 * @author      René Keijzer <><hoofdAuteur>
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
 * @author 		tom Verloop <Tom_Verloop@live.nl>
 * @version     1.0
 * @since       18-3-2014
 *
 * the interface to implement for the controllers which need to listen to the sensors
 */

public interface SensorListener {
	/**
	 * the abstract method which hands the sensor update to the sensor listeners
	 * 
	 * @param updatingSensor the sensor object that sends this update
	 * @param oldValue the previous value send
	 * @param newValue the new value send
	 */
	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue);
}
