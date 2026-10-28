package com.example.appcategorias;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Color;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.text.TextUtils;
import java.util.ArrayList;
import android.content.ActivityNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    CheckBox cbMusica, cbCarro, cbCalle, cbPersona;
    Button btnMostrar;
    ImageView imgResultado;
    // Datos del usuario
    String nombreUsuario = "Mariely";
    String materia = "Introducción a las Apps Móviles";
    String institucion = "ITLA";
    GestureDetector gestureDetector;
    Button btnVerInfo;
    int colorFondo = Color.WHITE;

    ActivityResultLauncher<Intent> launcherEditar = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    nombreUsuario = data.getStringExtra("nombre");
                    materia = data.getStringExtra("materia");
                    institucion = data.getStringExtra("institucion");
                    colorFondo = data.getIntExtra("color", Color.WHITE);
                    findViewById(R.id.rootLayout).setBackgroundColor(colorFondo);
                    Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                }

            });
    Uri fotoUri = null; // foto elegida de la galeria
    Bitmap fotoBitmap = null;  // foto tomada con la camara

    ActivityResultLauncher<Intent> launcherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fotoUri = result.getData().getData();
                    fotoBitmap = null;
                    Toast.makeText(this, "Foto actualizada", Toast.LENGTH_SHORT).show();
                }
            });

    ActivityResultLauncher<Intent> launcherCamara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fotoBitmap = (Bitmap) result.getData().getExtras().get("data");
                    fotoUri = null;
                    Toast.makeText(this, "Foto actualizada", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlaza vistas con el xml
        cbMusica = findViewById(R.id.cbMusica);
        cbCarro = findViewById(R.id.cbCarro);
        cbCalle = findViewById(R.id.cbCalle);
        cbPersona = findViewById(R.id.cbPersona);
        btnMostrar = findViewById(R.id.btnMostrar);
        imgResultado = findViewById(R.id.imgResultado);

        btnMostrar.setOnClickListener(v -> mostrarImagen());
        btnVerInfo = findViewById(R.id.btnVerInfo);
        btnVerInfo.setOnClickListener(v -> mostrarInfo());
        findViewById(R.id.btnResumen).setOnClickListener(v -> abrirResumen());
        findViewById(R.id.btnCompartir).setOnClickListener(v -> compartir());
        findViewById(R.id.btnFoto).setOnClickListener(v -> elegirFoto());
        // swipe de derecha a izquierda
        // GestureDetector: swipe de derecha a izquierda
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true; // necesario para que el detector siga los movimientos
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) return false;

                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();

                // Mas horizontal que vertical, hacia la izquierda
                if (Math.abs(dx) > Math.abs(dy) && dx < -100 && Math.abs(velocityX) > 100) {
                    Toast.makeText(MainActivity.this, "Saliendo...", Toast.LENGTH_SHORT).show();
                    finishAffinity(); // cierra la app
                    return true;
                }
                return false;
            }
        });
    }



    private void mostrarImagen() {
        boolean musica = cbMusica.isChecked();
        boolean carro = cbCarro.isChecked();
        boolean calle = cbCalle.isChecked();
        boolean persona = cbPersona.isChecked();

        // Construye una clave con las categorias marcadas (1 = marcada, 0 = no)
        String clave = (musica ? "1" : "0") + (carro ? "1" : "0")
                + (calle ? "1" : "0") + (persona ? "1" : "0");

        int imagen;

        switch (clave) {
            case "1000": imagen = R.drawable.img_musica;       break; // solo musica
            case "0100": imagen = R.drawable.img_carro;        break; // solo carro
            case "0010": imagen = R.drawable.img_calle;        break; // solo calle
            case "0001": imagen = R.drawable.img_persona;      break; // solo persona
            case "1100": imagen = R.drawable.img_musica_carro; break; // musica + carro

            default:     imagen = R.drawable.img_default;      break;
        }

        imgResultado.setImageResource(imagen);

        if (clave.equals("0000")) {
            Toast.makeText(this, "Selecciona al menos una categoría", Toast.LENGTH_SHORT).show();
        }
    }
    // ALERTDIALOG: Ver informacion
    private void mostrarInfo() {
        View vista = LayoutInflater.from(this).inflate(R.layout.dialog_info, null);

        TextView tvNombre = vista.findViewById(R.id.tvNombre);
        TextView tvMateria = vista.findViewById(R.id.tvMateria);
        TextView tvInstitucion = vista.findViewById(R.id.tvInstitucion);

        tvNombre.setText(nombreUsuario);
        tvMateria.setText("Materia: " + materia);
        tvInstitucion.setText("Institución: " + institucion);
        ImageView imgPerfil = vista.findViewById(R.id.imgPerfil);
        if (fotoBitmap != null) {
            imgPerfil.setImageBitmap(fotoBitmap);
        } else if (fotoUri != null) {
            imgPerfil.setImageURI(fotoUri);
        }

        new AlertDialog.Builder(this)
                .setTitle("Mi información")
                .setView(vista)
                .setPositiveButton("Cerrar", null)
                .setNeutralButton("Editar", (dialog, which) -> mostrarEditar())
                .setNegativeButton("Edición avanzada", (dialog, which) -> abrirEditorAvanzado())
                .show();
    }

    // ALERTDIALOG: Editar informacion
    private void mostrarEditar() {
        View vista = LayoutInflater.from(this).inflate(R.layout.dialog_editar, null);

        EditText etNombre = vista.findViewById(R.id.etNombre);
        EditText etMateria = vista.findViewById(R.id.etMateria);
        EditText etInstitucion = vista.findViewById(R.id.etInstitucion);

        // Mouestra los valores actuales
        etNombre.setText(nombreUsuario);
        etMateria.setText(materia);
        etInstitucion.setText(institucion);

        new AlertDialog.Builder(this)
                .setTitle("Editar información")
                .setView(vista)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    nombreUsuario = etNombre.getText().toString();
                    materia = etMateria.getText().toString();
                    institucion = etInstitucion.getText().toString();
                    Toast.makeText(this, "Información actualizada", Toast.LENGTH_SHORT).show();
                    mostrarInfo(); // vuelve a mostrar la info ya actualizada
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    //INTENT EXPLICITO
    private void abrirEditorAvanzado() {
        Intent intent = new Intent(this, EditarPerfilActivity.class);
        intent.putExtra("nombre", nombreUsuario);
        intent.putExtra("materia", materia);
        intent.putExtra("institucion", institucion);
        intent.putExtra("color", colorFondo);
        launcherEditar.launch(intent);
    }

    private String obtenerCategorias() {
        ArrayList<String> lista = new ArrayList<>();
        if (cbMusica.isChecked())  lista.add("música");
        if (cbCarro.isChecked())   lista.add("carro");
        if (cbCalle.isChecked())   lista.add("calle");
        if (cbPersona.isChecked()) lista.add("persona");

        if (lista.isEmpty()) return "ninguna";
        return TextUtils.join(", ", lista);
    }

    // selecciones y datos personales
    private String construirTexto() {
        return "Mi selección: " + obtenerCategorias()
                + "\nNombre: " + nombreUsuario
                + "\nMateria: " + materia
                + "\nInstitución: " + institucion;
    }

    // INTENT EXPLICITO
    private void abrirResumen() {
        Intent intent = new Intent(this, ResumenActivity.class);
        intent.putExtra("texto", construirTexto());
        startActivity(intent);
    }
    // INTENT IMPLICITO
    private void compartir() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mi selección");
        intent.putExtra(Intent.EXTRA_TEXT, construirTexto());
        startActivity(Intent.createChooser(intent, "Compartir con..."));
    }
    // Dialogo para elegir: galeria o camara
    private void elegirFoto() {
        String[] opciones = {"Elegir de la galería", "Tomar una foto"};
        new AlertDialog.Builder(this)
                .setTitle("Foto de perfil")
                .setItems(opciones, (dialog, which) -> {
                    try {
                        if (which == 0) {
                            // INTENT IMPLICITO
                            Intent pick = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pick.setType("image/*");
                            launcherGaleria.launch(pick);
                        } else {
                            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            launcherCamara.launch(cam);
                        }
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(this, "No hay una app disponible", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}