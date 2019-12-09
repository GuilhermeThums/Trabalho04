package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TelaEditarAnimal extends AppCompatActivity {
    TextView idAnimalEditar;
    EditText nomeAnimalEditar;
    EditText pesoAnimalEditar;
    EditText corAnimalEditar;
    EditText dataNascimentoAnimalEditar;


    Integer animalId;
    ArrayList<Animal> Animals = new ArrayList<Animal>();
    String animalCor,animalEspecie,animalNascimento,animalNome,animalSexo;
    Double animalPeso;
    final int duracao = Toast.LENGTH_LONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_animal);
        getAnimal();
        Bundle intent = getIntent().getExtras();
        animalId = intent.getInt("id");
        animalNome = intent.getString("nome");

        idAnimalEditar = findViewById(R.id.txvIdAnimalEditar);
        nomeAnimalEditar = findViewById(R.id.edtNomeAnimal);
        pesoAnimalEditar = findViewById(R.id.edtPesoAnimal);
        corAnimalEditar = findViewById(R.id.edtCorAnimal);
        dataNascimentoAnimalEditar = findViewById(R.id.edtDataNascimentoAnimal);


        nomeAnimalEditar.setText(animalNome);
    }

    public void getAnimal() {
        final Context contexto = getApplicationContext();
        final String URL = "http://10.0.2.2:8080/animal/" + animalId;
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL githubEndpoint = new URL(URL);
                        HttpURLConnection myConnection =
                                (HttpURLConnection) githubEndpoint.openConnection();

                        if (myConnection.getResponseCode() == 200) {
                            InputStream responseBody = myConnection.getInputStream();
                            InputStreamReader responseBodyReader =
                                    new InputStreamReader(responseBody, "UTF-8");
                            JsonReader jsonReader = new JsonReader(responseBodyReader);
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String key = jsonReader.nextName();
                                if (key.equals("animalCor")) {
                                    animalCor = jsonReader.nextString();
                                } else if (key.equals("animalEspecie")) {
                                    animalEspecie = jsonReader.nextString();
                                } else if (key.equals("animalNascimento")) {
                                     animalNascimento = jsonReader.nextString();
                                }else if (key.equals("animalNome")) {
                                     animalNome = jsonReader.nextString();
                                }else if (key.equals("animalPeso")) {
                                    animalPeso = jsonReader.nextDouble();
                                }else if (key.equals("animalSexo")) {
                                     animalSexo = jsonReader.nextString();
                                }
                                else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }

                                Animals.add(new Animal(animalId, animalNome));

                                jsonReader.endObject();
                            }
                        } else {
                            // Error handling code goes here
                        }
                        myConnection.disconnect();
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                        Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer a lista animal", duracao);
                        noItem.show();

                    }
                }
            });
        } catch (
                Exception e) {
            Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer a lista animal", duracao);
            noItem.show();
        }

    }

    public void deletarAnimal(View v){
        String animalIdDeletarString = String.valueOf(animalId);
        Intent intent = new Intent(this, TelaDeletarAnimal.class);

        Bundle passarInfosDeletar = new Bundle();
        passarInfosDeletar.putString("id", animalIdDeletarString);
        passarInfosDeletar.putString("nome", animalNome);
        intent.putExtras(passarInfosDeletar);
        startActivity(intent);
    }


    public void btnSalvar(View v) {

    }

    public void btnVoltarEditar(View v) {
        onBackPressed();
    }
}
