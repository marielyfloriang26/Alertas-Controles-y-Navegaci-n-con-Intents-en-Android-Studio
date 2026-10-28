package com.example.appcategorias;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditarPerfilActivity extends AppCompatActivity {

    EditText etNombre, etMateria, etInstitucion;
    View rootEditar;
    int colorSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        rootEditar = findViewById(R.id.rootEditar);
        etNombre = findViewById(R.id.etNombreAv);
        etMateria = findViewById(R.id.etMateriaAv);
        etInstitucion = findViewById(R.id.etInstitucionAv);

        // Recibe los datos enviados por MainActivity (Intent explicito)
        Intent recibido = getIntent();
        etNombre.setText(recibido.getStringExtra("nombre"));
        etMateria.setText(recibido.getStringExtra("materia"));
        etInstitucion.setText(recibido.getStringExtra("institucion"));
        colorSeleccionado = recibido.getIntExtra("color", Color.WHITE);
        rootEditar.setBackgroundColor(colorSeleccionado);

        // Botones de color
        findViewById(R.id.btnColorBlanco).setOnClickListener(v -> cambiarColor(Color.WHITE));
        findViewById(R.id.btnColorAzul).setOnClickListener(v -> cambiarColor(Color.parseColor("#BBDEFB")));
        findViewById(R.id.btnColorVerde).setOnClickListener(v -> cambiarColor(Color.parseColor("#C8E6C9")));

        // Guardar y devolver los datos a MainActivity
        Button btnGuardar = findViewById(R.id.btnGuardarAv);
        btnGuardar.setOnClickListener(v -> {
            Intent resultado = new Intent();
            resultado.putExtra("nombre", etNombre.getText().toString());
            resultado.putExtra("materia", etMateria.getText().toString());
            resultado.putExtra("institucion", etInstitucion.getText().toString());
            resultado.putExtra("color", colorSeleccionado);
            setResult(RESULT_OK, resultado);
            finish();
        });
    }

    private void cambiarColor(int color) {
        colorSeleccionado = color;
        rootEditar.setBackgroundColor(color); // vista previa inmediata
    }
}