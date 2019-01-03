package wolfsoft.routes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wolfsoft.routes.Entities.teacher;
public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener {
    Location mLastLocation;
    LatLng latLng;
    Marker mCurrLocationMarker;
    static double lat;
    static double lng;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private List<teacher> all_location = new ArrayList<teacher>();
    private static String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private double longitude_end;
    private double latitude_end;
    //Our buttons
    private Button buttonSetTo;
    private Button buttonSetFrom;
    private Button buttonCalcDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main3Activity.class));
                finish();
            }
        });




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);



        RequestQueue queue = Volley.newRequestQueue(this);

        String url_user_cour = "http://192.168.153.1/TA/all_location.php";

        // prepare the Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_user_cour,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response.toString());


                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("location");
                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject student = array.getJSONObject(i);
                                // Get the current student (json object) data
                                String name = student.getString("name");
                                String role = student.getString("role");
                                long lattitude = student.getLong("lattitude");
                                long longitude = student.getLong("longitude");
                                if (name.equals("Student")) {
                                    LatLng sydney = new LatLng(lattitude, longitude);
                                    mMap.addMarker(new MarkerOptions()
                                            .position(sydney)
                                            .title(role)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.professeur))
                                    );
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                } else {
                                    LatLng sydney = new LatLng(lattitude, longitude);
                                    mMap.addMarker(new MarkerOptions()
                                            .position(sydney)
                                            .title(role)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.graduation))
                                    );
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                                }


                            }
                        } catch (JSONException e) {

                            Log.d(TAG, "onResponseError: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error:11111 " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "jjjjj" +
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.getStackTrace();
                        Log.d(TAG, "onErrorResponse:123123 ");
                    }
                }
        );

        queue.add(stringRequest);


        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

    }


    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        //Place current location marker
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lat = latLng.latitude;
        lng = latLng.longitude;
        String dataToSend = latLng.toString();


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == buttonSetFrom) {

            Toast.makeText(this, "From set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonSetTo) {

            Toast.makeText(this, "To set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonCalcDistance) {


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitude_end, longitude_end));
            markerOptions.title("Destination");
            markerOptions.draggable(true);
            float result[] = new float[10];
            Location.distanceBetween(lng, lat, latitude_end, longitude_end, result);
            markerOptions.snippet("distination" + result[0]);
            mMap.addMarker(markerOptions);


        }
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
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latitude_end = marker.getPosition().latitude;
        longitude_end = marker.getPosition().longitude;

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }


    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();

        List<Address> addressList = null;

        if (((EditText)findViewById(R.id.TFaddress)).getText().toString().equals(""))

        {
            Toast.makeText(getApplicationContext(),
                    " put your city ", Toast.LENGTH_SHORT).show();
        }
            else
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location , 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            String addressText = String.format("%s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getCountryName());


            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(addressText));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.setMinZoomPreference(6.0f);
            mMap.setMaxZoomPreference(14.0f);

        }


    }


    public void changeType(View view)
    {
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}