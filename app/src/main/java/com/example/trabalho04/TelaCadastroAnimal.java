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
    String sexoDoAnimalTexto;
    String especieDoAnimalTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_animal);

        nomeCadastroAnimal = findViewById(R.id.edtNomeAnimal);
        pesoCadastroAnimal = findViewById(R.id.edtPesoAnimal);
        corCadastroAnimal = findViewById(R.id.edtCorAnimal);
        dataNascimentoAnimal = findViewById(R.id.edtDataNascimentoAnimal);
        vacina1 = findViewById(R.id.ckbVacina1);
        vacina2 = findViewById(R.id.ckbVacina2);
        vacina3 = findViewById(R.id.ckbVacina3);
        vacina4 = findViewById(R.id.ckbVacina4);

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
        //Muda o conteúdo da CheckBox quando muda spinnerEspecieAnimal
        spinnerEspecieAnimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    vacina1.setText(vacinaV8);
                    vacina2.setText(vacinaV10);
                    vacina3.setText(vacinaV12);
                    vacina4.setText(vacinaRabica);
                    if(vacina1.isChecked())
                        vacina1.toggle();
                    if(vacina2.isChecked())
                        vacina2.toggle();
                    if(vacina3.isChecked())
                        vacina3.toggle();
                    if(vacina4.isChecked())
                        vacina4.toggle();
                } else if(position == 1){
                    vacina1.setText(vacinaV3);
                    vacina2.setText(vacinaV4);
                    vacina3.setText(vacinaV5);
                    vacina4.setText(vacinaRabica);
                    if(vacina1.isChecked())
                        vacina1.toggle();
                    if(vacina2.isChecked())
                        vacina2.toggle();
                    if(vacina3.isChecked())
                        vacina3.toggle();
                    if(vacina4.isChecked())
                        vacina4.toggle();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void botaoCadastrar(View v){
        TelaEditarAnimal telaEditarAnimal = new TelaEditarAnimal();
        final String nomeAnimal = nomeCadastroAnimal.getText().toString();
        final String pesoAnimal = pesoCadastroAnimal.getText().toString();
        final String corAnimal = corCadastroAnimal.getText().toString();
        final String dataNascimento = dataNascimentoAnimal.getText().toString();
        String sexo = sexoDoAnimalTexto;
        String especie =  especieDoAnimalTexto;
        double pesoAnimalDouble = Double.parseDouble(pesoAnimal);

//        boolean nomeRegex = Pattern.matches("^[a-zA-Z\\u00C0-\\u00FF]{2,224}$", nomeAnimal);

        if(vacina1.isChecked()){
            String vacina1Texto = vacina1.getText().toString();
        }
        if(vacina2.isChecked()){
            String vacina2Texto = vacina2.getText().toString();
        }
        if(vacina3.isChecked()){
            String vacina3Texto = vacina3.getText().toString();
        }
        if(vacina4.isChecked()){
            String vacina4Texto = vacina4.getText().toString();
        }

        if (!TextUtils.isEmpty(dataNascimento)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date strDate = format.parse(dataNascimento);
                Date atual = new Date();
                if (strDate.after(atual)) {
                    Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT).show();
                } else {
                }
            } catch (ParseException e) {
                //handle exception
            }
        }

        if(TextUtils.isEmpty(nomeAnimal.trim()))
            Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
//        else if(!nomeRegex)
//            Toast.makeText(this, "Campo nome inválido (apenas letras)", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(pesoAnimal.trim()))
            Toast.makeText(this, "Campo peso vazio", Toast.LENGTH_SHORT).show();
        else if(pesoAnimalDouble <= 0 || pesoAnimalDouble > 122)
            Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(corAnimal.trim()))
            Toast.makeText(this, "Campo cor vazio", Toast.LENGTH_SHORT).show();
//        else if(!nomeRegex)
//            Toast.makeText(this, "Campo cor inválido", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(dataNascimento.trim()))
            Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();
        else {
//            Toast.makeText(this, "Tudo ok!", Toast.LENGTH_SHORT).show();
            telaEditarAnimal.stringToDate(dataNascimento);


            RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "http://10.0.2.2:8080/animal/";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("animalNome", nomeAnimal);
                    params.put("animalEspecie", especieDoAnimalTexto);
                    params.put("animalSexo", sexoDoAnimalTexto);
                    params.put("animalCor", corAnimal);
                    params.put("animalNascimento", dataNascimento);
                    params.put("animalPeso", pesoAnimal);

                    return params;
                }
            };
            queue.add(postRequest);


            Intent intent = new Intent(this, TelaPrincipal.class);
            startActivity(intent);
        }
    }

    public void botaoVoltar(View v){
        onBackPressed();
    }
}
