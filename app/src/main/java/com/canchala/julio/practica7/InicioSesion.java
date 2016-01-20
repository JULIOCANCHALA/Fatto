package com.canchala.julio.practica7;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class InicioSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private Firebase mRef;
    Button iniciar;
    TextView snombre;
    TextView scontra;
    String snom;
    String scont;

    private static final String TAG="Main";
    private static final int RC_SIGN_IN=9001;
    private GoogleApiClient mGoogleApiClient;
    private String mStatusUser;
    private String mStatusEmail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_inicio_sesion);

        Firebase.setAndroidContext(this);
        mRef=new Firebase("https://fatto.firebaseio.com/");

        final Firebase usuarios = mRef.child("Usuarios");

        iniciar=(Button)findViewById(R.id.inientrar);
        snombre=(TextView)findViewById(R.id.iniusuario);
        scontra=(TextView)findViewById(R.id.inicontra);

        findViewById(R.id.id_sign_in_button).setOnClickListener((View.OnClickListener) this);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snom=snombre.getText().toString();
                scont=scontra.getText().toString();

                usuarios.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            inuser newinuser = postSnapshot.getValue(inuser.class);

                            if(newinuser.getNombre().toString().equals("Usuario: "+snom) && newinuser.getContrase().toString().equals(scont)){
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);

                                intent.putExtra("nombre",newinuser.getNombre());
                                intent.putExtra("email",newinuser.getCorreo());

                                startActivity(intent);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });



            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.id_sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn()
    {
        Intent signInIntent= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result){

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount acct=result.getSignInAccount();
            mStatusUser=getString(R.string.signed_in_fmt,acct.getDisplayName());
            mStatusEmail=getString(R.string.signed_in_fmt2,acct.getEmail());
            updateUI(true);
        }
        else{
            updateUI(false);
        }}

    private void updateUI(boolean signedIn)
    {
        if(signedIn){
            Toast.makeText(InicioSesion.this, "Ahora eres parte de Fatto", Toast.LENGTH_LONG).show();

            Intent intent=new Intent(getApplicationContext(),MainActivity.class);

            Firebase usuarios=mRef.child("Usuarios").child(mStatusUser);

            user nuevo=new user(mStatusUser,mStatusEmail,"*");

            usuarios.setValue(nuevo);

            intent.putExtra("nombre",mStatusUser);
            intent.putExtra("email",mStatusEmail);

            startActivity(intent);

        }
        else {
            Toast.makeText(InicioSesion.this, "Registro incorrecto",Toast.LENGTH_LONG).show();


        }
    }



}
