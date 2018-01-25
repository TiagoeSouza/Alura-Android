package br.com.android.tiago.agendaIII.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.android.tiago.agendaIII.converter.AlunoConverter;
import br.com.android.tiago.agendaIII.dao.AlunoDAO;
import br.com.android.tiago.agendaIII.modelo.Aluno;
import br.com.android.tiago.agendaIII.web.WebClient;

/**
 * Created by Tiago on 07/11/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos ....", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String response = client.post(json);

//        Toast.makeText(context, "Enviando Notas..." + response, Toast.LENGTH_LONG).show();
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        progressDialog.dismiss();;
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

    }
}
