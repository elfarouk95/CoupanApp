package com.coupanapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coupanapp.R;
import com.coupanapp.databinding.ActivityMainBinding;
import com.onesignal.OneSignal;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gpsTracker = new GpsTracker(this);
        //  gpsTracker.showSettingsAlert();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String id = Util.getStringPref("id", "-1", this);
        if (!id.equals("-1")) {
            Intent n = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(n);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.Address.setText(getAddress(gpsTracker, this));
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.Address.setText(getAddress(gpsTracker, MainActivity.this));
            }
        }, 1000);

    }

    public void Goto(View view) {
        Api.getClient().add(binding.email.getText().toString(),
                binding.Phone.getText().toString()
                , binding.Address.getText().toString()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.body() != null)
                    Util.setStringPref("id", response.body().toString(), MainActivity.this);
                Intent n = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(n);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }

    public static String getAddress(GpsTracker gpsTracker, Context context) {
        try {

            Geocoder geo = new Geocoder(context, Locale.getDefault());
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                // gpsTracker.showSettingsAlert();
                return ("");

            } else {
                if (addresses.size() > 0) {
                    return (addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
        return "";
    }

    public void skip(View view) {
        Intent n = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(n);
    }
}
