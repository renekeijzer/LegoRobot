import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class AvoidanceController extends Thread implements SensorListener
{

	private NXTRegulatedMotor motorA = Motor.A;
	private NXTRegulatedMotor motorC = Motor.C;
	private LineFollowerController lineFollower;
	private boolean arcDriving;
	private int ultraSonicSensorValue = 255;

	public AvoidanceController(LineFollowerController lineFollowerController)
	{
		lineFollower = lineFollowerController;
		this.start();
	}

	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue)
	{
		if (updatingSensor.toString().equals("Ultrasonic sensor"))
		{
			ultraSonicSensorValue = newValue;
		}

	}

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
	
	public void Drive(float centiMeters)
	{
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		float wheel = (float) (GlobalValues.WHEEL_DIAMETER * Math.PI);
		int i = (int) ((GlobalValues.DEGREES_OF_CIRCLE / wheel) * centiMeters);
		motorA.rotate(i, true);
		motorC.rotate(i);
		
	}

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
