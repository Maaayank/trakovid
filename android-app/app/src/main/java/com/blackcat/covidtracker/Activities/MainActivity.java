package com.blackcat.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.covidtracker.Models.World;
import com.blackcat.covidtracker.NetworkHelpers.RetrofitInstance;
import com.blackcat.covidtracker.NetworkHelpers.ServiceInterfaces.ServiceWorld;
import com.blackcat.covidtracker.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        ServiceWorld service = retrofit.create(ServiceWorld.class);

        Call<World> call = service.getTotal();

        call.enqueue(new Callback<World>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<World> call,@NonNull Response<World> response) {

                if (response.isSuccessful()) {

                    World world = response.body();

                    if(world != null ) {
                        ((TextView) findViewById(R.id.tconfirmed)).setText("Total Confirmed : " + world.gettConfirmed());
                        ((TextView) findViewById(R.id.trecovered)).setText("Total Recovered : " + world.gettRecovered());
                        ((TextView) findViewById(R.id.tdeaths)).setText("Total Deaths : " + world.gettDeaths());

                        ((TextView) findViewById(R.id.nconfirmed)).setText("New Confirmed : " + world.getnConfirmed());
                        ((TextView) findViewById(R.id.nrecovered)).setText("New Recovered : " + world.getnRecovered());
                        ((TextView) findViewById(R.id.ndeaths)).setText("New Deaths : " + world.getnDeaths());
                    }else{
                        Toast.makeText(MainActivity.this, "Null Response ", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Response unsuccessful" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull  Call<World> call,@NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Network error ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
