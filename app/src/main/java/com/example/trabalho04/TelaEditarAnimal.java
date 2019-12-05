package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class TelaEditarAnimal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_animal);


    }


    public void btnSalvar(View v){

    }

    public void btnVoltarEditar(View v){
        onBackPressed();
    }
}
