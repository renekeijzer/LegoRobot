import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author tom<><>
 * @author rene<><Hoofdauteur>
 * @author ricardo<>
 * @author floris
 * 
 * @version 1.6
 * @since 2-4-2014
 * 
 *This controller contains the logic for collision avoidance
 * 
 */
public class AvoidanceController extends Thread implements SensorListener
{

	private NXTRegulatedMotor motorA = Motor.A;
	private NXTRegulatedMotor motorC = Motor.C;
	private LineFollowerController lineFollower;
	private boolean arcDriving = false;
	private int ultraSonicSensorValue = 255;

	/**
	 * the constructor initiating the 
	 * thread and giving the avoidance controller the linefollowercontroller refference
	 * @param lineFollowerController
	 */
	public AvoidanceController(LineFollowerController lineFollowerController)
	{
		lineFollower = lineFollowerController;
		this.start();
	}

	/**
	 * State handeling when the values changed 
	 * @param updating sensor the sensor that sended the updated state
	 * @param oldvalue the old value of this sensor
	 * @param newvalue the new value of this sensor
	 */
	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue)
	{
		if (updatingSensor.toString().equals("Ultrasonic sensor"))
		{
			ultraSonicSensorValue = newValue;
		}

	}
	
	/**
	 * Function that makes the robot ride a arc
	 * @param degrees of the arc
	 * @param left boolean, true = left, false= right
	 */
	public void DriveArc(int degrees, boolean left)
	{
		if (left)
		{
			motorA.setSpeed(GlobalValues.START_SPEED);
			motorA.rotate(
					(int) Math.round((degrees * 2)
							* (Math.PI * GlobalValues.VEHICLE_WIDTH)
							/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
			motorC.setSpeed(GlobalValues.STOP_SPEED);
			while (motorA.isMoving())
			{
			}
		}
		if (!left)
		{
			motorC.setSpeed(GlobalValues.START_SPEED);
			motorC.rotate(
					(int) Math.round((degrees * 2)
							* (Math.PI * GlobalValues.VEHICLE_WIDTH)
							/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
			motorA.setSpeed(GlobalValues.STOP_SPEED);
			while (motorC.isMoving())
			{
			}
		}
	}
	
	
	/**
	 * Drive funtion in centimeters
	 * @param centimeters float centimeters
	 */
	public void Drive(float centimeters)
	{
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		float wheel = (float) (GlobalValues.WHEEL_DIAMETER * Math.PI);
		int i = (int) ((GlobalValues.DEGREES_OF_CIRCLE / wheel) * centimeters);
		motorA.rotate(i, true);
		motorC.rotate(i);
		
	}
	
	/**
	 * This function is called when the ultrasonic sensor sees an object
	 * This function makes sure that the robot drives arround the object
	 */
	public void DriveAround()
	{
		DriveArc(GlobalValues.DEGREES_OF_ONE_EIGHT_CIRCLE, true);
		Drive(GlobalValues.EVASION);
		DriveArc(GlobalValues.DEGREES_OF_QUARTER_CIRCLE, false);
		Drive(GlobalValues.EVASION);
		DriveArc(GlobalValues.DEGREES_OF_ONE_EIGHT_CIRCLE, true);
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorA.forward();
		motorC.forward();
		arcDriving = false;
		lineFollower.enable();

	}

	/**
	 * This is the run function of the avoidance controller
	 * when the ultrasonicsensor value is below 30 then the linefollower controller is disabled 
	 */
	public void run()
	{
		while (true)
		{
			if (ultraSonicSensorValue < 30 && !arcDriving)
			{
				lineFollower.disable();
				arcDriving = true;
				DriveAround();
			}
		}
	}

}
