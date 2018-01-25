package br.com.android.tiago.agendaIII;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.android.tiago.agendaIII.dao.AlunoDAO;
import br.com.android.tiago.agendaIII.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

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

        Button btnCamera = (Button) findViewById(R.id.formulario_btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";;
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
                // para funcionar no android 7 ou superior necessario realizar alterações conforme link de ajuda abaixo
                // https://cursos.alura.com.br/course/android-studio-ii-integracoes-e-recursos/task/23374
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                // Abrir a foto tirada
               helper.carregaImagem(caminhoFoto);
            }
        }
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
