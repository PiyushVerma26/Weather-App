package com.example.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etCity;
    TextView evCity,evTemp,tvHumidity,tvPressure,tvVisible,tvWind;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity=findViewById(R.id.etCity);
        evCity=findViewById(R.id.evCity);
        evTemp=findViewById(R.id.evTemp);
        tvHumidity=findViewById(R.id.tvHumidity);
        tvPressure=findViewById(R.id.tvPressure);
        tvVisible=findViewById(R.id.tvVisible);
        tvWind=findViewById(R.id.tvWind);
        btn=findViewById(R.id.btnSearch);
        btn.setOnClickListener(this::search);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case
                    R.id.home:
                Toast.makeText(this,"Home Is Clicked",Toast.LENGTH_LONG).show();
                break;
            case
                    R.id.setting:
                Toast.makeText(this,"Setting Is Clicked",Toast.LENGTH_LONG).show();
                break;
            case
                    R.id.search:
                Toast.makeText(this,"Search Is Clicked",Toast.LENGTH_LONG).show();
                break;
            case
                    R.id.help: Toast.makeText(this,"Help And Feedback Is Clicked",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }

    public void search(View view) {
        String Apikey="20bb1096d3bec4bcfdd08ea41a47c7b7";
        String city = etCity.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+Apikey;
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        @SuppressLint("SetTextI18n") JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject object=response.getJSONObject("main");

                String temperature=object.getString("temp");
                double temprate=Double.parseDouble(temperature)-273.15;
                evTemp.setText(Double.toString(temprate).substring(0,5)+ " °C");

                String humidity=object.getString("humidity");
                tvHumidity.setText(humidity);

                String pressure=object.getString("pressure");
                tvPressure.setText(pressure);

                String cityName=response.getString("name");
                evCity.setText(cityName);

                String visibility=response.getString("visibility");
                tvVisible.setText(visibility);

                JSONObject windObj=response.getJSONObject("wind");
                String speed=windObj.getString("speed");
                tvWind.setText(speed);

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }, error -> Toast.makeText(MainActivity.this,"Please Check The City Name",Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}