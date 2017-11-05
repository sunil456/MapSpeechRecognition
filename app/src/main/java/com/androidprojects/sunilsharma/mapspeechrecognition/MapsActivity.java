package com.androidprojects.sunilsharma.mapspeechrecognition;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.androidprojects.sunilsharma.mapspeechrecognition.Model.CountryDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String receivedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent mainActivityIntent = getIntent();
        receivedCountry = mainActivityIntent.getStringExtra(CountryDataSource.COUNTRY_KEY);

        if(receivedCountry == null)
        {
            receivedCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        double countryLatitude = CountryDataSource.DEFUALT_COUNTRY_LATITUDE;
        double countryLongitude = CountryDataSource.DEFUALT_COUNTRY_LONGITUDE;

        CountryDataSource countryDataSource = MainActivity.countryDataSource;
        String countryMessage = countryDataSource.getTheInfoOfTheCountry(receivedCountry);


        Geocoder geocoder = new Geocoder(MapsActivity.this);

        try
        {
            String countryAddress = receivedCountry;
            List<Address> countryAddresses = geocoder.getFromLocationName(countryAddress , 10);
            if(countryAddresses != null)
            {
                countryLatitude = countryAddresses.get(0).getLatitude();
                countryLongitude = countryAddresses.get(0).getLongitude();
            }
            else
            {
                receivedCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
            }
        }
        catch (IOException ioe)
        {
            receivedCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
        }

        LatLng myCountryLocation = new LatLng(countryLatitude , countryLongitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCountryLocation , 17.2f);
        mMap.moveCamera(cameraUpdate);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myCountryLocation);
        markerOptions.title(countryMessage);
        markerOptions.snippet(CountryDataSource.DEFUALT_MESSAGE);
        mMap.addMarker(markerOptions);

        /** Now we are Going to Add Circle to the Marker*/
        CircleOptions circleOptions = new CircleOptions();
        /** We have to set The Center of the Circle*/
        circleOptions.center(myCountryLocation);
        circleOptions.radius(300);
        /** now we have to set StrokeWidth of the Circle*/
        circleOptions.strokeWidth(20.0f);
        /** specifying the Color of the stroke*/
        circleOptions.strokeColor(Color.YELLOW);
        /** now we have to Add circle to the Map*/
        mMap.addCircle(circleOptions);



        //Now We Don't Need This Code.
       /** //19.261508, 72.847748 india
        //22.869478, 78.560639

        // Add a marker in India and move the camera
        LatLng india = new LatLng(22.869478, 78.560639);

        /** First we have to decide how much camera ZOOM In*//*
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(india , 14.0f);
        mMap.moveCamera(cameraUpdate);

        /** Now we are going to add The Marker to the place*//*
        MarkerOptions markerOptions = new MarkerOptions();
        *//** Now we have to specify the position of the marker*//*
        markerOptions.position(india);
        *//** Now we are going to Specify the Title*//*
        markerOptions.title("Welcome to India");

        markerOptions.snippet("Fantastic");

        *//** Finally we have to add markerOptions into the Map*//*
        mMap.addMarker(markerOptions);


        *//** Now we are Going to Add Circle to the Marker*//*
        CircleOptions circleOptions = new CircleOptions();
        *//** We have to set The Center of the Circle*//*
        circleOptions.center(india);
        circleOptions.radius(300);
        *//** now we have to set StrokeWidth of the Circle*//*
        circleOptions.strokeWidth(20.0f);
        *//** specifying the Color of the stroke*//*
        circleOptions.strokeColor(Color.YELLOW);
        *//** now we have to Add circle to the Map*//*
        mMap.addCircle(circleOptions);*/


    }
}
