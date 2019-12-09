package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TelaCadastroAnimal extends AppCompatActivity {
    private EditText nomeCadastroAnimal;
    private EditText pesoCadastroAnimal;
    private EditText corCadastroAnimal;
    private EditText dataNascimentoAnimal;

    String sexoDoAnimalTexto;
    String especieDoAnimalTexto;
    Date dateNascimentoEnviar;
    String dateNascimentoEnviarString;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_animal);

        intent = new Intent(this, TelaPrincipal.class);
        nomeCadastroAnimal = findViewById(R.id.edtNomeAnimal);
        pesoCadastroAnimal = findViewById(R.id.edtPesoAnimal);
        corCadastroAnimal = findViewById(R.id.edtCorAnimal);
        dataNascimentoAnimal = findViewById(R.id.edtDataNascimentoAnimal);

        //Define spinner sexo do animal
        String[] sexoAnimal = getResources().getStringArray(R.array.arraySexoAnimal);
        Spinner spinnerSexoAnimal = findViewById(R.id.spnSexoAnimal);
        ArrayAdapter<String> sexoAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sexoAnimal);
        spinnerSexoAnimal.setAdapter(sexoAnimalAdapter);
        sexoDoAnimalTexto = spinnerSexoAnimal.getSelectedItem().toString();
        //Define spinner espécie do animal
        String[] especieAnimal = getResources().getStringArray(R.array.arrayEspecie);
        Spinner spinnerEspecieAnimal = findViewById(R.id.spnEspecieAnimal);
        ArrayAdapter<String> especieAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, especieAnimal);
        spinnerEspecieAnimal.setAdapter(especieAnimalAdapter);
        especieDoAnimalTexto = spinnerEspecieAnimal.getSelectedItem().toString();
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

    public void botaoCadastrar(View v) {
        TelaEditarAnimal telaEditarAnimal = new TelaEditarAnimal();
        final String nomeAnimal = nomeCadastroAnimal.getText().toString();
        final String pesoAnimal = pesoCadastroAnimal.getText().toString();
        final String corAnimal = corCadastroAnimal.getText().toString();
        final String dataNascimento = dataNascimentoAnimal.getText().toString();
        stringToDate(dataNascimento);
        String sexo = sexoDoAnimalTexto;
        String especie = especieDoAnimalTexto;

//        boolean nomeRegex = Pattern.matches("^[a-zA-Z\\u00C0-\\u00FF]{2,224}$", nomeAnimal);
        if (!validarDate(dataNascimento)) {
            Toast.makeText(this, "Data Invalida", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pesoAnimal.trim())) {
            Toast.makeText(this, "Campo peso vazio", Toast.LENGTH_SHORT).show();
        } else {
            dateToExpectedString();
            double pesoAnimalDouble = Double.parseDouble(pesoAnimal);

            if (TextUtils.isEmpty(nomeAnimal.trim()))
                Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();

            else if (pesoAnimalDouble <= 0 || pesoAnimalDouble > 122)
                Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(corAnimal.trim()))
                Toast.makeText(this, "Campo cor vazio", Toast.LENGTH_SHORT).show();
//        else if(!nomeRegex)
//            Toast.makeText(this, "Campo cor inválido", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(dataNascimento.trim()))
                Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();
            else {


                RequestQueue queue = Volley.newRequestQueue(this);
                final String url = "http://10.0.2.2:8080/animal/";

                final JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("animalNome", nomeAnimal);
                    jsonObject.put("animalEspecie", especieDoAnimalTexto);
                    jsonObject.put("animalSexo", sexoDoAnimalTexto);
                    jsonObject.put("animalCor", corAnimal);
                    jsonObject.put("animalNascimento", dateNascimentoEnviarString);
                    jsonObject.put("animalPeso", pesoAnimalDouble);
                } catch (JSONException e) {
                    // handle exception
                }


                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // response
                                Log.d("Response", response.toString());
                                startActivity(intent);

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
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        return params;
                    }
                };
                queue.add(postRequest);
            }
        }
    }

    public void botaoVoltar(View v) {
        onBackPressed();
    }
}
