package br.com.android.tiago.agendaIII;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

import br.com.android.tiago.agendaIII.dao.AlunoDAO;
import br.com.android.tiago.agendaIII.modelo.Aluno;

/**
 * Created by Tiago on 16/01/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;  // guardamos o mapa
        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Rua Vergueiro 3185, Vila Mariana, São Paulo");
        if (posicaoDaEscola != null) {
            /*CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 17);
            googleMap.addMarker(new MarkerOptions().position(posicaoDaEscola).title("Caelum - Alura"));
            googleMap.moveCamera(update);*/

            centralizaEm(posicaoDaEscola, false);  // usamos um método aqui agora
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()) {
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada).title(aluno.getNome()).snippet(aluno.getNota().toString());

                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();


        new Localizador(getContext(), MapaFragment.this);
    }

    public void centralizaEm(LatLng coordenada, boolean localizador) {
        if (mapa != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            mapa.moveCamera(update);

            if (localizador) {
//                mapa.addMarker(new MarkerOptions()
//                        .position(coordenada)
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mapa.addCircle(new CircleOptions()
                        .center(coordenada).radius(5)
                        .strokeColor(Color.RED).strokeWidth(1).fillColor(Color.RED));
            } else {
                mapa.addCircle(new CircleOptions()
                        .center(coordenada).radius(50)
                        .strokeColor(Color.RED).strokeWidth(1).fillColor(Color.DKGRAY));
            }
        }
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);

            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
