import lejos.nxt.SensorPort;


public class testController implements SensorListener{
	public testController()
	{
		ultrasonicSensor us = new ultrasonicSensor(SensorPort.S1);
		lightSensor ls = new lightSensor(SensorPort.S2);
		ls.addListener(this);
		us.addListener(this);
		for(;;){}
	}

	@Override
	public void stateChanged(UpdatingSensor updatingSensor, int oldValue,
			int newValue) {
		System.out.println("Sensor:"+updatingSensor+" Oldvalue = " + oldValue + " New Value = " + newValue);
		
	}
}
