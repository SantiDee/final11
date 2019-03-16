package com.example.zavitar.final1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {


    public  static final String user="name";
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtUser=(TextView)findViewById(R.id.textuser);
        String user = getIntent().getStringExtra("name");
        txtUser.setText("Bienvenido A Clean MX: "+ user +"!");
    }

    //Metodo para el ImageBoton
    public void MessajeImagenBoton(View view){
        Toast.makeText(this,"Aprende A Reciclar",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(),MnuNavegacionActivity.class);
        startActivity(intent);

    }
}
