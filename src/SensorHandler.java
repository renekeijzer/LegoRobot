import java.util.ArrayList;
/**
 * @author      René Keijzer <>
 * @author 		tom Verloop <Tom_Verloop@live.nl>
 * @version     1.0
 * @since       18-3-2014
 *
 * SensorHandler Handles the updates of the sensor object in a seperate thread
 */
public class SensorHandler extends Thread {

	private static SensorHandler instance = null; ///< keeps a reference to this singleton object
	private ArrayList<UpdatingSensor> sensorList; ///< keeps a list of sensors

	private SensorHandler() {
		sensorList = new ArrayList<UpdatingSensor>();
	}
	   /**
	    * Return the SensorHandler object and instantiates it if its null
	    * @return the SensorHandler object
	    */
	public static SensorHandler getInstance() {
		if (instance == null) {
			instance = new SensorHandler();
		}

		return instance;
	}
/**
 * the run method of the handler thread which updates all the sensors
 * @exception throws an exception if the thread sleep is interrupted
 */
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (UpdatingSensor us : sensorList) {
				us.updateState();

			}	
		}

			}
/**
 * adds a sensor to the list
 * 
 * @param updatingSensor is the sensor to add to the list
 */
	public void addSensor(UpdatingSensor updatingSensor) {
		sensorList.add(updatingSensor);
		if (!this.isAlive()) {
			this.start();
		}
	}
}
