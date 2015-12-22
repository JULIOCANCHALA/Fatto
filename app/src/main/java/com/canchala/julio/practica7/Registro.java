package com.canchala.julio.practica7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class Registro extends AppCompatActivity {

    private Firebase mRef;
    Button registrar;
    TextView nombre;
    TextView correo;
    TextView contra;
    String nom;
    String corr;
    String cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        registrar=(Button)findViewById(R.id.regientrar);
        nombre=(TextView)findViewById(R.id.entusuario);
        correo=(TextView)findViewById(R.id.entcorreo);
        contra=(TextView)findViewById(R.id.entcontra);

        Firebase.setAndroidContext(this);
        mRef=new Firebase("https://fatto.firebaseio.com/");

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nom=nombre.getText().toString();
                corr=correo.getText().toString();
                cont=contra.getText().toString();

                Firebase usuarios=mRef.child("Usuarios").child(nom);

                user nuevo=new user(nom,corr,cont);

                usuarios.setValue(nuevo, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                        if (firebaseError != null) {

                            Toast.makeText(Registro.this, "Registro incorrecto" + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Registro.this, "Ahora eres parte de Fatto", Toast.LENGTH_SHORT).show();
                            nombre.setText("");
                            correo.setText("");
                            contra.setText("");

                            Intent intent=new Intent(getApplicationContext(),Muestra.class);

                            intent.putExtra("nombre", "Usuario: "+nom);
                            intent.putExtra("email", "Correo: "+corr);

                            startActivity(intent);

                        }

                    }
                });

            }
        });




    }
}
