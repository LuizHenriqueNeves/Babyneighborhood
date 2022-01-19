package br.com.paisdavizinhanca.paisdavizinhanca;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class PesquisarGrupo extends Fragment {

    EditText nomeGrupo;

    public PesquisarGrupo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisar_grupo,
                container, false);

        nomeGrupo = view.findViewById(R.id.editNomeGrupo);

        String nome = nomeGrupo.getText().toString();
        LogPesquisa logPesquisa = new LogPesquisa();
        logPesquisa.setNome(nome);

        //Intent abreInicio = new Intent(getActivity(), LogPesquisa.class);
        //abreInicio.putExtra("nomeGrupo", nome);
        //startActivity(abreInicio);
        return inflater.inflate(R.layout.fragment_pesquisar_grupo,container,false);

    }
}

