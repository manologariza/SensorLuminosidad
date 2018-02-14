package com.example.manolo.sensorluminosidad;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorLuz;
    private SensorEventListener luzEventListener;
    private View layoutRaiz;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutRaiz=findViewById(R.id.layoutRaiz);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorLuz=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(sensorLuz == null){
            Toast.makeText(this, "Su dispositivo no tiene sensor de luminosidad", Toast.LENGTH_LONG).show();
            finish();
        }

        maxValue=sensorLuz.getMaximumRange();

        luzEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float valor=sensorEvent.values[0];
                getSupportActionBar().setTitle("Luminosidad: " + valor + " lx");

                int nuevoValor=(int)(255*valor/maxValue);
                layoutRaiz.setBackgroundColor(Color.rgb(nuevoValor, nuevoValor, nuevoValor));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(luzEventListener, sensorLuz, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(luzEventListener);
    }
}
