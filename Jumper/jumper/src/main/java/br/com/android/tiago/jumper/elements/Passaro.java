package br.com.android.tiago.jumper.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.android.tiago.jumper.R;
import br.com.android.tiago.jumper.engine.Som;
import br.com.android.tiago.jumper.graphic.Cores;
import br.com.android.tiago.jumper.graphic.Tela;

/**
 * Created by Tiago on 25/01/2018.
 */

public class Passaro {

    public static final float X = 100;
    public static final int RAIO = 50;
    private static final Paint VERMELHO = Cores.getCorDoPassaro();
    private final Tela tela;
    private Bitmap passaroBp;
    private float altura;
    private Som som;

    public Passaro(Tela tela, Context context, Som som) {
        this.som = som;
        this.altura = 100;
        this.tela = tela;
        passaroBp = BitmapFactory.decodeResource(context.getResources(), R.drawable.passaro);
        this.passaroBp = Bitmap.createScaledBitmap(passaroBp, RAIO * 2, RAIO * 2, false);
    }


    public void desenhaNo(Canvas canvas) {

        //canvas.drawCircle(X, altura, RAIO, VERMELHO);

        canvas.drawBitmap(passaroBp, X - RAIO, altura - RAIO, null);
    }


    public void cai() {
        boolean chegouNoChao = (altura + RAIO) > tela.getAltura();
        if (!chegouNoChao) {
            this.altura += 5;
        }
    }

    public void pula() {
        if (altura - RAIO > 0) {
            som.toca(Som.PULO);
            this.altura -= 150;
        }
    }

    public float getAltura() {
        return this.altura;
    }
}
