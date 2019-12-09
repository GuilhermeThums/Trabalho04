package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TelaDeletarAnimal extends AppCompatActivity {
    String nomeAnimal;
    int idAnimalInt;
    Intent intentPrincipal;
    TextView txvIdAnimalDeletar;
    TextView txvNomeAnimalDeletarConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentPrincipal = new Intent(this, TelaPrincipal.class);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/animal/"+idAnimalInt;
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        startActivity(intentPrincipal);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        queue.add(dr);
    }

    public void voltarTelaEditar(View v){
        onBackPressed();
    }
}
