package com.mira.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView txtDate, txtCity, txtTemp, txtMinTemp, txtMaxTemp, txtTempFL, txtPressure, txtHumidity, txtVisibility, txtDescription;
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
        txtDescription=findViewById(R.id.txtDescription);
        txtVisibility=findViewById(R.id.txtVisibility);
        imgIcon=findViewById(R.id.imgIcon);

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
                    // Log.d("weather", "resultat ="+array.toString());
                    // Log.d("main", "resultat ="+main_object.toString());
                    JSONObject object=array.getJSONObject(0);

                    //temp
                    int tempC=(int)Math.round(main_object.getDouble("temp"));
                    String temp=String.valueOf(tempC);

                    int temp_FL=(int)Math.round(main_object.getDouble("feels_like"));
                    String tempFL=String.valueOf(temp_FL);

                    int temp_min=(int)Math.round(main_object.getDouble("temp_min"));
                    String tempMin=String.valueOf(temp_min);

                    int temp_max=(int)Math.round(main_object.getDouble("temp_max"));
                    String tempMax=String.valueOf(temp_max);

                    // description, pressure, humidity, city, icon
                    String description=object.getString("description");
                  //  String pressure=object.getString("pressure");
                    String humidity=object.getString("humidity");
                    String visibility=object.getString("visibility");
                    String city=response.getString("name");
                    String icon=object.getString("icon");

                    txtCity.setText(city);
                    txtTemp.setText(temp+"°C");
                    txtMinTemp.setText("Min: "+tempMin+"°C");
                    txtMaxTemp.setText("Max: "+tempMax+"°C");
                    txtTempFL.setText("Feels like: "+tempFL+"°C");
                    txtDescription.setText(description);
                   // txtPressure.setText("Pressure: "+pressure+" mbar");
                    txtHumidity.setText("Humidity: "+humidity+"%");
                    txtVisibility.setText("Visibility: "+visibility+" m");

                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE, MMMM dd");
                    String formatted_date=simpleDateFormat.format(calendar.getTime());

                    txtDate.setText(formatted_date);

                    //image
                    String imageUri="http://openweathermap.org/img/w/"+ icon+"png";
                    imgIcon=findViewById(R.id.imgIcon);
                    Uri myUri=Uri.parse(imageUri);
                    Picasso.with(MainActivity.this).load(myUri).resize(200, 200).into(imgIcon);

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