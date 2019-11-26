package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TelaCadastroAnimal extends AppCompatActivity {
    private EditText nomeCadastroAnimal;
    private EditText pesoCadastroAnimal;
    private EditText corCadastroAnimal;
    private EditText dataNascimentoAnimal;

    String sexoDoAnimalTexto;
    String especieDoAnimalTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_animal);

        nomeCadastroAnimal = findViewById(R.id.edtNomeAnimal);
        pesoCadastroAnimal = findViewById(R.id.edtPesoAnimal);
        corCadastroAnimal = findViewById(R.id.edtCorAnimal);
        dataNascimentoAnimal = findViewById(R.id.edtDataNascimentoAnimal);

        String[] sexoAnimal = getResources().getStringArray(R.array.arraySexo);
        Spinner spinnerSexoAnimal = findViewById(R.id.spnSexoAnimal);
        ArrayAdapter<String> sexoAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sexoAnimal);
        spinnerSexoAnimal.setAdapter(sexoAnimalAdapter);
        sexoDoAnimalTexto = spinnerSexoAnimal.getSelectedItem().toString();

        String[] especieAnimal = getResources().getStringArray(R.array.arrayEspecie);
        Spinner spinnerEspecieAnimal = findViewById(R.id.spnEspecieAnimal);
        ArrayAdapter<String> especieAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, especieAnimal);
        spinnerEspecieAnimal.setAdapter(especieAnimalAdapter);
        especieDoAnimalTexto = spinnerEspecieAnimal.getSelectedItem().toString();
    }

    public void botaoCadastrar(View v){
        String nomeAnimal = nomeCadastroAnimal.getText().toString();
        String pesoAnimal = pesoCadastroAnimal.getText().toString();
        String corAnimal = corCadastroAnimal.getText().toString();
        String dataNascimento = dataNascimentoAnimal.getText().toString();

        if(TextUtils.isEmpty(nomeAnimal.trim()))
            Toast.makeText(this, "Campo nome inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(pesoAnimal.trim()))
            Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(corAnimal.trim()))
            Toast.makeText(this, "Campo cor inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(dataNascimento.trim()))
            Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();


    }


    public void botaoVoltar(View v){
        onBackPressed();
    }
}
