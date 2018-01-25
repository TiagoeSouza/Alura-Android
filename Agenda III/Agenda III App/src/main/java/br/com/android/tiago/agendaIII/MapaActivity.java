package br.com.android.tiago.agendaIII;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;

public class MapaActivity extends AppCompatActivity {


    private static final int REQUEST_PERMISSOES = 1;
    private MapaFragment mapaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        /*FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        tx.replace(R.id.frame_mapa, new MapaFragment());
        tx.commit();*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissoes = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissoes, REQUEST_PERMISSOES);
            } else {
                showMapaFragment();
            }
        }
    }

    private void showMapaFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mapaFragment = new MapaFragment();
        transaction.replace(R.id.frame_mapa, mapaFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSOES) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                new Localizador(this, mapaFragment);
                showMapaFragment();
            }else{
                finish();
            }
        }
    }
}
