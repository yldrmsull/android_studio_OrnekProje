package net.merhabaandroid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;



public class MA_Sensor extends Activity {
	private SensorManager sensorYoneticisi;
	//private SensorManagerSimulator sensorYoneticisi;
	private Sensor oryantasyonSensoru;
	private SensorEventListener sensorDinleyici = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			
				azimut.setText(String.valueOf(event.values[0]));
				egim.setText(String.valueOf(event.values[1]));
				roll.setText(String.valueOf(event.values[2]));
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	
	private TextView azimut;
	private TextView egim;
	private TextView roll;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		azimut = (TextView) findViewById(R.id.azimut);
		egim = (TextView) findViewById(R.id.egim);
		roll = (TextView) findViewById(R.id.roll);

		sensorYoneticisi = (SensorManager)getSystemService(SENSOR_SERVICE);
		
//		List<Sensor> sensors = sensorYoneticisi.getSensorList(Sensor.TYPE_ALL);
//		for (int i = 0; i < sensors.size(); i++) {
//			Log.i("Sensor " + i, sensors.get(i).toString());
//		}

		oryantasyonSensoru = sensorYoneticisi.getDefaultSensor(Sensor.TYPE_ORIENTATION);

	}

	@Override
	protected void onStart() {
		super.onStart();
		sensorYoneticisi.registerListener(sensorDinleyici, oryantasyonSensoru, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorYoneticisi.unregisterListener(sensorDinleyici);
	}
   
}