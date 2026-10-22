package com.edu.georegistro;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapaGoogle;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        db = FirebaseFirestore.getInstance();

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
        mapaGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(centroChillan, 13f));

        db.collection("incidencias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documento : task.getResult()) {
                                String titulo = documento.getString("titulo");
                                String latStr = documento.getString("latitud");
                                String lonStr = documento.getString("longitud");

                                if (latStr != null && lonStr != null && !latStr.isEmpty() && !lonStr.isEmpty()) {
                                    try {
                                        double lat = Double.parseDouble(latStr);
                                        double lon = Double.parseDouble(lonStr);
                                        LatLng posicion = new LatLng(lat, lon);

                                        mapaGoogle.addMarker(new MarkerOptions()
                                                .position(posicion)
                                                .title(titulo));
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(MapaActivity.this, "Error al cargar los marcadores", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}