import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class main {
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		NXTRegulatedMotor motora = Motor.A;
		NXTRegulatedMotor motorc = Motor.C;
		motora.setSpeed(900);
		motorc.setSpeed(900);
		lightSensor ls = new lightSensor(SensorPort.S2);
		colorSensor cs = new colorSensor(SensorPort.S3);
		motora.rotate((int) (360 * 2.73),true);
		motorc.rotate((int) ((360 * 2.73)*-1),true);
		while(motorc.isMoving())
		{
			SensorHandler.getInstance().massCalibrate();
		}
		testController test = new testController();
		ls.addListener(test);
		cs.addListener(test);
		
		Button.ESCAPE.waitForPress();
	}

}
