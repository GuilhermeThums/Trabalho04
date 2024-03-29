package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class TelaPrincipal extends AppCompatActivity {

    private static final String URL = "http://10.0.2.2:8080/animal";
    final int duracao = Toast.LENGTH_LONG;
    ArrayList<Animal> animals = new ArrayList<Animal>();
    Spinner spinnerAnimal;
    ArrayAdapter<Animal> spinnerArrayAdapter;
    Integer valueID;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        this.listaAnimal();
        spinnerAnimal = findViewById(R.id.spnAnimal);
        spinnerArrayAdapter = new ArrayAdapter<Animal>(this, android.R.layout.simple_spinner_item, animals);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerAnimal.setAdapter(spinnerArrayAdapter);

    }

    public void cadastrarNovoAnimal(View v) {
        Intent intent = new Intent(this, TelaCadastroAnimal.class);
        startActivity(intent);
    }

    public void editarAnimal(View v) {
        Animal animal = (Animal) spinnerAnimal.getSelectedItem();
        int itemSelecionado = animal.getId();

        Intent intent = new Intent(this, TelaEditarAnimal.class);
        intent.putExtra("id",itemSelecionado);
        startActivity(intent);
    }

    //Muda o arquivo de preferências pra "falso" e salva
    public void sairSessao(View v) {
        SharedPreferences preferencia = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putString("lembrar", "falso");
        editor.apply();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Muda a função de voltar do Android para salvar o arquivo de preferências como "falso" e voltar pra tela de login
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferencia = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putString("lembrar", "falso");
        editor.apply();
        finish();
    }

    public void listaAnimal() {
        final TextView exemplo = findViewById(R.id.txvBemVindo);
        final Context contexto = getApplicationContext();


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
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String key = jsonReader.nextName(); // Fetch the next key
                                    if (key.equals("id")) {
                                        valueID = jsonReader.nextInt();
                                    } else if (key.equals("animalNome")) { // Check if desired key
                                        // Fetch the value as a String
                                        value = jsonReader.nextString();

                                    } else {
                                        jsonReader.skipValue(); // Skip values of other keys
                                    }
                                }
                                animals.add(new Animal(valueID, value));

                                jsonReader.endObject();
                            }
                            jsonReader.endArray();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spinnerArrayAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            Toast noItem = Toast.makeText(contexto, "Erro ao conectar com a API", duracao);
                            noItem.show();
                            // Error handling code goes here
                        }
                        myConnection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer a lista animal", duracao);
                        noItem.show();

                    }
                }
            });
        } catch (Exception e) {
            Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer a lista animal", duracao);
            noItem.show();
        }

    }


}
