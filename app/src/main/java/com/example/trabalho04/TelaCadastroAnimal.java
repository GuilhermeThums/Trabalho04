package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TelaCadastroAnimal extends AppCompatActivity {
    private EditText nomeCadastroAnimal;
    private EditText pesoCadastroAnimal;
    private EditText corCadastroAnimal;
    private EditText dataNascimentoAnimal;

    private CheckBox vacina1;
    private CheckBox vacina2;
    private CheckBox vacina3;
    private CheckBox vacina4;

    String sexoDoAnimalTexto;
    String especieDoAnimalTexto;

    String vacinaV8 = "Vacina v8";
    String vacinaV10 = "Vacina v10";
    String vacinaV12 = "Vacina v12";
    String vacinaRabica = "Vacina anti raiva";
    String vacinaV3 = "Vacina v3";
    String vacinaV4 = "Vacina v4";
    String vacinaV5 = "Vacina v5";



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

        String[] sexoAnimal = getResources().getStringArray(R.array.arraySexo);
        Spinner spinnerSexoAnimal = findViewById(R.id.spnSexoAnimal);
        ArrayAdapter<String> sexoAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sexoAnimal);
        spinnerSexoAnimal.setAdapter(sexoAnimalAdapter);
        sexoDoAnimalTexto = spinnerSexoAnimal.getSelectedItem().toString();

        String[] especieAnimal = getResources().getStringArray(R.array.arrayEspecie);
        Spinner spinnerEspecieAnimal = findViewById(R.id.spnEspecieAnimal);
        ArrayAdapter<String> especieAnimalAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, especieAnimal);
        spinnerEspecieAnimal.setAdapter(especieAnimalAdapter);
        especieDoAnimalTexto = spinnerEspecieAnimal.getSelectedItem().toString();

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

//        if(especieDoAnimalTexto.equals("Cão")){
//            vacina1.setText(vacinaV8);
//            vacina2.setText(vacinaV10);
//            vacina3.setText(vacinaV12);
//            vacina4.setText(vacinaRabica);
//        } else{
//            vacina1.setText(vacinaV3);
//            vacina2.setText(vacinaV4);
//            vacina3.setText(vacinaV5);
//            vacina4.setText(vacinaRabica);
//        }

    }

    public void botaoCadastrar(View v){
        String nomeAnimal = nomeCadastroAnimal.getText().toString();
        String pesoAnimal = pesoCadastroAnimal.getText().toString();
        String corAnimal = corCadastroAnimal.getText().toString();
        String dataNascimento = dataNascimentoAnimal.getText().toString();

        if(TextUtils.isEmpty(nomeAnimal.trim()))
            Toast.makeText(this, "Campo nome inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(pesoAnimal.trim()))
            Toast.makeText(this, "Campo peso inválido", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(corAnimal.trim()))
            Toast.makeText(this, "Campo cor inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(dataNascimento.trim()))
            Toast.makeText(this, "Campo data de nascimento inválido!", Toast.LENGTH_SHORT).show();


    }


    public void botaoVoltar(View v){
        onBackPressed();
    }
}
