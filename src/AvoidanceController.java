import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class AvoidanceController implements SensorListener, Runnable{

	NXTRegulatedMotor motora = Motor.A;
	NXTRegulatedMotor motorc = Motor.C;
	private float rightRotation, leftRotation, speedCoefficient;
	private boolean arcDriving; 
	private Thread avoidThread;
	private int fortyfiveDegree = 90;
	private int ninetyDegree = 180;
	public AvoidanceController()
	{
		avoidThread = new Thread(this);
		
		motora.setSpeed(400);
		motorc.setSpeed(400);
		
		motora.forward();
		motorc.forward();
	}
	
	@Override
	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue) {
		if(newValue < 33 && !arcDriving)
		{
			LCD.drawInt(newValue,0,0);
			arcDriving = true;
			//avoidThread.start();
			DriveArc(); 
		}	
		
	}
	
	public void DriveArc()
	{
	
		motora.setSpeed(400);
		motorc.setSpeed(400);
		motora.rotate((int) Math.round(125 * 2.73), true);
		motorc.rotate((int) Math.round(125* 2.73), true);
		while(motorc.isMoving())
		{
		}
		motora.setSpeed(400);
		motora.rotate((int)Math.round(fortyfiveDegree * 2.73),true);
		motorc.setSpeed(0);
		
		while(motora.isMoving()){}
		
		motora.setSpeed(400);
		motorc.setSpeed(400);
		motora.rotate((int) Math.round(250 * 2.73), true);
		motorc.rotate((int) Math.round(250* 2.73), true);
		
		while(motorc.isMoving()){}
		
		motorc.setSpeed(400);
		motorc.rotate((int) Math.round(ninetyDegree * 2.73), true);
		motora.setSpeed(0);
		while(motorc.isMoving())
		{
		}
		motora.setSpeed(400);
		motora.rotate((int) Math.round(250 * 2.73), true);
		motorc.rotate((int) Math.round(250 * 2.73), true);
		while(motora.isMoving()){}
		motora.setSpeed(400);
		motora.rotate((int) Math.round(fortyfiveDegree * 2.73), true);
		motorc.setSpeed(0);
		
		
		
	}

	@Override
	public void run() {
		
		
		
	}

}
