import java.util.ArrayList;


public class SensorHandler extends Thread {
	
	private static SensorHandler instance = null;
	private ArrayList<UpdatingSensor> sensorList;
	
	private SensorHandler()
	{
		sensorList = new ArrayList<UpdatingSensor>();
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
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(UpdatingSensor us : sensorList)
			{
				us.updateState();
			}	
		}
	}
	
	public void addSensor(UpdatingSensor updatingSensor)
	{
		sensorList.add(updatingSensor);
		if(!this.isAlive()){
		this.start();
		}
	}
}
