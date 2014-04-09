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
	private int lightSensorValue = 0;
	private int colorSensorValue = 0;
	private boolean stopRun = false;
	private long sysTime = System.currentTimeMillis();
	private long currentTime = System.currentTimeMillis();
	private state line = state.straight;
	private state oldline = state.straight;
	private int statetimer = 0;

	public LineFollowerController()
	{
		currentTime -= sysTime;
		motorA = Motor.A;
		motorC = Motor.C;
		motorA.setSpeed(GlobalValues.START_SPEED);
		motorC.setSpeed(GlobalValues.START_SPEED);
		motorA.forward();
		motorC.forward();
		lightSensorValue = 0;
		colorSensorValue = 0;

		this.start();
	}

	/**
	 * State handeling when the values changed
	 * 
	 * @param updating
	 *            sensor the sensor that sended the updated state
	 * @param oldvalue
	 *            the old value of this sensor
	 * @param newvalue
	 *            the new value of this sensor
	 * 
	 */
	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue)
	{
		if (updatingSensor.toString().equals("Color sensor"))
		{
			colorSensorValue = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor"))
		{
			lightSensorValue = newValue;
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
			LCD.clear();
			LCD.drawInt(lightSensorValue, 0, 0);
			LCD.drawInt(colorSensorValue, 0, 1);
			LCD.drawString(line.toString(), 0, 2);

			currentTime = System.currentTimeMillis() - sysTime;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (line != state.straight && lightSensorValue > GlobalValues.WHITE
					&& colorSensorValue < GlobalValues.BLACK)
			{
				line = state.straight;
			}
			else if (line == state.straight && lightSensorValue < GlobalValues.BLACK
					&& colorSensorValue > GlobalValues.WHITE)
			{
				line = state.left;
			}
			else if (line == state.left && lightSensorValue > GlobalValues.WHITE
					&& colorSensorValue > GlobalValues.WHITE)
			{
				line = state.left;
			}
			else if (line == state.straight && lightSensorValue > GlobalValues.WHITE
					&& colorSensorValue > GlobalValues.WHITE)
			{
				line = state.right;
			}

			if (oldline == line)
			{
				statetimer++;
			} else
			{
				LCD.clear();
				LCD.drawInt(lightSensorValue, 0, 4);
				LCD.drawInt(colorSensorValue, 0, 5);
				LCD.drawString(line.toString(), 0, 6);
				statetimer = 0;
				oldline = line;
			}



			if (line == state.right) 
			{
				if (motorC.getSpeed() < GlobalValues.MAX_SPEED
						&& motorA.getSpeed() > GlobalValues.MIN_SPEED)
				{
					if (currentTime > GlobalValues.ACCELERATIONTIME)
					{
						motorC.setSpeed(motorC.getSpeed()
								+ GlobalValues.INCREASE_SPEED);
						motorA.setSpeed(motorA.getSpeed()
								- GlobalValues.DECREASE_SPEED);
						sysTime = System.currentTimeMillis();
					}
				}
			} else if (line == state.left)
			{
				if (motorC.getSpeed() > GlobalValues.MIN_SPEED
						&& motorA.getSpeed() < GlobalValues.MAX_SPEED)
				{
					if (currentTime > GlobalValues.ACCELERATIONTIME)
					{
						motorC.setSpeed(motorC.getSpeed()
								- GlobalValues.DECREASE_SPEED);
						motorA.setSpeed(motorA.getSpeed()
								+ GlobalValues.INCREASE_SPEED);
						sysTime = System.currentTimeMillis();
					}
				}

			} else if (line == state.straight)
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
	 * sets boolean to false notifies all threads
	 */
	public synchronized void enable()
	{
		stopRun = false;
		notifyAll();
		// System.out.println("GO!");
	}

	/**
	 * sets boolean to true
	 */
	public void disable()
	{
		// System.out.println("DISABLED!");
		stopRun = true;
	}
}
