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

    Integer animalId;
    Animal animal;
    String animalCor, animalEspecie, animalNascimento, animalNome, animalSexo, dateNascimentoEnviarString;
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
    Intent intentPrincipal;

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

        intentPrincipal = new Intent(this, TelaPrincipal.class);
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
        }
    }

    public void dateToExpectedString() {
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

    public boolean validarDate(String dataNascimento) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = format.parse(dataNascimento);
            Date atual = new Date();
            if (strDate.before(atual)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT).show();
            return false;
            //handle exception
        }
    }

    public void btnSalvar(View v) {

        final String cor = editCor.getText().toString();

        final String dataNascimento = editNascimento.getText().toString();
        final String nome = editNome.getText().toString();
        final String sexo = spinnerSexo.getSelectedItem().toString();
        final String especie = spinnerEspecie.getSelectedItem().toString();

        if (!validarDate(dataNascimento)) {
            Toast.makeText(this, "Data Invalida", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editPeso.getText().toString().trim())) {
            Toast.makeText(this, "Campo peso vazio", Toast.LENGTH_SHORT).show();
        } else {
            final Double peso = parseDouble(editPeso.getText().toString());

            if (TextUtils.isEmpty(nome.trim()))
                Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
            else if (peso <= 0 || peso > 122)
                Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(cor.trim()))
                Toast.makeText(this, "Campo cor vazio", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(dataNascimento.trim()))
                Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();
            else {
                stringToDate(editNascimento.getText().toString());
                dateToExpectedString();

                final String nascimento = dateNascimentoEnviarString;
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
                                startActivity(intentPrincipal);
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
    }


    public void btnVoltarEditar(View v) {
        onBackPressed();
    }
}
