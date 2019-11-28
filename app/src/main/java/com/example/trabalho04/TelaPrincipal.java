package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TelaPrincipal extends AppCompatActivity {

    private static final String URL = "http://10.0.2.2:8080/animal";
    final int duracao = Toast.LENGTH_LONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

    }

    public void cadastrarNovoAnimal(View v) {
        Intent intent = new Intent(this, TelaCadastroAnimal.class);
        startActivity(intent);
    }

    public void listaAnimal(View v) {
        final TextView exemplo = findViewById(R.id.textView);
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

                            String value = jsonReader.nextString();
//                            exemplo.setText(value);
                            System.out.println(value);
                        } else {
                            // Error handling code goes here
                        }

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
