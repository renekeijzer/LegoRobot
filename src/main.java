import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * @author Floris Rijker<><hoofd auteur>
 * @version 1.3
 * @since 18-3-2014
 * 
 *        Main class
 */
public class main
{
	private static int threeSixtyDegrees;
	public static void main(String[] args)
	{
		Button.waitForAnyPress();
		NXTRegulatedMotor motora = Motor.A;
		NXTRegulatedMotor motorc = Motor.C;
		threeSixtyDegrees = (int) Math.round(GlobalValues.DEGREES_OF_CIRCLE* (Math.PI * GlobalValues.VEHICLE_WIDTH)/ (Math.PI * GlobalValues.WHEEL_DIAMETER));
		motora.setSpeed(GlobalValues.CALIBRATE_SPEED);
		motorc.setSpeed(GlobalValues.CALIBRATE_SPEED);

		ultrasonicSensor ultrasonicSensor = new ultrasonicSensor(SensorPort.S1);
		lightSensor lightSensor = new lightSensor(SensorPort.S2);
		colorSensor colorSensor = new colorSensor(SensorPort.S3);

		try
		{
			motora.rotate(
					(int) threeSixtyDegrees, true); // /< let the weels go round 360 degrees
			motorc.rotate(
					(int) threeSixtyDegrees * -1,true); // /<let the wheels go round 360 degrees
		} catch (Exception Ex)
		{
			Ex.printStackTrace();
		}
		while (motorc.isMoving())
		{
			SensorHandler.getInstance().massCalibrate(); // /< while the motors are driving, callibrate the sensors
		}
		SensorHandler.getInstance().start();
		LineFollowerController lineFollowController = new LineFollowerController();
		AvoidanceController avoidanceController = new AvoidanceController(
				lineFollowController);

		ultrasonicSensor.addListener(avoidanceController); // /< Adding listeners to the sensors
		lightSensor.addListener(lineFollowController); // /< Adding listeners to the sensors
		colorSensor.addListener(lineFollowController); // /< Adding listeners to the sensors

		Button.ESCAPE.waitForPress();
	}

}
