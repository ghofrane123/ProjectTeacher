package wolfsoft.routes.loginandregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wolfsoft.routes.App.AppConfig;
import wolfsoft.routes.App.AppController;
import wolfsoft.routes.Entities.teacher;
import wolfsoft.routes.Main3Activity;
import wolfsoft.routes.R;
import wolfsoft.routes.ViewPagerTest;
import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.helper.SessionManager;
import wolfsoft.routes.splach.Splashscreen;

public class MainActivity extends AppCompatActivity {


    private TextView signup;
    private TextView signin;
    private TextView fb;
    private TextView account;
    private EditText email;
    private EditText password;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public String profile_user;
    private int profile = 0;
    private ArrayList<teacher> profilelist;
    private String url_profile;
    private String email_user;
    private static int has_profile = 0;
    private String user_id;
    private static final String TAG = MainActivity.class.getSimpleName();
    String uid, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Fetching one signal: " + has_profile);
        signup = (TextView) findViewById(R.id.signup);
        signin = (TextView) findViewById(R.id.signin);

        account = (TextView) findViewById(R.id.buttonsignin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        // Progress dialog
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {

            // SQLite database handler
            db = new SQLiteHandler(getApplicationContext());
            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();
            user_id = user.get("uid");
            test(user_id);
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, Splashscreen.class);
            intent.putExtra("profile_user",has_profile);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        account.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();

                // Check for empty data in the form
                if (!email1.isEmpty() && !password1.isEmpty()) {
                    // login user
                    checkLogin(email1, password1);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SecondActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");


                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        test(uid);
                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);


                        // Launch main activity
                        Intent intent = new Intent(MainActivity.this,
                                Splashscreen.class);
                        intent.putExtra("profile_user",has_profile);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public  int  test(String getuid)
    {
        //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_comment = "http://197.28.171.136/android_login_api/has_profile.php?unique_id="+getuid;

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
                            org.json.JSONArray array = response.getJSONArray("profile");
                            // Loop through the array elements

                            // Get current json object
                            JSONObject student = array.getJSONObject(0);
                            teacher teacher = new teacher();
                            // Get the current student (json object) data
                            has_profile = student.getInt("has_profile");


                        }catch (JSONException e){
                            e.printStackTrace();
                        }




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
        return  has_profile;
    }

}