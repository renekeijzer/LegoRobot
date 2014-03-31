import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class testController extends Thread implements SensorListener
{

	private NXTRegulatedMotor A;
	private NXTRegulatedMotor C;
	private int left = 0;
	private int right = 0;
	private boolean stop = false;
	private static final int START_SPEED = 400;
	private static final int MAX_SPEED = 600;
	private static final int ACTION_DIF = 10;

	public testController()
	{
		A = Motor.A;
		C = Motor.C;
		A.setSpeed(START_SPEED);
		C.setSpeed(START_SPEED);
		A.forward();
		C.forward();
		left = 0;
		right = 0;
		this.start();
	}

	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue)
	{
		/*
		 * if (updatingSensor.getClass() == colorSensor.class) {
		 * LCD.drawString("Right:" + newValue, 0, 0); if (oldValue < newValue &&
		 * newValue > 50) { C.setSpeed(C.getSpeed() - 10);
		 * A.setSpeed(A.getSpeed() + 10); } else if(oldValue > newValue &&
		 * newValue < 50) {
		 * 
		 * C.setSpeed(C.getSpeed() + 10); A.setSpeed(A.getSpeed() - 10); } } if
		 * (updatingSensor.getClass() == lightSensor.class) {
		 * 
		 * LCD.drawString("left:" + newValue, 0, 1); if (oldValue < newValue &&
		 * newValue > 50) {
		 * 
		 * C.setSpeed(C.getSpeed() + 10); A.setSpeed(A.getSpeed() - 10); } else
		 * if (oldValue > newValue && newValue < 50) {
		 * 
		 * C.setSpeed(C.getSpeed() - 5); A.setSpeed(A.getSpeed() + 5); } }
		 */

		if (updatingSensor.toString().equals("Color sensor"))
		{
			right = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor"))
		{
			left = newValue;
		}
		LCD.clear();
		LCD.drawInt(Math.abs(left - right), 0, 0);
		LCD.drawString("Light Sensor:" + left, 0, 1);
		LCD.drawString("Color Sensor:" + right, 0, 2);

	}

	@Override
	public void run()
	{
		while (!stop)
		{
			if (left > right && Math.abs(left - right) > 10)
			{
				if (C.getSpeed() < MAX_SPEED)
				{
					C.setSpeed(C.getSpeed() + 30);
					A.setSpeed(A.getSpeed() - 40);
				}
			} else if (left < right && Math.abs(left - right) > 10)
			{
				if (C.getSpeed() > 200)
				{
					C.setSpeed(C.getSpeed() - 40);
					A.setSpeed(A.getSpeed() + 30);
				}
			} else
			{
				C.setSpeed(400);
				A.setSpeed(400);
			}
		}
	}
}
