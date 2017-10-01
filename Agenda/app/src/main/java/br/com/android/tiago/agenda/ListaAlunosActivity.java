package br.com.android.tiago.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.android.tiago.agenda.dao.AlunoDAO;
import br.com.android.tiago.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
               // Toast.makeText(ListaAlunosActivity.this, String.format("O Aluno: %1$s, clicado!",aluno.getNome()), Toast.LENGTH_SHORT).show();

                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);

            }
        });

        /*listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListaAlunosActivity.this, String.format("Click Long!"), Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        Button novoaluno = (Button) findViewById(R.id.novo_aluno);

        novoaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        AlunoDAO alunoDao = new AlunoDAO(this);
        List<Aluno> alunos = alunoDao.buscaAlunos();
        alunoDao.close();

        //String[] alunos = {"Tiago", "Karolina", "Nick", "Paulo", "Fernando", "Fernanda"};
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                AlunoDAO alunoDao = new AlunoDAO(ListaAlunosActivity.this);
                alunoDao.deleta(aluno);
                alunoDao.close();
                carregaLista();

                Toast.makeText(ListaAlunosActivity.this, String.format("O Aluno: %1$s, foi deletado com %2$s!",aluno.getNome(),"sucesso"), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
