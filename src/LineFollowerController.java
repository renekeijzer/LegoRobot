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

public class LineFollowerController extends Thread implements SensorListener {
	private State state = State.Straight;
	private NXTRegulatedMotor A;
	private NXTRegulatedMotor C;
	private int left = 0;
	private int right = 0;
	private boolean stop = false;
	private static final int START_SPEED = 400;
	private static final int MAX_SPEED = 600;
	private static final int ACTION_DIF = 10;

	public LineFollowerController() {
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
			int newValue) {
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

		if (updatingSensor.toString().equals("Color sensor")) {
			right = newValue;
		}
		if (updatingSensor.toString().equals("Light sensor")) {
			left = newValue;
		}
		LCD.clear();
		// LCD.drawInt(Math.abs(left - right), 0, 0);
		// LCD.drawString("Light Sensor:" + left, 0, 1);
		// LCD.drawString("Color Sensor:" + right, 0, 2);

		LCD.drawString("state: " + state, 0, 0);
		LCD.drawString("left: " + left, 0, 1);
		LCD.drawString("right: " + right, 0, 2);

	}

	@Override
	public void run() {
		while (!stop) {
			if (left > 70) {
				if(C.getSpeed() > MAX_SPEED){
				C.setSpeed(C.getSpeed() + 30);
				A.setSpeed(A.getSpeed() - 40);}
			} else if (right > 70) {

				if(A.getSpeed() > MAX_SPEED){
				C.setSpeed(C.getSpeed() - 40);
					A.setSpeed(A.getSpeed() + 30);
				}
			}
			else if (left > right && Math.abs(left - right) > 15) {
				if (C.getSpeed() < MAX_SPEED) {
					state = state.Left;
					C.setSpeed(C.getSpeed() + 30);
					A.setSpeed(A.getSpeed() - 40);
				}
			} else if (left < right && Math.abs(left - right) > 15) {
				if (C.getSpeed() > 200) {
					state = state.Right;
					C.setSpeed(C.getSpeed() - 40);
					A.setSpeed(A.getSpeed() + 30);
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
					C.setSpeed(400);
					A.setSpeed(400);
//				}
			}
		}
	}
}
