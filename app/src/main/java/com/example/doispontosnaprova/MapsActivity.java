package com.example.doispontosnaprova;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Contato> listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public class LerJson extends AsyncTask<String, Void, String> {
        JSONreader jsonReader = new JSONreader();
        String TAG = "DoInBackground";
        String json;

        @Override
        protected String doInBackground(String... strings) {
            json = jsonReader.downloadJson(strings[0]);
            if (json == null) {
                Log.e(TAG, "doInBackground: Erro baixando JSON");
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String jsonString = json;
            jsonReader.leFaturasDeJSONString(jsonString, listaContatos);

            if (listaContatos.size() == 0) {
                Log.d(TAG, "onMapReady: Lista vazia!");
            }
            imprimirNoMapa();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        listaContatos = new ArrayList<>();
        String TAG = "MainActivity";
        LerJson readJson = new LerJson();
        readJson.execute("http://www.mocky.io/v2/5cdb4544300000640068cc7b");

    }

    public void imprimirNoMapa() {
        String TAG = "ImprimirNoMapa";
        Log.d(TAG, "onMapReady: Tamanho da lista = " + listaContatos.size());

        for (int i = 0; i < listaContatos.size(); i++) {
            Log.d(TAG, "onMapReady: " + listaContatos.get(i).getLatitude());
            LatLng loc = new LatLng(listaContatos.get(i).getLatitude(), listaContatos.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(listaContatos.get(i).getNome()));
        }
    }
}
