package wolfsoft.routes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wolfsoft.routes.Adapter.PlaceArrayAdapter;
import wolfsoft.routes.helper.SQLiteHandler;

import static wolfsoft.routes.data.AlarmReminderProvider.LOG_TAG;

public class Profile extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    Activity mActivity;
    private String image_url ="http://192.168.153.1/TA/upload.php";
    private Bitmap bitmap;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_USER = "user_id";

    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int GOOGLE_API_CLIENT_ID = 0;
    AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;


    ImageView imageView;
    EditText name;
    private EditText lastname;
    private EditText telephone;
    private EditText longitude;
    private EditText latitude;
    private Button btnsave;
    private final int PICK_IMAGE_REQUEST = 1;
    Spinner mySpinner,MySpinner;
    private SQLiteHandler db;
    static String user_id,user_role,user_email;
    String savedExtra;
     private  static  String autoadresse;
    String text_name,spinner_text,text_telephone,text_lastname,Spinner_text;
    private static String TAG = Profile.class.getSimpleName();
    public static double lat;
    public static double lng;
    Geocoder geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mGoogleApiClient = new GoogleApiClient.Builder(Profile.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);



         geo = new Geocoder(this);




        savedExtra = getIntent().getStringExtra("image");
        imageView= (ImageView)findViewById(R.id.image);
        btnsave = (Button)findViewById(R.id.save);
         mySpinner=(Spinner) findViewById(R.id.spinner);
        MySpinner=(Spinner) findViewById(R.id.spinner1);
         lastname = (EditText)findViewById(R.id.lastname);
         name = (EditText)findViewById(R.id.name);
         telephone = (EditText)findViewById(R.id.telephone);






        Picasso.with(this)
                .load(savedExtra)
                .placeholder(R.drawable.profileimage)
                .error(R.drawable.profileimage)
                .resize(300,300)
                .into(imageView);



            Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
        });

        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view,
                                                                           int position, long id) {
                                                    spinner_text = mySpinner.getSelectedItem().toString();
                                                    Toast.makeText(Profile.this,spinner_text+lat+lng, Toast.LENGTH_LONG).show();

                                                }
                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }

                                            } );
        MySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner_text = MySpinner.getSelectedItem().toString();
                Toast.makeText(Profile.this,Spinner_text+lastname.getText().toString()+telephone.getText().toString(), Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );




        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                text_name = name.getText().toString();
                text_telephone = telephone.getText().toString();
                text_lastname = lastname.getText().toString();

                String url = "http://192.168.153.1/TA/update_profil.php?telephone="+text_telephone+"&lastname="+text_lastname+"&role="+text_name+"&niveau="+spinner_text+"&etude="+Spinner_text+"&lattitude="+lat+"&longitude="+lng+"&has_profile="+1+"&unique_id="+user_id;
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });


                queue.add(jsonObjReq);
                uploadImage();


            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                imageView.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }






    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,image_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Profile.this,s , Toast.LENGTH_LONG).show();
                        Log.e(TAG, "image error: " +s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d(TAG, "image error: " + volleyError.getMessage().toString());

                        //Showing toast
                        Toast.makeText(Profile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = lastname.getText().toString().trim();

                db = new SQLiteHandler(getApplicationContext());
                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();

                String user_id = user.get("uid");



                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE,image);
                params.put(KEY_NAME,name);
                params.put(KEY_USER,user_id);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            autoadresse = String.valueOf(item.description);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
            geocodeAddress(autoadresse,geo);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }







    public static void geocodeAddress(String addressStr, Geocoder gc) {
        Address address = null;
        List<Address> addressList = null;
        try {
            if (!TextUtils.isEmpty(addressStr)) {
                addressList = gc.getFromLocationName(addressStr, 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != addressList && addressList.size() > 0) {
            address = addressList.get(0);
        }
        if (null != address && address.hasLatitude()
                && address.hasLongitude()) {
            lat = address.getLatitude();
            lng = address.getLongitude();
            Log.d(TAG, "lattitude: "+lat +""+" "+ lng);
        }

    }



}
