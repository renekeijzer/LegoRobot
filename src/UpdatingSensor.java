/**
 * @author      René Keijzer <>
 * @author 		tom Verloop <Tom_Verloop@live.nl>
 * @version     1.0
 * @since       18-3-2014
 *
 * the interface to implement for the Sensors which need to be updated by the sensor handler
 */

public interface UpdatingSensor {
	/**
	 * the abstract method to be implemented by the sensor object
	 */
	public void updateState();
}
