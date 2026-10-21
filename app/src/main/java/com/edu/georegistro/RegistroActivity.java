package com.edu.georegistro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText cajaTitulo, cajaDetalle, cajaFecha, cajaLatitud, cajaLongitud;
    private Spinner listaCategorias;
    private Button botonGPS, botonGuardar;

    private FusedLocationProviderClient clienteUbicacion;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        cajaTitulo = findViewById(R.id.etIngresoTitulo);
        cajaDetalle = findViewById(R.id.etIngresoDetalle);
        cajaFecha = findViewById(R.id.etIngresoFecha);
        cajaLatitud = findViewById(R.id.etValorLat);
        cajaLongitud = findViewById(R.id.etValorLon);
        listaCategorias = findViewById(R.id.spnTipoIncidencia);
        botonGPS = findViewById(R.id.btnCapturarGPS);
        botonGuardar = findViewById(R.id.btnRegistrarDatos);

        String[] categorias = {"- Seleccione -", "Daño Estructural", "Seguridad Pública", "Fallo Eléctrico", "Otro"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaCategorias.setAdapter(adaptador);

        clienteUbicacion = LocationServices.getFusedLocationProviderClient(this);
        db = FirebaseFirestore.getInstance();

        botonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCoordenadas();
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarValidaciones();
            }
        });
    }

    private void obtenerCoordenadas() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        clienteUbicacion.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    cajaLatitud.setText(String.valueOf(location.getLatitude()));
                    cajaLongitud.setText(String.valueOf(location.getLongitude()));
                    Toast.makeText(RegistroActivity.this, "Ubicación obtenida", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroActivity.this, "Error al obtener ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ejecutarValidaciones() {
        String txtTitulo = cajaTitulo.getText().toString().trim();
        String txtDetalle = cajaDetalle.getText().toString().trim();
        String txtFecha = cajaFecha.getText().toString().trim();
        int indexCategoria = listaCategorias.getSelectedItemPosition();

        if (txtTitulo.isEmpty()) {
            cajaTitulo.setError("El título es obligatorio");
            return;
        }
        if (txtDetalle.isEmpty()) {
            cajaDetalle.setError("Debe ingresar un detalle");
            return;
        }
        if (indexCategoria == 0) {
            Toast.makeText(this, "Debe seleccionar una categoría de la lista", Toast.LENGTH_LONG).show();
            return;
        }
        if (txtFecha.isEmpty()) {
            cajaFecha.setError("Indique la fecha");
            return;
        }

        Map<String, Object> incidencia = new HashMap<>();
        incidencia.put("titulo", txtTitulo);
        incidencia.put("detalle", txtDetalle);
        incidencia.put("categoria", listaCategorias.getSelectedItem().toString());
        incidencia.put("fecha", txtFecha);
        incidencia.put("latitud", cajaLatitud.getText().toString());
        incidencia.put("longitud", cajaLongitud.getText().toString());

        db.collection("incidencias")
                .add(incidencia)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegistroActivity.this, "¡Incidencia guardada en la nube!", Toast.LENGTH_LONG).show();
                        cajaTitulo.setText("");
                        cajaDetalle.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "Error de red: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}