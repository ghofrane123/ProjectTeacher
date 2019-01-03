package wolfsoft.routes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import wolfsoft.routes.Adapter.CommentAdapter;
import wolfsoft.routes.Adapter.CourAdapter;
import wolfsoft.routes.Adapter.CourByUserAdapter;
import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.comments;
import wolfsoft.routes.helper.SQLiteHandler;


import static wolfsoft.routes.MapsActivity.lat;
import static wolfsoft.routes.MapsActivity.lng;
import static wolfsoft.routes.R.id.imageView;
import static wolfsoft.routes.R.id.imageView3;

public class Comment extends AppCompatActivity {
    private SQLiteHandler db;
    String savedExtra, savedExtra1, savedExtra2, savedExtra3, savedExtra12,savedExtra4,savedExtra5,savedExtra10,savedExtra6, savedExtra7,savedExtra8, savedExtra9,savedExtra11;
    String text;
    static String user_id;
    private ListView listview_comment;
    static CommentAdapter adapter;
    private List<comments> cbu = new ArrayList<comments>();
    private static String TAG = Comment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
        });


        savedExtra = getIntent().getStringExtra("telephone");
        savedExtra1 = getIntent().getStringExtra("cour_matiere");
        savedExtra2 = getIntent().getStringExtra("cour_price");
        savedExtra3 = getIntent().getStringExtra("cour_description");
        savedExtra4 = getIntent().getStringExtra("id");
        savedExtra5 = getIntent().getStringExtra("email");
        savedExtra6 = getIntent().getStringExtra("rating");
        savedExtra7 = getIntent().getStringExtra("heure");
        savedExtra8 = getIntent().getStringExtra("name");
        savedExtra9 = getIntent().getStringExtra("lastname");
        savedExtra10 = getIntent().getStringExtra("number");
        savedExtra11 = getIntent().getStringExtra("image");



        Button call_user = (Button) findViewById(R.id.call);
        Button reclamation_user = (Button)findViewById(R.id.reclamation);
        Button make_comment = (Button) findViewById(R.id.button4);
        final EditText comment = (EditText) findViewById(R.id.editText3);

        TextView name= (TextView) findViewById(R.id.name);
        TextView lastname= (TextView) findViewById(R.id.lastname);
        TextView email_user= (TextView) findViewById(R.id.email);

        final TextView nombre= (TextView) findViewById(R.id.nombre);
        final TextView etudiant= (TextView) findViewById(R.id.nombre);
        TextView cour= (TextView) findViewById(R.id.cour);
        TextView Hour= (TextView) findViewById(R.id.heure);


        ImageView imgb= (ImageView) findViewById(imageView);
        ImageView image_profile= (ImageView) findViewById(imageView3);


        Picasso.with(this)
                .load(savedExtra11)
                .placeholder(R.drawable.profileimage)
                .error(R.drawable.profileimage)
                .resize(300,300)
                .into(image_profile);


        email_user.setText("email :"+savedExtra5);
        name.setText("name :"+savedExtra8);
        lastname.setText("lastname :"+savedExtra9);
        Hour.setText("number of hour  :"+savedExtra7);
        cour.setText("cour :"+savedExtra1);
        etudiant.setText("number of student :"+savedExtra10);




        //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_comment = "http://192.168.153.1/TA/fetch_comment.php?cour_id="+savedExtra4;

        // prepare the Request
        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url_comment, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try{
                            // Get the JSON array
                            org.json.JSONArray array = response.getJSONArray("comment");
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject student = array.getJSONObject(i);
                                comments CourByUser = new comments();
                                // Get the current student (json object) data
                                CourByUser.setUser_comment(student.getString("comment"));
                                CourByUser.setName(student.getString("role"));
                                CourByUser.setLastname(student.getString("lastname"));
                                cbu.add(CourByUser);


                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }





        );

        // add it to the RequestQueue
        queue.add(getRequest1);



        adapter = new CommentAdapter(Comment.this,cbu) ;
        listview_comment = (ListView) findViewById(R.id.list_item);
        listview_comment.setAdapter(adapter);


        ///get extra
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");







        call_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri number = Uri.parse("tel:+216"+savedExtra);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }

        });

        reclamation_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Comment.this,
                        Reclamation.class);
                intent.putExtra("email_user", savedExtra5);
                startActivity(intent);
                finish();
            }
        });

        make_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = comment.getText().toString();


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.153.1/TA/create_comment.php?comment="+text+"&unique_id="+user_id+"&cour_id="+savedExtra4;
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                ((EditText) findViewById(R.id.editText3)).getText().clear();
                                cbu.add(new comments(text));


                                adapter.notifyDataSetChanged();
                            //    adapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });


                queue.add(jsonObjReq);








            }


        });




    }









}
