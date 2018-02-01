package br.com.android.tiago.jumper.elements;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.android.tiago.jumper.graphic.Tela;

/**
 * Created by Tiago on 29/01/2018.
 */

public class Canos {
    private static final int QUANTIDADE_DE_CANOS = 5;
    private static final int DISTANCIA_ENTRE_CANOS = 200;
    private final List<Cano> canos = new ArrayList<Cano>();
    private Tela tela;
    private Pontuacao pontuacao;
    private Context context;

    public Canos(Tela tela, Pontuacao pontuacao, Context context) {
        this.tela = tela;
        this.pontuacao = pontuacao;
        this.context = context;
        int posicao = 400;

        for (int i = 0; i < QUANTIDADE_DE_CANOS; i++) {
            posicao += DISTANCIA_ENTRE_CANOS;
            Cano cano = new Cano(tela, posicao,context);
            canos.add(cano);
        }
    }

    public void desenhaNo(Canvas canvas) {
        for (Cano cano : canos) {
            cano.desenhaNo(canvas);
        }
    }

    public void move() {
        ListIterator<Cano> interator = canos.listIterator();
        while (interator.hasNext()) {
//            for (Cano cano : canos) {
            Cano cano = interator.next();
            cano.move();
            if (cano.saiuDaTela()) {
                pontuacao.adicionaPonto();
                interator.remove();
                // criar outro cano
                Cano outroCano = new Cano(tela, getMaximo() + DISTANCIA_ENTRE_CANOS,context);
                interator.add(outroCano);
            }
//            }
        }
    }

    private int getMaximo() {
        int maximo = 0;
        for (Cano cano : canos) {
            maximo = Math.max(cano.getPosicao(), maximo);
        }
        return maximo;
    }

    public boolean temColisaoCom(Passaro passaro) {
        for (Cano cano : canos) {
            if(cano.temColisaoHorizontalCon(passaro)
                    && cano.temColisaoVertivalCon(passaro)){
                return true;
            }

        }
        return false;
    }
}
