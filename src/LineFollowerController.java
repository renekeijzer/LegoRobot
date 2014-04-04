import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

/**
 * @author tom Verloop <Tom_Verloop@live.nl>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Contains the class that follows the line
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
		if (updatingSensor.toString().equals("Color sensor"))
		{
			rightSensorValue = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor"))
		{
			leftSensorValue = newValue;
		}

	}

	@Override
	public synchronized void run()
	{
		while (true)
		{
			if (leftSensorValue > rightSensorValue
					&& Math.abs(leftSensorValue - rightSensorValue) > GlobalValues.ACTION_DIF)
			{
				if (motorC.getSpeed() < GlobalValues.MAX_SPEED)
				{
					motorC.setSpeed(motorC.getSpeed()
							+ GlobalValues.INCREASE_SPEED);
					motorA.setSpeed(motorA.getSpeed()
							- GlobalValues.DECREASE_SPEED);
				}
			} else if (leftSensorValue < rightSensorValue
					&& Math.abs(leftSensorValue - rightSensorValue) > GlobalValues.ACTION_DIF)
			{
				if (motorC.getSpeed() > GlobalValues.MIN_SPEED)
				{
					motorC.setSpeed(motorC.getSpeed()
							- GlobalValues.DECREASE_SPEED);
					motorA.setSpeed(motorA.getSpeed()
							+ GlobalValues.INCREASE_SPEED);
				}
			} else
			{
				motorC.setSpeed(GlobalValues.START_SPEED);
				motorA.setSpeed(GlobalValues.START_SPEED);
			}
			while (stopRun)
			{
				try
				{
					System.out.println("WAIT");
					wait();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void enable()
	{
		stopRun = false;
		notifyAll();
		System.out.println("GO!");
	}

	public void disable()
	{
		System.out.println("DISABLED!");
		stopRun = true;
	}
}
