package br.com.android.tiago.agendaIII;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.android.tiago.agendaIII.adapter.AlunosAdapter;
import br.com.android.tiago.agendaIII.dao.AlunoDAO;
import br.com.android.tiago.agendaIII.modelo.Aluno;
import br.com.android.tiago.agendaIII.tasks.EnviaAlunosTask;

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

                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                break;

            case R.id.menu_baixar_provas:
                Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;

            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa );
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void carregaLista() {
        AlunoDAO alunoDao = new AlunoDAO(this);
        List<Aluno> alunos = alunoDao.buscaAlunos();
        alunoDao.close();

        //String[] alunos = {"Tiago", "Karolina", "Nick", "Paulo", "Fernando", "Fernanda"};
//        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.activity_list_item, alunos);
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

//        Não funciona por conta das permissões do android 5.0 acima, então é necessário antes da ação solicitar prermissão ao usuário, conforme abaixo
//        Intent intentChamada = new Intent(Intent.ACTION_CALL);
//        intentChamada.setData(Uri.parse("tel:" + aluno.getTelefone()));
//        itemChamada.setIntent(intentChamada);
        MenuItem itemChamada = menu.add("Ligar");
        itemChamada.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentChamada = new Intent(Intent.ACTION_CALL);
                    intentChamada.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentChamada);
                }
                return false;

            }
        });


        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemSite = menu.add("Visitar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("http://")) {
            if (!site.startsWith("https://")) {
                site = "http://" + aluno.getSite();
            }
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()

        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO alunoDao = new AlunoDAO(ListaAlunosActivity.this);
                alunoDao.deleta(aluno);
                alunoDao.close();
                carregaLista();

                Toast.makeText(ListaAlunosActivity.this, String.format("O Aluno: %1$s, foi deletado com %2$s!", aluno.getNome(), "sucesso"), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // No cenario atual temos que solicitar a permissão do usuário para realizar a chamada telefonica.
//        // E após o mesmo ter permitido o acesso, é necessário que o usuário selecione a opção de ligar novamente, para conseguir reconhecer a permissão atualizada.
//
//        if (requestCode == 123) // Exemplo, provavelmente não está correto o Code para chamada
//        {
//            // Faz a chamada telefonica
//        } else if (requestCode == 124) {
//            // faz envio do SMS
//        }
//
//    }
}
