package br.com.paisdavizinhanca.paisdavizinhanca;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TelaCriarGrupo extends AppCompatActivity {

    EditText editNomeGrupo, editComent;
    Button btdCancelar, btdRegistrar;
    String url = "";
    String parametros = "";

    int idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tela_criar_grupo);

        idUser = User.getInstance(this).getUserId();

        editNomeGrupo = findViewById(R.id.editNomeGrupo);
        editComent = findViewById(R.id.editComent);
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

                    int User = idUser;
                    String coment = editComent.getText().toString();
                    String nomeGrupo = editNomeGrupo.getText().toString();

                    if (nomeGrupo.isEmpty() || coment.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
                    }else {

                        url = "http://10.60.191.188:4563/login/registrarGrupo.php";

                        parametros = "adm=" + User  + "&des=" + coment + "&nome=" + nomeGrupo;

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

            if(resultado.contains("usuario_ja_cadastrado")){
                Toast.makeText(getApplicationContext(), "Usuário já possui um grupo cadastrado", Toast.LENGTH_LONG).show();

            }else if(resultado.contains("registro_ok")){

                Intent abreInicio = new Intent(TelaCriarGrupo.this, TelaInicial.class);
                Toast.makeText(getApplicationContext(), "Grupo criado com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(abreInicio);

            }else{
                Toast.makeText(getApplicationContext(), "Usuário ou senha estão incorretos", Toast.LENGTH_LONG).show();
            }
        }
    }

}
