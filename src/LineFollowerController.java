import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * @author tom Verloop <Tom_Verloop@live.nl>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Contains the class that folows the line
 */

public class LineFollowerController extends Thread implements SensorListener
{

	private NXTRegulatedMotor motorA;
	private NXTRegulatedMotor motorC;
	private int leftSensorValue = 0;
	private int rightSensorValue = 0;
	private boolean stopRun = false;

	public LineFollowerController()
	{
		motorA = Motor.A;
		motorC = Motor.C;
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorA.forward();
		motorC.forward();
		leftSensorValue = 0;
		rightSensorValue = 0;
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
			rightSensorValue = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor"))
		{
			leftSensorValue = newValue;
		}
		LCD.clear();
		LCD.drawInt(Math.abs(leftSensorValue - rightSensorValue), 0, 0);
		LCD.drawString("Light Sensor:" + leftSensorValue, 0, 1);
		LCD.drawString("Color Sensor:" + rightSensorValue, 0, 2);

	}

	@Override
	public synchronized void run()
	{
		while (true)
		{
			if (leftSensorValue > rightSensorValue && Math.abs(leftSensorValue - rightSensorValue) > 15)
			{
				if (motorC.getSpeed() < GlobalValues.MAX_SPEED)
				{
					motorC.setSpeed(motorC.getSpeed() + 30);
					motorA.setSpeed(motorA.getSpeed() - 40);
				}
			} else if (leftSensorValue < rightSensorValue && Math.abs(leftSensorValue - rightSensorValue) > 15)
			{
				if (motorC.getSpeed() > 200)
				{
					motorC.setSpeed(motorC.getSpeed() - 40);
					motorA.setSpeed(motorA.getSpeed() + 30);
				}
			} else
			{
				motorC.setSpeed(400);
				motorA.setSpeed(400);
			}
			while(!stopRun)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void enable()
	{
		stopRun = true;
		notifyAll();
	}
	
	public void disable()
	{
		stopRun = false;
	}
}
