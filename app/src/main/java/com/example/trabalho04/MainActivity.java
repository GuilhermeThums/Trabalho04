package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText senha;
    CheckBox lembrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.edtLogin);
        senha = findViewById(R.id.edtSenha);
        lembrar = findViewById(R.id.checkBoxSalvarPreferencias);

        SharedPreferences preferencias = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferencias.getString("lembrar", "");
        if(checkbox.equals("verdadeiro")){
            Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
            startActivity(intent);
            Toast.makeText(this, "Login salvo!", Toast.LENGTH_SHORT).show();
        }else if(checkbox.equals("falso")){
            Toast.makeText(this, "Por favor faça o login.", Toast.LENGTH_SHORT).show();
        }

        lembrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    SharedPreferences preferencia = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencia.edit();
                    editor.putString("lembrar", "verdadeiro");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Salvar login marcado", Toast.LENGTH_SHORT).show();
                } else if(!buttonView.isChecked()){
                    SharedPreferences preferencia = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencia.edit();
                    editor.putString("lembrar", "falso");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Salvar login desmarcado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void entrarApp(View v){
        String loginTexto = login.getText().toString();
        String senhaTexto = senha.getText().toString();

//        if(loginTexto.equals("admin")&& senhaTexto.equals("admin")){
            Intent intent = new Intent(this, TelaPrincipal.class);
            startActivity(intent);
//        } else
//            Toast.makeText(this, "Login ou senha incorretos!", Toast.LENGTH_SHORT).show();

    }
}
