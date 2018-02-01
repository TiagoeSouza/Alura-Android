package br.com.android.tiago.jumper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import br.com.android.tiago.jumper.engine.Game;

public class MainActivity extends Activity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        this.game = new Game(this);
        container.addView(this.game);

    }

    @Override
    protected void onResume() {
        super.onResume();
        game.inicia();
        new Thread(this.game).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.game.pausa();
    }
}
