import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class AvoidanceController extends Thread implements SensorListener {

	NXTRegulatedMotor motora = Motor.A;
	NXTRegulatedMotor motorc = Motor.C;
	private boolean arcDriving;

	public AvoidanceController() {

		motora.setSpeed(400);
		motorc.setSpeed(400);

		motora.forward();
		motorc.forward();
	}

	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue) {
		if (newValue < 25 && !arcDriving) {

			arcDriving = true;
			// avoidThread.start();
			DriveArc();
		}

	}

	public void DriveArc() {
		motora.setSpeed(400);
		motora.rotate((int) Math.round(180 * 2.73), true);
		motorc.setSpeed(0);

		while (motora.isMoving()) {
		}

		motora.setSpeed(400);
		motorc.setSpeed(400);
		motora.rotate((int) Math.round(90 * 2.73), true);
		motorc.rotate((int) Math.round(90 * 2.73), true);

		while (motorc.isMoving()) {
		}

		motorc.setSpeed(400);
		motorc.rotate((int) Math.round(180 * 2.73), true);
		motora.setSpeed(0);
		while (motorc.isMoving()) {
		}
		motora.setSpeed(400);
		motora.rotate((int) Math.round(220 * 2.73), true);
		motorc.rotate((int) Math.round(220 * 2.73), true);
		while (motorc.isMoving()) {
		}
		motorc.setSpeed(400);
		motorc.rotate((int) Math.round(180 * 2.73), true);
		motora.setSpeed(0);

		motorc.rotate((int) Math.round(180 * 2.73), true);
		motora.setSpeed(0);
		while (motorc.isMoving()) {
		}
		motora.setSpeed(400);
		motorc.setSpeed(400);
		motora.rotate((int) Math.round(90 * 2.73), true);
		motorc.rotate((int) Math.round(90 * 2.73), true);

		while (motorc.isMoving()) {
		}
		motora.setSpeed(400);
		motora.rotate((int) Math.round(180 * 2.73), true);
		motorc.setSpeed(0);

	}

	public void run() {

	}

}
