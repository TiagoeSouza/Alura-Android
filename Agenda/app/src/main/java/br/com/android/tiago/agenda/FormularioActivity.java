package br.com.android.tiago.agenda;

import android.net.sip.SipAudioCall;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Button btnSalvar = (Button) findViewById(R.id.formulario_btnSalvar);

       btnSalvar.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               Toast.makeText(FormularioActivity.this,"Aluno Salvo com Sucesso!",Toast.LENGTH_SHORT).show();
           }
       });
    }
}
