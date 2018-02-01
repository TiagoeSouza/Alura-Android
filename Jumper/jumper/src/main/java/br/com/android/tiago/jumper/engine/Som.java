package br.com.android.tiago.jumper.engine;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import br.com.android.tiago.jumper.R;

/**
 * Created by Tiago on 31/01/2018.
 */

public class Som {
    private SoundPool soundPool;

    public static int PULO;
    public static int COLISAO;
    public static int PONTOS;



    public Som(Context context) {
        if(android.os.Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(3);
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        PULO = soundPool.load(context, R.raw.pulo, 1);
        COLISAO = soundPool.load(context, R.raw.colisao, 1);
        PONTOS = soundPool.load(context, R.raw.pontos, 1);

    }

    public void toca(int som) {
        soundPool.play(som, 1, 1, 1, 0, 1);

    }
}
