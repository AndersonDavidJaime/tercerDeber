package com.example.tarea3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Resultado extends AppCompatActivity {
    String token = "";
    String url2 = "https://api.uealecpeterson.net/public/productos/search";
    TextView txtProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Bundle bundle = this.getIntent().getExtras();
        token = bundle.getString("token");
        txtProductos = findViewById(R.id.txtProductos);
        recursoVolley();

    }
    private void recursoVolley(){
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("respuesta", response);
                    try {
                        JSONObject objeto = new JSONObject(response);
                        JSONArray Jsonlista = objeto.getJSONArray("productos");
                        for (int i = 0; i < Jsonlista.length(); i++) {
                            JSONObject objetojson = Jsonlista.getJSONObject(i);
                            txtProductos.append("ID: "+objetojson.getString("id").toString() + "\n" +
                                           "DESCRIPCIÓN: "+objetojson.getString("descripcion") + "\n"+"\n");
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("eror", error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("Authorization", "Bearer " + token);
                    return headerMap;
                }
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("correo", "carlos@gmail.com");
                    headerMap.put("clave", "12345678");
                    headerMap.put("fuente", "1");
                    return headerMap;
                }
            };
            queue.add(stringRequest);
    }
}