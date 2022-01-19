package br.com.paisdavizinhanca.paisdavizinhanca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView txtCadastro;
    Button btdLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if(User.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, TelaInicial.class));
            return;
        }

        txtCadastro = findViewById(R.id.txtCadastro);
        btdLogin = findViewById(R.id.fzLogin);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(abreCadastro);
            }
        });

        btdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreLogin = new Intent(MainActivity.this, TelaLogin.class);
                startActivity(abreLogin);
            }
        });
    }
}
