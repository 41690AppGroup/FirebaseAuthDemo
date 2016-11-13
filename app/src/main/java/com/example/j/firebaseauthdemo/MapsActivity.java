package com.example.j.firebaseauthdemo;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mMap;
    private static final double
            Home_lat = 53.30766,
            Home_long = -6.22506;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener mListener;
    Circle shape;
    double currentlat, currentlong;

    LatLng home = new LatLng(Home_lat, Home_long);
    LatLng currenLoc = new LatLng(currentlat,currentlong);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



        if (mMap != null)
            Toast.makeText(this, "The map is connected", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Map not avaialble", Toast.LENGTH_SHORT).show();


        getCurrentLocation();

        mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Toast.makeText(MapsActivity.this, "The loc changes are" + location.getLatitude() + "and" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                gotoLocation(location.getLatitude(), location.getLongitude(),17);
            }
        };

        locationRequester();


    }

    private void locationRequester() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(50000);
        request.setFastestInterval(50000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, mListener);
        final float[] results = new float[1];
        Location.distanceBetween(Home_lat, Home_long,currentlat,currentlong, results);
            String str = Float.toString(results[0]/1000);
            Toast.makeText(MapsActivity.this,"The distance is "+str+"KM",Toast.LENGTH_SHORT).show();


            if((results[0]/1000)>1)
                Toast.makeText(MapsActivity.this, "Saftey Breach",Toast.LENGTH_SHORT).show();

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        currentlat = currentLocation.getLatitude();
        currentlong = currentLocation.getLongitude();
        gotoLocationAnimate(currentlat,currentlong,15);
    }

    private  void gotoLocation(double lat, double lng,float zoom)
    {
        LatLng latLng = new LatLng(lat,lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));


    }

    private  void gotoLocationAnimate(double lat, double lng,float zoom)
    {
        LatLng latLng = new LatLng(lat,lng);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currenLoc) // Sets the center of the map to
                .zoom(15)                   // Sets the zoom
                .bearing(1) // Sets the orientation of the camera to east
                .tilt(90)    // Sets the tilt of the camera to 30 degrees
                .build();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));


    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


        mMap.addMarker(new MarkerOptions().position(home).title("This is home"));
        CircleOptions circleOpt = new CircleOptions()
                .strokeWidth(3)
                .fillColor(0x330000ff)
                .strokeColor(Color.BLUE)
                .center(home)
                .radius(100);
        shape = mMap.addCircle(circleOpt);

        //mMap.setPadding(30,0,0,0);    => this would be useful when we want to pad the map if we have menu , so that map stays centre irrespective of the menu
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,mListener);
    }
}
