package com.example.e_commerceapp;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import java.io.IOException;
import java.util.List;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    mylocationlistener myloc;
    LocationManager locationManager;
    TextView location;
    Button btn;
    Button buy;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myloc=new mylocationlistener(getApplicationContext());
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,6000,0,myloc);
        }catch (SecurityException ex)
        {
            Toast.makeText(getApplicationContext(),"not allowed access current location",Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //move camera to cairo
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
       //access edittexts from xml
        btn=(Button)findViewById(R.id.map_btn);
        buy=(Button)findViewById(R.id.buy_order_btn);
        location=(TextView)findViewById(R.id.location_by_map);
        final EditText credit=(EditText) findViewById(R.id.credit);
        Intent intent=getIntent();
        //get data from intent
        final DatabaseHelper db=new DatabaseHelper(this);
        final int user_id=intent.getIntExtra("id",0);
        final double price=intent.getDoubleExtra("price",00);
        final String date=intent.getStringExtra("date");
        //get current location click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc=null;
                //permission is denied
                try {
                    loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(),"Not allowed access current location, " +
                            "Edit application permissions",Toast.LENGTH_SHORT).show();
                }
                if(loc!=null)
                {
                    LatLng myposition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try
                    {
                        //store location
                        addressList=geocoder.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            String address="";
                            Fragment fragment= new CartFragment();
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            {
                                address+=addressList.get(0).getAddressLine(i)+",";
                                mMap.addMarker(new MarkerOptions().position(myposition).title("My location").snippet(address)).setDraggable(true);
                                location.setText(address);
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        mMap.addMarker(new MarkerOptions().position(myposition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determine",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //buy order button click
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that credit id =16 and location is not null
                if(credit.length()==16 &&!location.getText().toString().isEmpty())
                {
                    Intent intent=new Intent(MapsActivity.this,ConfirmActivity.class);
                    //delete this ordre from orders tabls
                    db.delete_user_orders(user_id);
                    //add this order to paid orders
                    db.store_order_details_after_submit(user_id,credit.getText().toString(),price,date,location.getText().toString());
                    intent.putExtra("id",user_id);
                    intent.putExtra("price",price);
                    intent.putExtra("address",location.getText().toString());
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Correct Credit Card",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                location=(TextView)findViewById(R.id.location_by_map);
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address>addressList;
                try
                {
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        String address="";
                        for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                        {
                            address+=addressList.get(0).getAddressLine(i)+",";
                            location.setText(address);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_SHORT).show();
                        location.getText();
                    }
                }
                catch (IOException ex)
                {
                    Toast.makeText(getApplicationContext(),"can't get your address,check your network",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
            }
        });
    }
}