package com.mira.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtDate, txtCity, txtTemp, txtMinTemp, txtMaxTemp, txtTempFL, txtPressure, txtHumidity, txtSeaLevel, txtGroundLevel;
    ImageView imgIcon;
    String myCity="Toronto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDate=findViewById(R.id.txtDate);
        txtCity=findViewById(R.id.txtCity);
        txtTemp=findViewById(R.id.txtTemp);
        txtMinTemp=findViewById(R.id.txtMinTemp);
        txtMaxTemp=findViewById(R.id.txtMaxTemp);
        txtTempFL=findViewById(R.id.txtTempFL);
        txtPressure=findViewById(R.id.txtPressure);
        txtHumidity=findViewById(R.id.txtHumidity);
        txtSeaLevel=findViewById(R.id.txtSeaLevel);
        txtGroundLevel=findViewById(R.id.txtGroundLevel);

        afficher();
    }

    public void afficher()
    {
        String url="http://api.openweathermap.org/data/2.5/weather?q=Toronto&appid=9e06f9d4ef3186688fb86beca4cf51b6&units=metric";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray array=response.getJSONArray("weather");
                    Log.d("weather", "resultat ="+array.toString());
                    Log.d("main", "resultat ="+main_object.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}