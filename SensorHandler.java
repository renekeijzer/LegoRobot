
public class SensorHandler extends Thread {
	
	private static SensorHandler instance = null;
	private Sensor sensor;
	
	private SensorHandler()
	{
		
	}
	
	public static SensorHandler getInstance()
	{
		if(instance == null)
		{
			instance = new SensorHandler();
		}
		
		return instance;
	}
	
	@Override
	public void run()
	{
		while(!this.isInterrupted())
		{
			sensor.updateState();
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void addSensor(Sensor sensor)
	{
		this.sensor = sensor;
		this.start();
	}
}
