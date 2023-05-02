package com.cjz.wasif.trafficwarden;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class home extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private String driverFoundID;
    private Marker mDriverMarker;
    GoogleApiClient mGoogleApiClient;


    private DatabaseReference driverRefLocation;

    private ValueEventListener driverLocationRefListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        driverFoundID = getIntent().getStringExtra("Driver ID");

        Query tracking = FirebaseDatabase.getInstance().getReference().child("Tracking").orderByChild("DriverID").equalTo(driverFoundID);
        tracking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {

                    startActivity(new Intent(home.this, MainActivity.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        driverRefLocation = FirebaseDatabase.getInstance().getReference().child("Driver Working").child(driverFoundID).child("l");
        if (driverFoundID != null) {

            driverLocationRefListner = driverRefLocation.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        final List<Object> map = (List<Object>) dataSnapshot.getValue();
                        double locationLat = 0;
                        double locationLng = 0;

                        if (map.get(0) != null) {

                            locationLat = Double.parseDouble(map.get(0).toString());

                        }
                        if (map.get(1) != null) {

                            locationLng = Double.parseDouble(map.get(1).toString());

                        }

                        final LatLng driverLatlng = new LatLng(locationLat, locationLng);

                        if (mDriverMarker != null) {


                            mDriverMarker.remove();
                        }


                        Location loc2 = new Location("");
                        loc2.setLatitude(driverLatlng.latitude);
                        loc2.setLongitude(driverLatlng.longitude);


                        if (driverFoundID != null) {

                            // cancel_emergency.setVisibility(View.VISIBLE);
                            //cancel_emergency.setTextColor();
                        }


                        mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLatlng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ambulancen)));


                        // CameraPosition cameraPosition= new CameraPosition(driverLatlng,15f,15f,0f);
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLatlng,17));
                        //   CameraPosition cameraPosition= new CameraPosition(driverLatlng,15,0,0);
                        //  CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                        // CameraPosition cameraPosition= new CameraPosition.
                        // CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                        //  mMap.animateCamera(cameraUpdate);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLatlng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        if (ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setZoomControlsEnabled(true);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

            Toast.makeText(this, "No Tracking Found!", Toast.LENGTH_SHORT).show();

        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);


        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}