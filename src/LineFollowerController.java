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
	private long sysTime = System.currentTimeMillis();
	private long currentTime = System.currentTimeMillis();
	
	public LineFollowerController()
	{
		currentTime -= sysTime;
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
	
	/**
	 * State handeling when the values changed 
	 * @param updating sensor the sensor that sended the updated state
	 * @param oldvalue the old value of this sensor
	 * @param newvalue the new value of this sensor
	 * 
	 */
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
	
	/**
	 * The run method of the linecontroller
	 */
	@Override
	public synchronized void run()
	{
		while (true)
		{

			currentTime = System.currentTimeMillis() - sysTime;
			if (leftSensorValue > rightSensorValue
					&& Math.abs(leftSensorValue - rightSensorValue) > GlobalValues.ACTION_DIF) // /< if the difference between sensors is bigger then the allowed difference steer 
			{
				if (motorC.getSpeed() < GlobalValues.MAX_SPEED)
				{
					if(currentTime > GlobalValues.ACCELERATIONTIME){
					motorC.setSpeed(motorC.getSpeed()
							+ GlobalValues.INCREASE_SPEED);
					motorA.setSpeed(motorA.getSpeed()
						- GlobalValues.DECREASE_SPEED);
					sysTime = System.currentTimeMillis();
					}	
				}
			} else if (leftSensorValue < rightSensorValue
					&& Math.abs(leftSensorValue - rightSensorValue) > GlobalValues.ACTION_DIF)
			{
				if (motorC.getSpeed() > GlobalValues.MIN_SPEED)
				{
					if(currentTime > GlobalValues.ACCELERATIONTIME){
					motorC.setSpeed(motorC.getSpeed()
							- GlobalValues.DECREASE_SPEED);
					motorA.setSpeed(motorA.getSpeed()
							+ GlobalValues.INCREASE_SPEED);
					sysTime = System.currentTimeMillis();
					}	
					}
					
			} else
			{
				motorC.setSpeed(GlobalValues.START_SPEED);
				motorA.setSpeed(GlobalValues.START_SPEED);
			}
			while (stopRun) // /< if avoidance has enabled stoprun wait
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
	/**
	 * sets boolean to false
	 * notifies all threads
	 */
	public synchronized void enable()
	{
		stopRun = false;
		notifyAll();
		System.out.println("GO!");
	}
	/**
	 * sets boolean to true
	 */
	public void disable()
	{
		System.out.println("DISABLED!");
		stopRun = true;
	}
}
