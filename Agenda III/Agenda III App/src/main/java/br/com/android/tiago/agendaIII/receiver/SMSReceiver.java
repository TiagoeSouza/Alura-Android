package br.com.android.tiago.agendaIII.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.android.tiago.agendaIII.R;
import br.com.android.tiago.agendaIII.dao.AlunoDAO;

/**
 * Created by Tiago on 06/11/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS", Toast.LENGTH_SHORT).show();
            MediaPlayer mediaplayer = MediaPlayer.create(context, R.raw.msg);
            mediaplayer.start();
        }
    }
}
