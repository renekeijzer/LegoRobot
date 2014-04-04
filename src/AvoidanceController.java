import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class AvoidanceController extends Thread implements SensorListener
{

	private NXTRegulatedMotor motorA = Motor.A;
	private NXTRegulatedMotor motorC = Motor.C;
	private LineFollowerController lineFollower;
	private boolean arcDriving;
	private int ultraSonicSensorValue = 0;

	public AvoidanceController(LineFollowerController lineFollowerController)
	{
		lineFollower = lineFollowerController;
		this.start();
	}

	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue)
	{
		ultraSonicSensorValue = newValue;

	}

	public void DriveArc()
	{
		motorA.setSpeed(400);
		motorA.rotate((int) Math.round(180 * 2.73), true);
		motorC.setSpeed(0);

		while (motorA.isMoving())
		{
		}

		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorA.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_QUARTER_CIRCLE
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_QUARTER_CIRCLE
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);

		while (motorC.isMoving())
		{
		}

		motorC.setSpeed(GlobalValues.START_SPEED);
		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_HALF_CIRCLE
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		motorA.setSpeed(GlobalValues.STOP_SPEED);
		while (motorC.isMoving())
		{
		}
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorA.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_WEAK_CORNER
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_WEAK_CORNER
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		while (motorC.isMoving())
		{
		}
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_HALF_CIRCLE
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		motorA.setSpeed(GlobalValues.STOP_SPEED);

		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_HALF_CIRCLE
						* (Math.PI * GlobalValues.VEHICLE_WIDTH)
						/ (Math.PI * GlobalValues.WHEEL_DIAMETER)), true);
		motorA.setSpeed(GlobalValues.STOP_SPEED);
		while (motorC.isMoving())
		{
		}
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorA.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_QUARTER_CIRCLE * 2.73),
				true);
		motorC.rotate(
				(int) Math.round(GlobalValues.DEGREES_OF_QUARTER_CIRCLE * 2.73),
				true);

		while (motorC.isMoving())
		{
		}
		motorA.setSpeed(400);
		motorA.rotate((int) Math.round(180 * 2.73), true);
		motorC.setSpeed(0);
		while (motorA.isMoving())
		{
		}
		lineFollower.enable();

	}

	public void run()
	{
		if (ultraSonicSensorValue < 25 && !arcDriving)
		{
			lineFollower.disable();
			arcDriving = true;
			DriveArc();
		}

	}

}
