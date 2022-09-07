package com.example.e_commerceapp;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
public class mylocationlistener implements LocationListener {
    private Context con;
    public mylocationlistener(Context activity) {
        con=activity;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(con,location.getAltitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(con,"GPS Enable",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(con,"GPS disable",Toast.LENGTH_SHORT).show();
    }
}
