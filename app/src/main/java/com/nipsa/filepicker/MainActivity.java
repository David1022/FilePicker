package com.nipsa.filepicker;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private final int FILE_PICKER_CODE = 0;
    Button selector, copiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selector = (Button) findViewById(R.id.selector);
        copiar = (Button) findViewById(R.id.copiar);

        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CHOOSER);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_PICKER_CODE);
            }
        });

        copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copiarImagenFromRawToMemory();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_PICKER_CODE:
                if (resultCode == RESULT_OK) {
                    seleccionarArchivo();
                }
                break;
            default:
                break;
        }
    }

    public void seleccionarArchivo () {

    }

    public void copiarImagenFromRawToMemory () {
        try {
            String rutaSalida = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";

            // Se crean el directorio de salida si no está creado
            File dir = new File(rutaSalida);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InputStream isRaw = getResources().openRawResource(R.raw.icono); // Stream de lectura
            rutaSalida += "icono.png";

            OutputStream bosOut = new FileOutputStream(rutaSalida);

            // Proceso de lectura/escritura
            byte[] buffer = new byte[1024];
            int length;
            while ((length = isRaw.read(buffer)) > 0) {
                bosOut.write(buffer, 0, length);
            }

            bosOut.flush();
            bosOut.close();
            isRaw.close();
        } catch (IOException e) {
            Log.e("ERROR_COPY: ", e.toString());
            Toast.makeText(this, "Error al copiar el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
