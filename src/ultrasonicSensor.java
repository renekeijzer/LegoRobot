import java.util.ArrayList;

import lejos.nxt.I2CPort;
import lejos.nxt.UltrasonicSensor;

public class ultrasonicSensor extends UltrasonicSensor implements
		UpdatingSensor {

	private int Distance;
	private ArrayList<SensorListener> listenerList;

	public ultrasonicSensor(I2CPort port) {
		super(port);
		listenerList = new ArrayList<SensorListener>();
		SensorHandler handler = SensorHandler.getInstance();
		handler.addSensor(this);
		Distance = 0;
	}

	@Override
	public void updateState() {
		int newDistance = this.getDistance();
		if (Distance != newDistance) {
			if (this.listenerList.size() != 0) {
				for (SensorListener sl : listenerList) {
					sl.stateChanged(this, Distance, newDistance);
					Distance = newDistance;
				}
			}
		}
	}

	public void addListener(SensorListener SL) {
		this.listenerList.add(SL);
	}

	@Override
	public String toString() {
		return "Ultrasonic sensor";
	}

}
