package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TelaCadastro extends AppCompatActivity {
    private EditText nomeCadastro;
    private EditText emailCadastro;
    private EditText senhaCadastro;
    private EditText confirmarSenhaCadastro;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        nomeCadastro = findViewById(R.id.edtNomeCadastro);
        emailCadastro = findViewById(R.id.edtEmailCadastro);
        senhaCadastro = findViewById(R.id.edtSenhaCadastro);
        confirmarSenhaCadastro = findViewById(R.id.edtConfirmarSenhaCadastro);

        String nome = nomeCadastro.getText().toString();
        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();
        String confirmarSenha = confirmarSenhaCadastro.getText().toString();
    }

    public void abrirTelaLogin(View v){
        String nome = nomeCadastro.getText().toString();
        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();
        String confirmarSenha = confirmarSenhaCadastro.getText().toString();



        if(TextUtils.isEmpty(nome.trim()))
            Toast.makeText(this, "Campo nome inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(email.trim()))
            Toast.makeText(this, "Campo e-mail inválido!", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(senha))
            Toast.makeText(this, "Campo senha inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(confirmarSenha))
            Toast.makeText(this, "Campo confirmar senha inválido!", Toast.LENGTH_SHORT).show();
        else if (!(senha.equals(confirmarSenha)))
            Toast.makeText(this, "Senhas não coincidem!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
