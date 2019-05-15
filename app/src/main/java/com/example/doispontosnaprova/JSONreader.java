package com.example.doispontosnaprova;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JSONreader {

    public static List<Contato> leFaturasDeJSONString(String jsonString, List listaContato) {
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray contatos = json.getJSONArray("pessoas");
            String TAG = "LeFaturasDeJSONString";
            for (int i = 0; i < contatos.length(); i++) {
                JSONObject contato = contatos.getJSONObject(i);

                Contato c = new Contato(
                        contato.getString("nome"),
                        contato.getString("email"),
                        contato.getDouble("latitude"),
                        contato.getDouble("longitude")
                );
                Log.d(TAG, "leFaturasDeJSONString: Contato adicionado a lista");
                listaContato.add(c);
            }
        } catch (JSONException e) {
            System.err.println("Erro fazendo parse de String JSON: " + e.getMessage());
        }
        return listaContato;
    }

    public String downloadJson(String urlString) {
        String TAG = "DownloadJSON";
        StringBuilder jsonString = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int resposta = connection.getResponseCode();
            Log.e(TAG, "DownloadJSON: Código de resposta: " + resposta);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            int charsLidos;
            char[] InputBuffer = new char[500];
            while (true) {
                charsLidos = reader.read(InputBuffer);
                if (charsLidos < 0) {
                    break;
                }
                if (charsLidos > 0) {
                    jsonString.append(String.copyValueOf(InputBuffer, 0, charsLidos));
                }
            }
            reader.close();
            return jsonString.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "DownloadJSON: URL é inválido" + e.getMessage());

        } catch (IOException e) {
            Log.e(TAG, "DownloadJSON: Ocorreu um erro de IO ao baixar dados: " + e.getMessage());

        }
        return null;
    }

}
