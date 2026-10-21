package com.edu.georegistro;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapaGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment fragmentoMapa = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapaIncidencias);

        if (fragmentoMapa != null) {
            fragmentoMapa.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapaGoogle = googleMap;

        LatLng centroChillan = new LatLng(-36.589674, -72.082504);
        mapaGoogle.addMarker(new MarkerOptions().position(centroChillan).title("Centro de Operaciones"));

        mapaGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(centroChillan, 14f));
    }
}