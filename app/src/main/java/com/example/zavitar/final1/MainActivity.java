package com.example.zavitar.final1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //definir view objetos
    private EditText TextEmail;
    private EditText TextPassword;
    private Button bntRegistrar, btnLogin;
    private ProgressDialog progressDialog;

    //Declaramos objeto de firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebase
        mAuth = FirebaseAuth.getInstance();

        // Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText)findViewById(R.id.TxtPassword);
        bntRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.botonLogin);

        progressDialog = new ProgressDialog(this);

        //Adjuntamos al boton
        bntRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void registrarUsuario(){

        //obtemos el email y la contrasena desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar contrasena",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creacion de un nuevo usuario
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //comprovamos si hay exito
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){ //si se presenta una colicion
                                Toast.makeText(MainActivity.this, "Ese  usuario ya existe ", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(MainActivity.this,"No se puedo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();

                    }
                });


    }

    private  void LoginUsuario(){

        //obtemos el email y la contrasena desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar contrasena",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //Login usuario
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //comprovamos si hay exito
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Has TAP Para Avanzar: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();

                            Intent intencion = new Intent(getApplication(),InicioActivity.class);
                            intencion.putExtra(InicioActivity.user,email);
                            startActivity(intencion);

                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){ //si se presenta una colicion
                                Toast.makeText(MainActivity.this, "Ese  usuario ya existe ", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(MainActivity.this,"No se puedo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();

                    }
                });




    }




    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.botonRegistrar:
                // invocamos el metodo:
                registrarUsuario();
                TextEmail.setText("");
                TextPassword.setText("");
                break;
            case R.id.botonLogin:
                LoginUsuario();
                TextEmail.setText("");
                TextPassword.setText("");




        }

    }
}
