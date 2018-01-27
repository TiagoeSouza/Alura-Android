package br.com.android.tiago.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.android.tiago.jumper.graphic.Cores;

/**
 * Created by Tiago on 25/01/2018.
 */

public class Passaro {

    private static final float X = 100;
    private static final float RAIO = 50;
    private static final Paint VERMELHO = Cores.getCorDoPassaro();
    private float altura;

    public Passaro() {
        this.altura = 100;

    }


    public void desenhaNo(Canvas canvas) {
        canvas.drawCircle(X, altura, RAIO, VERMELHO);
    }


    public void cai() {
        this.altura += 5;
    }
}
