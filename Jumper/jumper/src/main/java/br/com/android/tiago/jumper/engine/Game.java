package br.com.android.tiago.jumper.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import br.com.android.tiago.jumper.elements.Passaro;

/**
 * Created by Tiago on 25/01/2018.
 */

public class Game extends SurfaceView implements Runnable {
    private boolean isRunning = true;
    private SurfaceHolder holder = getHolder();
    private Passaro passaro;

    public Game(Context context) {
        super(context);

        inicializaElementos();
    }

    private void inicializaElementos() {
        passaro = new Passaro();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!this.holder.getSurface().isValid()) {continue;}
            Canvas canvas = holder.lockCanvas();

            passaro.desenhaNo(canvas);
            passaro.cai();
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void inicia() {
        this.isRunning = true;
    }

    public void pausa() {
        this.isRunning = false;
    }
}
