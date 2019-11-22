package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.edtLogin);
        senha = findViewById(R.id.edtSenha);
    }

    public void abrirTelaCadastro(View v){
        Intent intent = new Intent(this, TelaCadastro.class);
        startActivity(intent);
    }
    public void abrirTelaPrincipal(View v){
        Intent intent = new Intent(this, TelaPrincipal.class);
        startActivity(intent);
    }
}
