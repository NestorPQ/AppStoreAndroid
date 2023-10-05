package com.senati.appstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btIrRegistrar, btnIrRegistrarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUI();

        btIrRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),registrarProductos.class));
            }
        });

        btnIrRegistrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registrarCategoria.class));
            }
        });
    }

    private void loadUI() {
        btIrRegistrar = findViewById(R.id.irRegistrar);
        btnIrRegistrarCategoria = findViewById(R.id.irRegistrarCategoria);
    }
}