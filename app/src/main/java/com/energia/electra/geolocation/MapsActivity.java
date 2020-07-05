package com.energia.electra.geolocation;


import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMapLongClickListener, View.OnClickListener {

    private GoogleMap _mapa; // Might be null if Google Play services APK is not available.
    private Marker _marker;
    private SharedPreferences _preferencias;
    private LatLng _carPosition;
    private Button _carButton;


    @Override
    public void onMapLongClick(LatLng point) {
        if (_marker != null) {
            _marker.remove();
        }
        makeMarker(point);
        StorePreferenceString(point);
        _carPosition = point;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        _carButton = (Button) findViewById(R.id.buttons);
        _carButton.setOnClickListener(this);



        SupportMapFragment mapFrag = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        _mapa = mapFrag.getMap();
        _mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        _mapa.setOnMapLongClickListener(this);

        _mapa.setMyLocationEnabled(true);
        _mapa.getUiSettings().setZoomControlsEnabled(true);
        _mapa.getUiSettings().setCompassEnabled(true);
        _mapa.setMyLocationEnabled(true);


        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);


        LatLng latLng;
        //comprovation if is posible get gps coordenate
        if (provider == null) {
            latLng = new LatLng(1, 1);
        } else {
            // Get Current Location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                latLng = new LatLng(0,0);;
            }else {
                Location myLocation = locationManager.getLastKnownLocation(provider);
                latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            }

        }

        //try to load the last position
        if( getLoadPosition()!=null)
           {
               makeMarker(getLoadPosition());
           }
        else
        {
        //if can't, show the actual position
        positionCamera(latLng);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #_mapa} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (_mapa == null) {
            // Try to obtain the map from the SupportMapFragment.
            _mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (_mapa != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #_mapa} is not null.
     */
    private void setUpMap() {
        _mapa.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    /**
     * Store the last coordenate of my car
     */
    private void StorePreferenceString(LatLng point)
    {
         _preferencias= this.getSharedPreferences("car", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = _preferencias.edit();
        editor.putString("latitude", point.latitude+"");
        editor.putString("longitude", point.longitude+"");
        editor.commit();

    }

    /**
     * fuction to load the position of a car
     * @return
     */
    private LatLng getLoadPosition() {

        _preferencias = this.getSharedPreferences("car", this.MODE_PRIVATE);
        String latitude = _preferencias.getString("latitude", "0.0");
        String longitude = _preferencias.getString("longitude", "0.0");
        _carPosition=new LatLng( Double.parseDouble(latitude),  Double.parseDouble(longitude));
        return _carPosition;


    }

    /**
     * function to mark the position of the car
     * @param point
     */
    private void makeMarker(LatLng point)
    {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.littlecar);
        _marker = _mapa.addMarker(new MarkerOptions()
                        .position(point)
                        .title("The Car is here")
                        .icon(icon)
        );
        positionCamera(point);
    }

    /**
     * Fuction to situate the camera on one point
     * @param point
     */
    private void positionCamera(LatLng point)
    {
        CameraPosition campos = new CameraPosition.Builder().target(point).zoom(11).build();
        CameraUpdate camup = CameraUpdateFactory.newCameraPosition(campos);
        _mapa.animateCamera(camup);
    }

    /**
     * function to take the event of click on a button
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == _carButton)
        {
            if(_carPosition!=null)
            {
                positionCamera(_carPosition);
            }
            else
            {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, "No hay ninguna ubicación del coche", duration);
                toast.show();
            }
        }
    }
}
