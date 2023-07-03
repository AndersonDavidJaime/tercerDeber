package com.example.tarea3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String url1 = "https://api.uealecpeterson.net/public/login";
    String usuario = "carlos@gmail.com";
    String pasword = "12345678";
    String fuente = "1";
    EditText et_correo;
    EditText et_clave;

    Map<String, String> body;

    JSONObject objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        body = new HashMap<>();
        et_correo = (EditText)findViewById(R.id.et_correo);
        et_clave = (EditText)findViewById(R.id.et_clave);
        objeto = new JSONObject();
        Button btnAcceder = (Button) findViewById(R.id.btnAcceder);
    }
    public void enviar(View view){
            RecursoVolley();
    }
    private void RecursoVolley() {
        usuario = et_correo.getText().toString();
        pasword = et_clave.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject objeto = new JSONObject(response);
                        Intent intent = new Intent(MainActivity.this, Resultado.class);
                        Bundle b = new Bundle();
                        b.putString("token", objeto.getString("access_token"));
                        intent.putExtras(b);
                        startActivity(intent);
                } catch (JSONException e) {
                    //throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "datos incorrectos",
                        Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                body.put("correo", usuario);
                body.put("clave", pasword);
                body.put("fuente", fuente);
                return body;
            }
        };
        queue.add(stringRequest);
    }

}