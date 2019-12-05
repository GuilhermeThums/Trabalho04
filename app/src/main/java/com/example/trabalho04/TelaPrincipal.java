package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    ArrayList<Animal> Animals = new ArrayList<Animal>();
    Spinner spinner;
    ArrayAdapter<Animal> spinnerArrayAdapter;
    Integer valueID;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
//        this.listaAnimal();
        spinnerAnimal=findViewById(R.id.spnAnimal);

         spinnerArrayAdapter = new ArrayAdapter<Animal>(this, android.R.layout.simple_spinner_item, Animals);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerAnimal.setAdapter(spinnerArrayAdapter);

    }

    public void cadastrarNovoAnimal(View v) {
        Intent intent = new Intent(this, TelaCadastroAnimal.class);
        startActivity(intent);
    }

    public void editarAnimal (View v){

//        String itemSelecionado = spinnerAnimal.getSelectedItem().toString();
//        Animal animal = (Animal )spinnerAnimal.getSelectedItem();
//        itemSelecionado = Integer.toString( animal.getId());
//        TextView label = findViewById(R.id.txvBemVindo);
//        label.setText((itemSelecionado));

        Intent intent = new Intent(this, TelaEditarAnimal.class);
        startActivity(intent);
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
                                while(jsonReader.hasNext()){
                                String key = jsonReader.nextName(); // Fetch the next key
                                if(key.equals("id")){
                                    valueID = jsonReader.nextInt();
                                }else if (key.equals("animalNome")) { // Check if desired key
                                    // Fetch the value as a String
                                    value = jsonReader.nextString();

                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }
                                }
                                Animals.add(new Animal(valueID,value));

                                jsonReader.endObject();
                            }
                            jsonReader.endArray();
                            spinnerArrayAdapter.notifyDataSetChanged();
                        } else {
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
