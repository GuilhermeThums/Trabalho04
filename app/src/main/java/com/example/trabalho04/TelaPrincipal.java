package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }

    public void cadastrarNovoAnimal(View v){
        Intent intent = new Intent(this, TelaCadastroAnimal.class);
        startActivity(intent);
    }
}
