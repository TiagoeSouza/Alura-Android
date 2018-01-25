package br.com.android.tiago.agendaIII.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.android.tiago.agendaIII.DetalhesProvaActivity;
import br.com.android.tiago.agendaIII.ProvasActivity;
import br.com.android.tiago.agendaIII.R;
import br.com.android.tiago.agendaIII.modelo.Prova;

/**
 * Created by Tiago on 15/11/2017.
 */

public class ListaProvasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Português", "25/05/2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equações de segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matemática", "27/05/2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();


                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);

                /*Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova",prova);
                startActivity(vaiParaDetalhes);*/
            }
        });

        return view;
    }
}
