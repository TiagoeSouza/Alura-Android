package br.com.android.tiago.jumper.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import br.com.android.tiago.jumper.R;
import br.com.android.tiago.jumper.elements.Cano;
import br.com.android.tiago.jumper.elements.Canos;
import br.com.android.tiago.jumper.elements.GameOver;
import br.com.android.tiago.jumper.elements.Passaro;
import br.com.android.tiago.jumper.elements.Pontuacao;
import br.com.android.tiago.jumper.graphic.Tela;

/**
 * Created by Tiago on 25/01/2018.
 */

public class Game extends SurfaceView implements Runnable, OnTouchListener {
    private final Som som;
    private boolean isRunning = true;
    private SurfaceHolder holder = getHolder();
    private Passaro passaro;
    private Canvas canvas;
    private Bitmap background;
    private Tela tela;
    private Context context;
    //    private Cano cano;
    private Canos canos;
    private Pontuacao pontuacao;

    public Game(Context context) {
        super(context);
        som = new Som(context);
        tela = new Tela(context);
        this.context = context;
        inicializaElementos();

        setOnTouchListener(this);
    }

    private void inicializaElementos() {

        passaro = new Passaro(tela, context, som);
//        cano = new Cano(tela,400);
        pontuacao = new Pontuacao(som);
        canos = new Canos(tela, pontuacao, context);
        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        background = Bitmap.createScaledBitmap(back, back.getWidth(), tela.getAltura(), false);

    }

    @Override
    public void run() {
        while (true) {
            while (isRunning) {
                if (!this.holder.getSurface().isValid()) {
                    continue;
                }
                canvas = holder.lockCanvas();
                canvas.drawBitmap(background, 0, 0, null);
                passaro.desenhaNo(canvas);
                passaro.cai();

//            cano.desenhaNo(canvas);
//            cano.move();

                canos.desenhaNo(canvas);
                pontuacao.desenhaNo(canvas);

                if (new VerificadorDeColisao(passaro, canos).temColisao()) {
                    som.toca(Som.COLISAO);
                    new GameOver(tela).desenhaNo(canvas);
                    isRunning = false;
                }
                canos.move();

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void inicia() {
        this.isRunning = true;
    }

    public void pausa() {
        this.isRunning = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isRunning) {
            passaro.pula();
        } else {
            inicializaElementos();
            inicia();
        }
        return false;
    }
}
