import java.util.ArrayList;
<<<<<<< HEAD
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
=======

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
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e

	private SensorHandler() {
		sensorList = new ArrayList<UpdatingSensor>();
	}
<<<<<<< HEAD
	   /**
	    * Return the SensorHandler object and instantiates it if its null
	    * @return the SensorHandler object
	    */
=======

	/**
	 * Return the SensorHandler object and instantiates it if its null
	 * 
	 * @return the SensorHandler object
	 */
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	public static SensorHandler getInstance() {
		if (instance == null) {
			instance = new SensorHandler();
		}

		return instance;
	}
<<<<<<< HEAD
/**
 * the run method of the handler thread which updates all the sensors
 * @exception throws an exception if the thread sleep is interrupted
 */
=======

	/**
	 * the run method of the handler thread which updates all the sensors
	 * 
	 * @exception throws an exception if the thread sleep is interrupted
	 */
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
<<<<<<< HEAD
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
=======
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
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
		}
	}
}
