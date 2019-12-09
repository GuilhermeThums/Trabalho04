package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class TelaEditarAnimal extends AppCompatActivity {
    TextView idAnimalEditar;
    EditText nomeAnimalEditar;
    EditText pesoAnimalEditar;
    EditText corAnimalEditar;
    EditText dataNascimentoAnimalEditar;

    private CheckBox vacina1;
    private CheckBox vacina2;
    private CheckBox vacina3;
    private CheckBox vacina4;

    String vacinaV8 = "Vacina v8";
    String vacinaV10 = "Vacina v10";
    String vacinaV12 = "Vacina v12";
    String vacinaRabica = "Vacina anti-rábica";
    String vacinaV3 = "Vacina v3";
    String vacinaV4 = "Vacina v4";
    String vacinaV5 = "Vacina v5";


    Integer animalId;
    Animal animal;
    String animalCor, animalEspecie, animalNascimento, animalNome, animalSexo , dateNascimentoEnviarString;
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
        vacina1 = findViewById(R.id.ckbVacina1Editar);
        vacina2 = findViewById(R.id.ckbVacina2Editar);
        vacina3 = findViewById(R.id.ckbVacina3Editar);
        vacina4 = findViewById(R.id.ckbVacina4Editar);

        adapterEspecie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, especies);
        adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerEspecie.setAdapter(adapterEspecie);

        editSexo = findViewById(R.id.spnSexoAnimalEditar);
        adapterSexo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexos);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerSexo.setAdapter(adapterSexo);

        spinnerEspecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    vacina1.setText(vacinaV8);
                    vacina2.setText(vacinaV10);
                    vacina3.setText(vacinaV12);
                    vacina4.setText(vacinaRabica);
                    if (vacina1.isChecked())
                        vacina1.toggle();
                    if (vacina2.isChecked())
                        vacina2.toggle();
                    if (vacina3.isChecked())
                        vacina3.toggle();
                    if (vacina4.isChecked())
                        vacina4.toggle();
                } else if (position == 1) {
                    vacina1.setText(vacinaV3);
                    vacina2.setText(vacinaV4);
                    vacina3.setText(vacinaV5);
                    vacina4.setText(vacinaRabica);
                    if (vacina1.isChecked())
                        vacina1.toggle();
                    if (vacina2.isChecked())
                        vacina2.toggle();
                    if (vacina3.isChecked())
                        vacina3.toggle();
                    if (vacina4.isChecked())
                        vacina4.toggle();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        }
    }
    public void dateToExpectedString(){
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        dateNascimentoEnviarString = formatador.format(dateNascimentoEnviar);
    }
    public void deletarAnimal(View v) {
        Intent intent = new Intent(this, TelaDeletarAnimal.class);
        animalNome = animal.getAnimalNome();
        Bundle passarInfosDeletar = new Bundle();
        passarInfosDeletar.putInt("id", animalId);
        passarInfosDeletar.putString("nome", animalNome);
        intent.putExtras(passarInfosDeletar);
        startActivity(intent);
    }


    public void btnSalvar(View v) {
        stringToDate(editNascimento.getText().toString());
        dateToExpectedString();
        final String cor = editCor.getText().toString();
        final String nascimento = dateNascimentoEnviarString;
        final String nome = editNome.getText().toString();
        final Double peso = parseDouble(editPeso.getText().toString());
        final String sexo = spinnerSexo.getSelectedItem().toString();
        final String especie = spinnerEspecie.getSelectedItem().toString();

        if (TextUtils.isEmpty(nome.trim()))
            Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
//        else if(!nomeRegex)
//            Toast.makeText(this, "Campo nome inválido (apenas letras)", Toast.LENGTH_SHORT).show();
        else if (peso <= 0 || peso > 122)
            Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(cor.trim()))
            Toast.makeText(this, "Campo cor vazio", Toast.LENGTH_SHORT).show();
//        else if(!nomeRegex)
//            Toast.makeText(this, "Campo cor inválido", Toast.LENGTH_SHORT).show();
//        else if(nascimento)
//            Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();
        else {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:8080/animal/" + animalId;


            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("animalNome", nome);
                jsonObject.put("animalEspecie", especie);
                jsonObject.put("animalSexo", sexo);
                jsonObject.put("animalCor", cor);
                jsonObject.put("animalNascimento", nascimento);
                jsonObject.put("animalPeso", peso);
            } catch (JSONException e) {
                // handle exception
            }


            JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // response
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    return headers;
                }

                @Override
                public byte[] getBody() {

                    try {
                        Log.i("json", jsonObject.toString());
                        return jsonObject.toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };


            queue.add(putRequest);
        }
    }


    public void btnVoltarEditar(View v) {
        onBackPressed();
    }
}
