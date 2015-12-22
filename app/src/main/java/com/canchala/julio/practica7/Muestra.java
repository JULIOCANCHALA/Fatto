package com.canchala.julio.practica7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Muestra extends AppCompatActivity {

    TextView nombre,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra);

        nombre=(TextView)findViewById(R.id.nombre);
        email=(TextView)findViewById(R.id.email);

        Bundle extras=getIntent().getExtras();

        String name=extras.getString("nombre");
        String ema=extras.getString("email");

        nombre.setText(name);
        email.setText(ema);
    }
}
