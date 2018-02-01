package br.com.android.tiago.jumper.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.android.tiago.jumper.R;
import br.com.android.tiago.jumper.graphic.Cores;
import br.com.android.tiago.jumper.graphic.Tela;

/**
 * Created by Tiago on 29/01/2018.
 */

public class Cano {
    private static final Paint VERDE = Cores.getCorDoCano();
    private static final int LARGURA_DO_CANO = 100;
    private final Tela tela;
    private static final int TAMANHO_DO_CANO = 250;
    private final int alturaDoCanoInferior;
    private final Bitmap canoInferior;
    private final Bitmap canoSuperior;
    private Context context;
    private int posicao;
    private int alturaDoCanoSuperior;


    public Cano(Tela tela, int posicao, Context context) {
        this.tela = tela;
        this.posicao = posicao;
        alturaDoCanoInferior = tela.getAltura() - TAMANHO_DO_CANO - valorAleatorio();
        this.context = context;
        alturaDoCanoSuperior = 0 + TAMANHO_DO_CANO + valorAleatorio();
        Bitmap canoBp = BitmapFactory.decodeResource(context.getResources(), R.drawable.cano);
        canoInferior = Bitmap.createScaledBitmap(canoBp, LARGURA_DO_CANO, alturaDoCanoInferior, false);

        canoSuperior = Bitmap.createScaledBitmap(canoBp, LARGURA_DO_CANO, alturaDoCanoSuperior, false);

    }

    private int valorAleatorio() {
        return (int) (Math.random() * 150);
    }

    public void desenhaNo(Canvas canvas) {
        desenhaCanoSuperiorNo(canvas);
        desenhaCanoInferiorNo(canvas);
    }

    private void desenhaCanoInferiorNo(Canvas canvas) {
//        canvas.drawRect(posicao, alturaDoCanoInferior, posicao + LARGURA_DO_CANO, tela.getAltura(), VERDE);
        canvas.drawBitmap(canoInferior, posicao, alturaDoCanoInferior, null);
    }

    private void desenhaCanoSuperiorNo(Canvas canvas) {
//        canvas.drawRect(posicao, 0, posicao + LARGURA_DO_CANO, alturaDoCanoSuperior, VERDE);
        canvas.drawBitmap(canoSuperior, posicao, 0, null);
    }


    public void move() {
        this.posicao -= 5;
    }

    public boolean saiuDaTela() {
        return (posicao + LARGURA_DO_CANO < 0);
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean temColisaoHorizontalCon(Passaro passaro) {
        return this.posicao < passaro.X + passaro.RAIO;
    }

    public boolean temColisaoVertivalCon(Passaro passaro) {
        return passaro.getAltura() - passaro.RAIO < this.alturaDoCanoSuperior
                || passaro.getAltura() + passaro.RAIO > this.alturaDoCanoInferior;
    }
}
