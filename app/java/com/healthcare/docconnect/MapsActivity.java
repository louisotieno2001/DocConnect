package com.healthcare.docconnect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Button routeButton;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;
    private Location contactLocation;
    private GoogleMap googleMap;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //FirebaseApp.initializeApp(this);

        routeButton = findViewById(R.id.route_button);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    getUserLocation();
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }

    private void getUserLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    userLocation = location;
                    // TODO: Get the contact's location
                    // Once you have the contact's location, you can display the route on the map
                    displayRoute();
                } else {
                    Toast.makeText(MapsActivity.this, "Unable to retrieve user location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayRoute() {
        // TODO: Implement displaying the route on the map

        // Add markers for the user's location and the contact's location
        LatLng userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        LatLng contactLatLng = new LatLng(contactLocation.getLatitude(), contactLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(userLatLng).title("User"));
        googleMap.addMarker(new MarkerOptions().position(contactLatLng).title("Contact"));

        // Create a PolylineOptions object to draw the route
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(userLatLng)
                .add(contactLatLng)
                .width(5)
                .color(ContextCompat.getColor(MapsActivity.this, R.color.routeColor));

        // Add the polyline to the map
        googleMap.addPolyline(polylineOptions);

        // Move the camera to show both markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(userLatLng);
        builder.include(contactLatLng);
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
