package br.com.android.tiago.jumper.engine;

import br.com.android.tiago.jumper.elements.Canos;
import br.com.android.tiago.jumper.elements.Passaro;

/**
 * Created by Tiago on 30/01/2018.
 */

class VerificadorDeColisao {
    private Canos canos;
    private Passaro passaro;

    public VerificadorDeColisao(Passaro passaro, Canos canos) {
        this.passaro = passaro;
        this.canos = canos;

    }

    public boolean temColisao() {
        return canos.temColisaoCom(passaro);
    }
}
