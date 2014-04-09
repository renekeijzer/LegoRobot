import java.util.ArrayList;

import lejos.nxt.Button;

/**
 * @author René Keijzer <Hoofdauteur>
 * @author tom Verloop <Tom_Verloop@live.nl>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        SensorHandler Handles the updates of the sensor object in a seperate
 *        thread
 */
public class SensorHandler extends Thread {

	private static SensorHandler instance = null; // /< keeps a reference to
													// this singleton object
	private ArrayList<UpdatingSensor> sensorList; // /< keeps a list of sensors

	private SensorHandler() {
		sensorList = new ArrayList<UpdatingSensor>();
	}

	/**
	 * Return the SensorHandler object and instantiates it if its null
	 * 
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
	 * 
	 * @exception throws an exception if the thread sleep is interrupted
	 */
	@Override
	public void run() {
		while (!this.isInterrupted()) {
//			try {
//				this.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (sensorList.size() > 0) {
				for (UpdatingSensor us : sensorList) {
					us.updateState();
				}
			}
		}

	}

	/**
	 * adds a sensor to the list
	 * 
	 * @param updatingSensor
	 *            is the sensor to add to the list
	 */
	public void addSensor(UpdatingSensor updatingSensor) {
		sensorList.add(updatingSensor);
	}
	/**
	 * calibrates the light and the color sensor if they are added into the arraylist
	 * 
	 * 
	 */
	public void massCalibrate() {
		if (sensorList.size() > 0) {
			for (UpdatingSensor us : sensorList) {
				try{
				us.Calibrate();
				}
				catch(Exception Ex)
				{
					System.out.println(us.toString() + "Could not be Calibrated");

					Button.waitForAnyPress();
				}
			}
		}
	}
}
