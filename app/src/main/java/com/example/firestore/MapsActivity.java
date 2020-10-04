package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestore.Modal.Common;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private ImageView map_marker;
    public TextView textView;
    public final int request = 1;
    public final int REQUEST_GPS_CODE = 11;
    public Button button;
    public String newAddress;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    public MapAddress map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        gpsCheck();
        geocoder = new Geocoder(this);
        map_marker = findViewById(R.id.map_marker);
        textView = findViewById(R.id.textview);
        button = findViewById(R.id.get_address);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                map = new MapAddress();
                firebaseFirestore = FirebaseFirestore.getInstance();
                MapAddress mapAddress = new MapAddress();
                Log.d("MAP", newAddress);
                Common.MAP.setMap(newAddress);
                collectionReference = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("order_details");
                collectionReference.document("order_address").set(Common.MAP);
                Intent intent = new Intent(MapsActivity.this, buy_activity.class);
                startActivity(intent);
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
//        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng latLng = mMap.getCameraPosition().target;
//                try {
//                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    if(addresses.size() > 0)
//                    {
//                        Address address = addresses.get(0);
//                        String newAddress = address.getAddressLine(0);
//                        textView.setText(newAddress);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            displayUserLocation();
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request);
            }
        }
    }
//        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker)
//            {
//                LatLng latLng = marker.getPosition();
////                try {
////                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
////                    if(addresses.size() > 0)
////                    {
////                        Address address = addresses.get(0);
////                        String newAddress = address.getAddressLine(0);
////                        marker.setTitle(newAddress);
////                        textView.setText(newAddress);
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//        });
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng)
//            {
//                try {
//                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    if(addresses.size() > 0)
//                    {
//                        Address address = addresses.get(0);
//                        String newAddress = address.getAddressLine(0);
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(newAddress)
//                        .draggable(true));
//                        textView.setText(newAddress);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                mMap.addMarker(new MarkerOptions().position(latLng));
//            }
//        });
//    }

    private void enableUserLocation()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
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
    }

    private void displayUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21));
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        newAddress = address.getAddressLine(0);
                        textView.setText(newAddress);
                        button.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        enableUserLocation();
                        displayUserLocation();
                    }
                    else
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))
                        {
                            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request);
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request);
                        }
                    }
                }
            }
        });
        changeLocation();
    }
    private void changeLocation()
    {
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if(addresses.size() > 0)
                    {
                        Address address = addresses.get(0);
                        newAddress = address.getAddressLine(0);
                        textView.setText(newAddress);
                        button.setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void gpsCheck() {

        LocationRequest request = new LocationRequest()
                .setFastestInterval(1500)
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode()){
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED :
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            try {
                                resolvableApiException.startResolutionForResult(MapsActivity.this,REQUEST_GPS_CODE);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }

                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + e.getStatusCode());
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GPS_CODE){
            if(resultCode == RESULT_OK){
                Toast.makeText(MapsActivity.this,"ENABLED", Toast.LENGTH_SHORT).show();
            }
            else{
                finish();
                Toast.makeText(MapsActivity.this,"NOT ENABLED", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == request)
        {
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                enableUserLocation();
            }
            else
            {

            }
        }
    }
}
