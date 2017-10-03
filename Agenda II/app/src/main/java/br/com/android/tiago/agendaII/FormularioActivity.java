package br.com.android.tiago.agendaII;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.android.tiago.agendaII.dao.AlunoDAO;
import br.com.android.tiago.agendaII.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");


        if (aluno != null) {
            //Toast.makeText(FormularioActivity.this, aluno.getNome(), Toast.LENGTH_SHORT).show();
            helper.preencheFormulario(aluno);
        }
        /*Button btnSalvar = (Button) findViewById(R.id.formulario_btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(FormularioActivity.this, "Aluno Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // TODO: TESTE DE TODO
             case R.id.menu_formulario_ok:
                Aluno aluno = helper.getAluno();
                AlunoDAO alunoDao = new AlunoDAO(this);

                if (aluno.getId() != null) {
                    alunoDao.altera(aluno);
                } else {
                    alunoDao.insere(aluno);
                }
                alunoDao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
