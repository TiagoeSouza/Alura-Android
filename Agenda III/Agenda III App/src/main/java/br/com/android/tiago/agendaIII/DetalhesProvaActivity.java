package br.com.android.tiago.agendaIII;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.android.tiago.agendaIII.modelo.Prova;

public class DetalhesProvaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_provas);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView materia = (TextView) findViewById(R.id.detalhes_prova_materia);
        TextView data = (TextView) findViewById(R.id.detalhes_prova_data);
        ListView listaTopicos = (ListView) findViewById(R.id.detalhes_prova_topicos);


        materia.setText(prova.getMateria());
        data.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);
    }
}
