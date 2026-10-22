package com.edu.georegistro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager administradorSensores;
    private Sensor acelerometro;
    private TextView tvValores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        tvValores = findViewById(R.id.tvValoresSensor);
        administradorSensores = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (administradorSensores != null) {
            acelerometro = administradorSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (acelerometro != null) {
            administradorSensores.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (administradorSensores != null) {
            administradorSensores.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent evento) {
        if (evento.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = evento.values[0];
            float y = evento.values[1];
            float z = evento.values[2];

            // Actualizamos los números en la pantalla en tiempo real
            String texto = String.format("Eje X: %.2f\nEje Y: %.2f\nEje Z: %.2f", x, y, z);
            tvValores.setText(texto);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {
    }
}