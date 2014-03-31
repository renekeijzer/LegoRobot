import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class main {
	
	public static void main(String[] args) {
		NXTRegulatedMotor motora = Motor.A;

		NXTRegulatedMotor motorc = Motor.C;

		motora.setSpeed(200);
		motorc.setSpeed(200);

		lightSensor ls = new lightSensor(SensorPort.S2);
		colorSensor cs = new colorSensor(SensorPort.S3);

		try{
		motora.rotate((int)Math.round(360 * 2.73),true);
		motorc.rotate((int)Math.round((360 * 2.73)*-1),true);
		}catch(Exception Ex)
		{
			System.out.print("wut");
		}
		while(motorc.isMoving())
		{
			SensorHandler.getInstance().massCalibrate();
		}
		SensorHandler.getInstance().start();
		testController test = new testController();

		ls.addListener(test);
		cs.addListener(test);
		
		Button.ESCAPE.waitForPress();
	}

}
