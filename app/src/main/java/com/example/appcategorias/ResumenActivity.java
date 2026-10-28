package com.example.appcategorias;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        // Recibe el texto enviado por el Intent explicito
        String texto = getIntent().getStringExtra("texto");

        TextView tvResumen = findViewById(R.id.tvResumen);
        tvResumen.setText(texto);

        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
    }
}