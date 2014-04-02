import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * @author Floris Rijker<><hoofd auteur>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Main class
 */
public class main {


	public static void main(String[] args) {
		NXTRegulatedMotor motora = Motor.A;
		NXTRegulatedMotor motorc = Motor.C;

		motora.setSpeed(GlobalValues.CALIBRATE_SPEED);
		motorc.setSpeed(GlobalValues.CALIBRATE_SPEED);

		ultrasonicSensor ultrasonicSensor = new ultrasonicSensor(SensorPort.S1);
		lightSensor lightSensor = new lightSensor(SensorPort.S2);
		colorSensor colorSensor = new colorSensor(SensorPort.S3);

		try {
			motora.rotate(
					(int) Math.round(GlobalValues.DEGREES_OF_CIRCLE
							* (Math.PI * GlobalValues.VEHICLE_WIDTH)
							/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
			motorc.rotate(
					(int) Math.round(GlobalValues.DEGREES_OF_CIRCLE
							* (Math.PI * GlobalValues.VEHICLE_WIDTH)
							/ (Math.PI * GlobalValues.WHEEL_DIAMETER) * -1),
					true);
		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
		while (motorc.isMoving()) {
			SensorHandler.getInstance().massCalibrate();
		}
		SensorHandler.getInstance().start();
		// LineFollowerController lineFollowController = new
		// LineFollowerController();
		AvoidanceController avoidanceController = new AvoidanceController();

		ultrasonicSensor.addListener(avoidanceController);
		// lightSensor.addListener(lineFollowController);
		// colorSensor.addListener(lineFollowController);

		Button.ESCAPE.waitForPress();
	}

}
