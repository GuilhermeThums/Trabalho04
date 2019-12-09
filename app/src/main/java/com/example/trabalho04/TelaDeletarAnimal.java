package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TelaDeletarAnimal extends AppCompatActivity {
    String nomeAnimal;
    int idAnimalInt;

    TextView txvIdAnimalDeletar;
    TextView txvNomeAnimalDeletarConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_deletar_animal);
        txvIdAnimalDeletar = findViewById(R.id.txvIdAnimalDeletar);
        txvNomeAnimalDeletarConteudo = findViewById(R.id.txvNomeAnimalDeletarConteudo);
        //Pega o nome e ID da activity anterior
        Bundle argumentos = getIntent().getExtras();
        idAnimalInt = argumentos.getInt("id");
        nomeAnimal = argumentos.getString("nome");
        //Atualiza os textos com Id e Nome do animal
        txvIdAnimalDeletar.setText("ID do animal: " + idAnimalInt);
        txvNomeAnimalDeletarConteudo.setText(nomeAnimal);

    }

    public void deletarAnimal(View v){


//        onBackPressed();
    }

    public void voltarTelaEditar(View v){
        onBackPressed();
    }
}
