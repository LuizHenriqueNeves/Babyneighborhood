package br.com.paisdavizinhanca.paisdavizinhanca;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class TelaCadastro extends AppCompatActivity {

    EditText editNome, editEmail, editSenha;
    Button btdCancelar, btdRegistrar;
    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btdRegistrar = findViewById(R.id.btdRegistrar);
        btdCancelar = findViewById(R.id.btdCancelar);

        btdCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btdRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){

                    String nome = editNome.getText().toString();
                    String email = editEmail.getText().toString();
                    String senha = editSenha.getText().toString();

                    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
                    }else {

                        url = "http://10.60.191.188:4563/login/registrar.php";

                        parametros = "nome=" + nome + "&email=" + email + "&senha=" + senha;

                        new SolicitaDados().execute(url);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){

            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado){

            try {
                JSONObject obj = new JSONObject(resultado);
                if (!obj.getBoolean("status")) {
                    User.getInstance(getApplicationContext())
                            .userLogin(
                                    obj.getInt("id"),
                                    obj.getString("email"),
                                    obj.getString("username")
                            );
                    Intent abreInicio = new Intent(TelaCadastro.this, TelaInicial.class);
                    startActivity(abreInicio);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha estão incorretos", Toast.LENGTH_LONG).show();
                }
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
