package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class TelaEditarAnimal extends AppCompatActivity {
    TextView idAnimalEditar;
    EditText nomeAnimalEditar;
    EditText pesoAnimalEditar;
    EditText corAnimalEditar;
    EditText dataNascimentoAnimalEditar;


    Integer animalId;
    Animal animal;
    String animalCor, animalEspecie, animalNascimento, animalNome, animalSexo;
    Double animalPeso;
    Date dateFormat, dateNascimentoEnviar;
    final int duracao = Toast.LENGTH_LONG;
    EditText editNome, editCor, editNascimento, editPeso;
    Spinner editSexo, editEspecie, spinnerEspecie, spinnerSexo;
    String[] especies = {"Cão", "Gato"};
    String[] sexos = {"Macho", "Fêmea"};
    ArrayAdapter<String> adapterEspecie;
    ArrayAdapter<String> adapterSexo;
    int sexoPosition, especiePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_animal);
        getAnimal();
        Bundle intent = getIntent().getExtras();
        animalId = intent.getInt("id");
        spinnerEspecie = findViewById(R.id.spnEspecieAnimalEditar);
        spinnerSexo = findViewById(R.id.spnSexoAnimalEditar);
        editNome = findViewById(R.id.edtNomeAnimalEditar);
        editCor = findViewById(R.id.edtCorAnimalEditar);
        editNascimento = findViewById(R.id.edtDataNascimentoAnimalEditar);
        editPeso = findViewById(R.id.edtPesoAnimalEditar);
        editEspecie = findViewById(R.id.spnEspecieAnimalEditar);

        adapterEspecie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, especies);
        adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerEspecie.setAdapter(adapterEspecie);

        editSexo = findViewById(R.id.spnSexoAnimalEditar);
        adapterSexo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexos);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerSexo.setAdapter(adapterSexo);

        getAnimal();
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
                                    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(animalNascimento);
                                } else if (key.equals("animalNome")) {
                                    animalNome = jsonReader.nextString();
                                } else if (key.equals("animalPeso")) {
                                    animalPeso = jsonReader.nextDouble();
                                } else if (key.equals("animalSexo")) {
                                    animalSexo = jsonReader.nextString();
                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }

                            }
                            jsonReader.endObject();
                            animal = new Animal(animalId, animalNome, animalEspecie, animalSexo, animalCor, animalPeso, dateFormat);
                            setAnimalFields();
                        } else {
                            // Error handling code goes here
                        }
                        myConnection.disconnect();
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                        Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer o animal", duracao);
                        noItem.show();

                    }
                }
            });
        } catch (
                Exception e) {
            Toast noItem = Toast.makeText(contexto, "Não foi possivel trazer o animal", duracao);
            noItem.show();
        }

    }

    public void setAnimalFields() {
        String nascimento = dateToString(animal.getAnimalNascimento());

        sexoPosition = adapterSexo.getPosition(animal.getAnimalSexo());
        especiePosition = adapterEspecie.getPosition(animal.getAnimalEspecie());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinnerSexo.setSelection(sexoPosition);
                spinnerEspecie.setSelection(especiePosition);
            }
        });


        editCor.setText(animal.getAnimalCor());
        editNascimento.setText(nascimento);
        editNome.setText(animal.getAnimalNome());
        editPeso.setText(animal.getAnimalPeso().toString());
    }

    public String dateToString(Date animalDate) {
        String dateTime;
//            dateTime = animalDate.toString();

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatador.format(animalDate);
        return dataFormatada;
    }

    public void stringToDate(String animalDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateNascimentoEnviar = format.parse(animalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }}

    public void deletarAnimal(View v){
        String animalIdDeletarString = String.valueOf(animalId);
        Intent intent = new Intent(this, TelaDeletarAnimal.class);

        Bundle passarInfosDeletar = new Bundle();
        passarInfosDeletar.putInt("id", animalId);
        passarInfosDeletar.putString("nome", animalNome);
        intent.putExtras(passarInfosDeletar);
        startActivity(intent);
    }


    public void btnSalvar(View v) {

        stringToDate(editNascimento.getText().toString());
        final String cor = editCor.getText().toString();
        final Date nascimento = dateNascimentoEnviar;
        final String nome = editNome.getText().toString();
        final Double peso = parseDouble(editPeso.getText().toString());
        final String sexo = spinnerSexo.getSelectedItem().toString();
        final String especie = spinnerEspecie.getSelectedItem().toString();



            InputStream inputStream = null;
            String result = "";

            try {
//                 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPut httpPUT = new
                        HttpPut("http://10.0.2.2:8080/animal/" + animalId);
                String json = "";
                // 3. build jsonObject
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("animalNome", nome);
                jsonParam.put("animalEspecie", especie);
                jsonParam.put("animalSexo", sexo);
                jsonParam.put("animalCor", cor);
                jsonParam.put("animalNascimento", nascimento);
                jsonParam.put("animalPeso", peso);


                // 4. convert JSONObject to JSON to String
                json = jsonParam.toString();

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);
                // 6. set httpPost Entity
                httpPUT.setEntity(se);
                // 7. Set some headers to inform server about the type of the content
                httpPUT.setHeader("Accept", "application/json");
                httpPUT.setHeader("Content-type", "application/json");
                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPUT);
                // 9. receive response as inputStream
                //                  inputStream = httpResponse.getEntity().getContent();
                //                  // 10. convert inputstream to string
                //                  if(inputStream != null)
                //                      result = convertInputStreamToString(inputStream);
                //                  else
                //                      result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

//            return "EXITO!";
//
//
//
//
////        Animal animal = new Animal(animalId, nome, especie, sexo, cor, peso, nascimento);
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        URL url = new URL("http://10.0.2.2:8080/animal/" + animalId);
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.setRequestMethod("PUT");
//                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                        conn.setRequestProperty("Accept","application/json");
//                        conn.setDoOutput(true);
//                        conn.setDoInput(true);
//
//                        JSONObject jsonParam = new JSONObject();
//                        jsonParam.put("animalNome", nome);
//                        jsonParam.put("animalEspecie", especie);
//                        jsonParam.put("animalSexo", sexo);
//                        jsonParam.put("animalCor", cor);
//                        jsonParam.put("animalNascimento", nascimento);
//                        jsonParam.put("animalPeso", peso);
//
//                        Log.i("JSON", jsonParam.toString());
//                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                        os.writeBytes(jsonParam.toString());
//
//                        os.flush();
//                        os.close();
//
//                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
//                        Log.i("MSG" , conn.getResponseMessage());
//
//                        conn.disconnect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            thread.start();

    }

    public void btnVoltarEditar(View v) {
        onBackPressed();
    }
}
