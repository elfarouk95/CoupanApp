package com.coupanapp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GpsTracker extends Service implements LocationListener {
    private final Context mContext;

    private static GpsTracker gpsTracker;
    // Is the GPS on?
    boolean isGPSEnabled = false;

    // Is the data connection active on the device?
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    // Location
    Location location;
    // Latitude
    double latitude;
    // Longitude
    double longitude;

    // Minimum amount of change that will require location update
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // metre

    // Minimum amount of time required to update location
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // dakika

    // LocationManager object
    protected LocationManager locationManager;

    //
    // Founder Method - Constructor
    //
    public GpsTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    //
    // Freeze location information
    //
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // GPS acik what?
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Is the Internet open?
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true;

                // Once the location information from the internet is saved
                if (isNetworkEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // location information from GPS;
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    // Freeze Latitude information
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    // Freezes the longitude information
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    // If position information is off, a message will be displayed to the user with a link to the settings page
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Message title
        alertDialog.setTitle(" GPS Off ");

        // Message
        alertDialog.setMessage(" Cannot get location information. Go to settings and activate gps. ");

        // Message icon
        // alertDialog.setIcon (R.drawable.delete);

        // When the Settings button is clicked
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // When the Cancel button is clicked
        alertDialog.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show message box
        alertDialog.show();
    }

    // Stops LocationManager's gps requests
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }
}