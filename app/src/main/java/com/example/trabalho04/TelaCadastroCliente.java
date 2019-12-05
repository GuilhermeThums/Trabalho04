package com.example.trabalho04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class TelaCadastroCliente extends AppCompatActivity {
    private EditText nomeCadastroCliente;
    private EditText emailCadastroCliente;
    private EditText senhaCadastroCliente;
    private EditText confirmarSenhaCadastroCliente;
    public String sexoDoClienteTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_cliente);

        nomeCadastroCliente = findViewById(R.id.edtNomeCliente);
        emailCadastroCliente = findViewById(R.id.edtEmailCliente);
        senhaCadastroCliente = findViewById(R.id.edtSenhaCliente);
        confirmarSenhaCadastroCliente = findViewById(R.id.edtConfirmarSenhaCliente);

        String[] sexoCliente = getResources().getStringArray(R.array.arraySexoHumano);
        Spinner spinnerSexoCliente = findViewById(R.id.spnSexoCliente);
        ArrayAdapter<String> sexoClienteAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sexoCliente);
        spinnerSexoCliente.setAdapter(sexoClienteAdapter);
        sexoDoClienteTexto = spinnerSexoCliente.getSelectedItem().toString();

        String nome = nomeCadastroCliente.getText().toString();
        String email = emailCadastroCliente.getText().toString();
        String senha = senhaCadastroCliente.getText().toString();
        String confirmarSenha = confirmarSenhaCadastroCliente.getText().toString();
    }

    public void abrirTelaLogin(View v){
        String nomeCliente = nomeCadastroCliente.getText().toString();
        String emailCliente = emailCadastroCliente.getText().toString();
        String senha = senhaCadastroCliente.getText().toString();
        String confirmarSenha = confirmarSenhaCadastroCliente.getText().toString();

        boolean emailRegex = Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", emailCliente);
        boolean senhaRegex = Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,8}$", senha);


        if(TextUtils.isEmpty(nomeCliente.trim()))
            Toast.makeText(this, "Campo nome inválido!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(emailCliente.trim()))
            Toast.makeText(this, "Campo e-mail inválido!", Toast.LENGTH_SHORT).show();
        else if(!emailRegex)
            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(senha))
            Toast.makeText(this, "Campo senha inválido!", Toast.LENGTH_SHORT).show();
        else if(!senhaRegex)
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(confirmarSenha))
            Toast.makeText(this, "Campo confirmar senha inválido!", Toast.LENGTH_SHORT).show();
        else if (!(senha.equals(confirmarSenha)))
            Toast.makeText(this, "Senhas não coincidem!", Toast.LENGTH_SHORT).show();
        else {
//            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Cadastrado!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
