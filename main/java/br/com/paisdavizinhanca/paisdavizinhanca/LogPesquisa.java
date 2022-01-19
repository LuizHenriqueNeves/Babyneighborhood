package br.com.paisdavizinhanca.paisdavizinhanca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LogPesquisa extends Activity {

    String parametros = "";
    String url = "";
    private String nome;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            if (getNome().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
            } else {

                url = "http://10.60.191.188:4563/login/registrarGrupo.php";

                parametros = "&nome=" + getNome();

                new SolicitaDados().execute(url);
            }
        } else

        {
            Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
        }
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if (resultado.contains("grup_false")) {
                Toast.makeText(getApplicationContext(), "Grupo não cadastrado", Toast.LENGTH_LONG).show();

            } else {

                try {
                    JSONObject obj = new JSONObject(resultado);
                    JSONArray objArray = obj.getJSONArray("grupos");

                    List<Grupo> grupoList = new ArrayList<>();

                    Gson gson = new Gson();
                    for (int i = 0; i < objArray.length(); i++) {
                        JSONObject finalObject = objArray.getJSONObject(i);
                        Grupo grupoModelo = gson.fromJson(finalObject.toString(), Grupo.class);
                        grupoList.add(grupoModelo);
                    }
                    Intent intent = new Intent(LogPesquisa.this, GruposOnline.class);
                    //intent.putExtra("listaDeGrupos", new Gson().toJson(grupoList));
                    //startActivity(intent);
                    Toast.makeText(getApplicationContext(), (CharSequence) grupoList, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

