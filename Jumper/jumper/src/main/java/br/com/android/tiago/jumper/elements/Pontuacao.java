package br.com.android.tiago.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.android.tiago.jumper.engine.Som;
import br.com.android.tiago.jumper.graphic.Cores;

/**
 * Created by Tiago on 30/01/2018.
 */

public class Pontuacao {
    private static final Paint BRANCO = Cores.getCorDaPontuacao();
    private final Som som;
    private int pontos = 0;

    public Pontuacao(Som som) {
        this.som = som;
    }

    public void desenhaNo(Canvas canvas) {
        canvas.drawText(String.valueOf(pontos), 100, 100, BRANCO);
    }

    public void adicionaPonto() {
        som.toca(Som.PONTOS);
        pontos++;
    }
}
