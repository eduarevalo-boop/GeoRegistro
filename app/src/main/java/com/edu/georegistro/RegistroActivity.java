package com.edu.georegistro;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText cajaTitulo, cajaDetalle, cajaFecha, cajaLatitud, cajaLongitud;
    private Spinner listaCategorias;
    private Button botonGPS, botonGuardar;

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

        botonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegistroActivity.this, "AQUIIII VA UN TEXTOOOO, PENDIENTEE", Toast.LENGTH_SHORT).show();
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarValidaciones();
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

        Toast.makeText(this, "Datos validados. Listo para enviar a Firebase.", Toast.LENGTH_SHORT).show();
    }
}