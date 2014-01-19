package gibbs.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Sensors extends ActionBarActivity implements SensorEventListener {

    private SensorManager mSensorMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        listenToLocation();
        mSensorMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listenTo(Sensor.TYPE_ACCELEROMETER, R.id.acceleration);
        listenTo(Sensor.TYPE_LIGHT, R.id.light);
        listenTo(Sensor.TYPE_PRESSURE, R.id.pressure);
        listenTo(Sensor.TYPE_GRAVITY, R.id.gravity);
        listenTo(Sensor.TYPE_LINEAR_ACCELERATION, R.id.linaccel);
        listenTo(Sensor.TYPE_MAGNETIC_FIELD, R.id.magfield);
        listenTo(Sensor.TYPE_AMBIENT_TEMPERATURE, R.id.temp);
        listenTo(Sensor.TYPE_RELATIVE_HUMIDITY, R.id.relhum);
        listenTo(Sensor.TYPE_PROXIMITY, R.id.proximity);
    }

    private void listenTo(int sensorType, int id) {
        Sensor sensor = mSensorMan.getDefaultSensor(sensorType);
        if (sensor != null) {
            mSensorMan.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            ((TextView) findViewById(id)).setText("Not Available");
        }
    }


    private void listenToLocation() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener ll = new LocationListener() {
            TextView elevation = (TextView) findViewById(R.id.elevation);
            TextView latlng = (TextView) findViewById(R.id.latlng);
            TextView gps = (TextView) findViewById(R.id.gps);

            @Override
            public void onLocationChanged(Location location) {
                elevation.setText(String.format("%.2f m",
                        location.getAltitude()));
                latlng.setText(String.format("%.4f/%.4f  (+/- %.2f m)",
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getAccuracy()));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
                gps.setText("GPS Enabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                gps.setText("GPS Disabled");
            }
        };
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        TextView gps = (TextView) findViewById(R.id.gps);
        gps.setText("Initialised");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                TextView light = (TextView) findViewById(R.id.light);
                light.setText(String.format("%.2f SI lux", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_PRESSURE:
                TextView pressure = (TextView) findViewById(R.id.pressure);
                pressure.setText(String.format("%.0f SI hPa", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                TextView temp = (TextView) findViewById(R.id.temp);
                temp.setText(String.format("%.2f C", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_PROXIMITY:
                TextView proximity = (TextView) findViewById(R.id.proximity);
                proximity.setText(String.format("%.1f cm", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                TextView humidity = (TextView) findViewById(R.id.relhum);
                humidity.setText(String.format("%.2f %", sensorEvent.values[0]));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                TextView magfield = (TextView) findViewById(R.id.magfield);
                magfield.setText(String.format("%.0f / %.0f / %.0f uT",
                        sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                break;


            case Sensor.TYPE_ACCELEROMETER:
                TextView accel = (TextView) findViewById(R.id.acceleration);
                accel.setText(String.format("%.1f / %.1f / %.1f",
                        sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                break;

            case Sensor.TYPE_GRAVITY:
                TextView gravity = (TextView) findViewById(R.id.gravity);
                gravity.setText(String.format("%.2f / %.2f / %.2f",
                        sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                TextView linaccel = (TextView) findViewById(R.id.linaccel);
                linaccel.setText(String.format("%.1f / %.1f / %.1f",
                        sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
