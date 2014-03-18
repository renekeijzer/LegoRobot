import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class testController implements SensorListener {

	private NXTRegulatedMotor A;
	private NXTRegulatedMotor C;
	private int left;
	private int right;

	public testController() {
		A = Motor.A;
		C = Motor.C;
		A.setSpeed(400);
		C.setSpeed(400);
		A.forward();
		C.forward();
		left = 0;
		right = 0;
	}

	@Override
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

		if (updatingSensor.getClass() == colorSensor.class) {
			right = newValue;
		}
		if (updatingSensor.getClass() == lightSensor.class) {
			left = newValue;
		}
		LCD.drawInt(Math.abs(left - right), 0, 0);
		if (left > right && Math.abs(left - right) > 40) {
			if (C.getSpeed() < 600) {
				C.setSpeed(C.getSpeed() + 30);
				A.setSpeed(A.getSpeed() - 30);
			}
		} else if (left < right && Math.abs(left - right) > 40) {
			if (C.getSpeed() > 200) {
				C.setSpeed(C.getSpeed() - 30);
				A.setSpeed(A.getSpeed() + 30);
			}
		} else {
			C.setSpeed(400);
			A.setSpeed(400);
		}
	}
}
