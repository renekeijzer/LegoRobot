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
	private State state = State.Straight;

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
			int newValue) {
		if (updatingSensor.toString().equals("Color sensor")) {
			rightSensorValue = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor")) {
			leftSensorValue = newValue;
		}
		LCD.clear();
		// LCD.drawInt(Math.abs(left - right), 0, 0);
		// LCD.drawString("Light Sensor:" + left, 0, 1);
		// LCD.drawString("Color Sensor:" + right, 0, 2);

		LCD.drawString("state: " + state, 0, 0);
		LCD.drawString("left: " + leftSensorValue, 0, 1);
		LCD.drawString("right: " + rightSensorValue, 0, 2);


	}

	@Override
	public void run() {
		while (!stopRun) {
			if (leftSensorValue > 70) {
				if(motorC.getSpeed() > GlobalValues.MAX_SPEED){
				motorC.setSpeed(motorC.getSpeed() + 30);
				motorA.setSpeed(motorA.getSpeed() - 40);}
			} else if (rightSensorValue > 70) {

				if(motorA.getSpeed() > GlobalValues.MAX_SPEED){
				motorC.setSpeed(motorC.getSpeed() - 40);
					motorA.setSpeed(motorA.getSpeed() + 30);
				}
			}
			else if (leftSensorValue > rightSensorValue && Math.abs(leftSensorValue - rightSensorValue) > 15) {
				if (motorC.getSpeed() < GlobalValues.MAX_SPEED) {
					state = state.Left;
					motorC.setSpeed(motorC.getSpeed() + 30);
					motorA.setSpeed(motorA.getSpeed() - 40);
				}
			} else if (leftSensorValue < rightSensorValue && Math.abs(leftSensorValue - rightSensorValue) > 15) {
				if (motorC.getSpeed() > 200) {
					state = state.Right;
					motorC.setSpeed(motorC.getSpeed() - 40);
					motorA.setSpeed(motorA.getSpeed() + 30);
				}
			} else {
//				if (left > 70 && right > 70 && state != state.Straight) {
//					switch (state) {
//					case Right:
//						if (A.getSpeed() < MAX_SPEED) {
//							C.setSpeed(C.getSpeed() - 40);
//							A.setSpeed(A.getSpeed() + 30);
//						}
//						break;
//					case Left:
//						if (C.getSpeed() < MAX_SPEED) {
//							C.setSpeed(C.getSpeed() + 30);
//							A.setSpeed(A.getSpeed() - 40);
//						}
//						break;
//					default:
//						break;
//					}
//				} else {
//					state = state.Straight;
					motorC.setSpeed(400);
					motorA.setSpeed(400);
//				}
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
