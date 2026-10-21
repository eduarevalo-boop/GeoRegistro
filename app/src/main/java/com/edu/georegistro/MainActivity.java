package com.edu.georegistro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrar;
    private Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar = findViewById(R.id.botonNuevaIncidencia);
        btnMapa = findViewById(R.id.botonVerUbicaciones);

        btnRegistrar.setOnClickListener(this);
        btnMapa.setOnClickListener(this);
    }

    @Override
    public void onClick(View vista) {
        int id = vista.getId();

        if (id == R.id.botonNuevaIncidencia) {
            Intent intentRegistro = new Intent(this, RegistroActivity.class);
            startActivity(intentRegistro);
        } else if (id == R.id.botonVerUbicaciones) {
            Intent intentMapa = new Intent(this, MapaActivity.class);
            startActivity(intentMapa);
        }
    }
}