<<<<<<< HEAD
public class main {

	public static void main(String[] args) {
		new testController();

=======
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
/**
 * @author Floris Rijker<><hoofd auteur>
 * @version 1.0
 * @since 18-3-2014
 * 
 *        Main class
 */
public class main {
	
	public static void main(String[] args) {
		NXTRegulatedMotor motora = Motor.A;

		NXTRegulatedMotor motorc = Motor.C;

		motora.setSpeed(200);
		motorc.setSpeed(200);

		ultrasonicSensor ultrasonicSensor = new ultrasonicSensor(SensorPort.S1);
		lightSensor lightSensor = new lightSensor(SensorPort.S2);
		colorSensor colorSensor = new colorSensor(SensorPort.S3);

		try{
		motora.rotate((int)Math.round(360 * 2.73),true);
		motorc.rotate((int)Math.round((360 * 2.73)*-1),true);
		}catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		while(motorc.isMoving())
		{
			SensorHandler.getInstance().massCalibrate();
		}
		SensorHandler.getInstance().start();
		//LineFollowerController lineFollowController = new LineFollowerController();
		AvoidanceController avoidanceController = new AvoidanceController();
		
		ultrasonicSensor.addListener(avoidanceController);
		//lightSensor.addListener(lineFollowController);
		//colorSensor.addListener(lineFollowController);
		
		Button.ESCAPE.waitForPress();
>>>>>>> ee0eae0077b07a2ac714600a728265fa499f681e
	}

}
