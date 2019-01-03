package wolfsoft.routes;

import android.app.ProgressDialog;

import android.app.SearchManager;
import android.content.res.Resources;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


import wolfsoft.routes.Adapter.CourAdapter;
import wolfsoft.routes.Adapter.CourByUserAdapter;
import wolfsoft.routes.Cour.Cour_Add;
import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.teacher_cour;
import wolfsoft.routes.ReminderActicity.MainReminder;
import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.helper.SessionManager;
import wolfsoft.routes.loginandregister.MainActivity;
//import wolfsoft.routes.Notification.MyNotificationOpenedHandler;
//import wolfsoft.routes.Notification.MyNotificationOpenedHandler2;
import wolfsoft.routes.utils.CustomVolleyRequest;
import wolfsoft.routes.utils.LocaleHelper;

//import static wolfsoft.routes.Notification.ResponseNotification.fetch;
import static wolfsoft.routes.R.menu.main3;


public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnQueryTextListener {
    private SQLiteHandler db;
    private SessionManager session;
    Spinner spinnertest;
    EditText name, price, description;
    private TextView n1,d1;
   private ListView listview_cour;
    static String user_id,user_role,user_email;
    ImageLoader imageLoader;
     static CourAdapter adapter;
    private SearchView searchView;
    private MenuItem searchMenuItem;
     static  CourByUserAdapter adapter1;
     private String imageurl;

    CourByUserAdapter ghofrane;


    private static String TAG = Main3Activity.class.getSimpleName();

    private List<teacher_cour> courtlist= new ArrayList<teacher_cour>();
    private ArrayList<teacher_cour> filteredList;
    private List<CourByUser> cbu = new ArrayList<CourByUser>();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    // Progress Dialog
    private ProgressDialog pDialog;
    // these two variables will be used by SharedPreferences
    private static final String FILE_NAME = "file_lang"; // preference file name
    private static final String KEY_LANG = "key_lang"; // preference key






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        test(user_id);
        //one signal
        /*OneSignal.startInit(this)
                .autoPromptLocation(true)
                .init();
        // please load language after super and before setContentView

            OneSignal.startInit(this)
                    .setNotificationOpenedHandler(new MyNotificationOpenedHandler(this))
                    .init();*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ///get extra
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");
        user_role = user.get("name");
        user_email = user.get("email");

        Log.d("userid", "Fetching one signal: " + user_id +" "+ user_email);


      /*  OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.sendTag("User_ID",user_email);
        OneSignal.sendTag("realname",user_role);*/





        // session manager
        session = new SessionManager(getApplicationContext());
        session.isconncted(user_id,1);

        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();

        //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);

        if(user_role.equals("Student"))
        {
            adapter1 = new CourByUserAdapter(Main3Activity.this,cbu) ;
            listview_cour = (ListView) findViewById(R.id.LV);
            listview_cour.setAdapter(adapter1);
            Animation animation = AnimationUtils.loadAnimation(Main3Activity.this, R.anim.swing_up_left);
            listview_cour.startAnimation(animation);




            //String savedExtra = getIntent().getStringExtra("user_id");
            Log.d("userid", "Fetching user id: " + user_id +" "+ user_role);
            String url_user_cour = "http://192.168.153.1/TA/jointure.php";

            // prepare the Request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_user_cour,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // display response
                            Log.d("Response", response.toString());


                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray array  = object.getJSONArray("test");
                                // Loop through the array elements
                                for(int i=0;i<array.length();i++){
                                    JSONObject student = array.getJSONObject(i);

                                    Log.d(TAG, "onResponse: 123"+student.getString("matiere"));

                                    // Get current json object
                                    CourByUser CourByUser = new CourByUser();

                                    // Get the current student (json object) data
                                    CourByUser.setMatiere(student.getString("matiere"));
                                    CourByUser.setPrice(student.getString("price"));
                                    CourByUser.setDescription(student.getString("description"));
                                    CourByUser.setIs_logged(student.getString("is_logged"));
                                    CourByUser.setCreated_at(student.getString("created_at"));
                                    CourByUser.setEmail(student.getString("email"));
                                    CourByUser.setCid(student.getString("cid"));
                                    CourByUser.setTelephone(student.getString("telephone"));
                                    CourByUser.setImage(student.getString("photo"));
                                    CourByUser.setLastname(student.getString("lastname"));
                                    CourByUser.setHeure(student.getString("heure"));
                                    CourByUser.setEtudiant(student.getString("etudiant"));
                                    CourByUser.setRole(student.getString("role"));
                                    CourByUser.setRating(student.getString("Rating"));
                                    CourByUser.setUser_id(student.getString("user_id"));
                                    cbu.add(CourByUser);


                                    //mTextView.append(firstName +" " + lastName +"\nage : " + age);
                                    //mTextView.append("\n\n");


                                }
                            }catch (JSONException e){

                                Log.d(TAG, "onResponseError: "+e.getMessage());
                                e.printStackTrace();
                            }

                            adapter1.notifyDataSetChanged();


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:11111 " + error.getMessage());
                            error.getStackTrace();
                            Log.d(TAG, "onErrorResponse:123123 ");
                        }
                    }





            );



            // add it to the RequestQueue
            queue.add(stringRequest);


        }
        else
        {
            adapter = new CourAdapter(Main3Activity.this,courtlist) ;
            listview_cour = (ListView) findViewById(R.id.LV);
            listview_cour.setAdapter(adapter);
            Animation animation = AnimationUtils.loadAnimation(Main3Activity.this, R.anim.swing_up_left);
            listview_cour.startAnimation(animation);


            // prepare the Request
            //String savedExtra = getIntent().getStringExtra("user_id");
            Log.d("userid", "Fetching user id: " + user_id +" "+ user_role);
            String url_fetch = "http://197.28.171.136/android_login_api/user_cours.php?user_id="+user_id;
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_fetch, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());

                            try{
                                // Get the JSON array
                                org.json.JSONArray array = response.getJSONArray("cours");

                                // Loop through the array elements
                                for(int i=0;i<array.length();i++){
                                    // Get current json object
                                    JSONObject student = array.getJSONObject(i);
                                    teacher_cour teacher_cour = new teacher_cour();


                                    // Get the current student (json object) data
                                    teacher_cour.setId(student.getString("cid"));
                                    teacher_cour.setMatiere(student.getString("matiere"));
                                    teacher_cour.setPrice(student.getString("price"));
                                    teacher_cour.setDescription(student.getString("description"));
                                    teacher_cour.setHeure(student.getString("heure"));
                                    teacher_cour.setEtudiant(student.getInt("etudiant"));
                                    teacher_cour.setCreated_at(student.getString("created_at"));




                                    courtlist.add(teacher_cour);


                                    //mTextView.append(firstName +" " + lastName +"\nage : " + age);
                                    //mTextView.append("\n\n");


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

                        }
                    }

            );

            // add it to the RequestQueue
            queue.add(getRequest);

        }














        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main3, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

         if(user_role.equals("Student"))
         {
             MenuItem item = menu.findItem(R.id.action_user);
             item.setVisible(false);

         }


        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);


        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;

    }






        @Override
    public boolean onOptionsItemSelected(MenuItem item) {


            switch (item.getItemId()) {
                case R.id.action_user:
                    Intent intent = new Intent(Main3Activity.this,Cour_Add.class);
                    startActivity(intent);
                    finish();
                    break;



            }


        return true ;
    }







    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Main3Activity.this,FetchReclamation.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(Main3Activity.this, MapsActivity.class);
            startActivity(intent);
            finish();


     } else if (id == R.id.nav_student) {


            // Launching the login activity
            Intent intent = new Intent(Main3Activity.this, MainReminder.class);
            startActivity(intent);
            finish();



    }

        else if (id == R.id.Profile) {

            // Launching the login activity
            Intent intent = new Intent(Main3Activity.this, Profile.class);
            intent.putExtra("image",imageurl);
            startActivity(intent);
           finish();

        }

       else if (id == R.id.nav_share) {

            Intent intent = new Intent(Main3Activity.this,Historique.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_send) {

            session.setLogin(false);

            db.deleteUsers();

         session.isconncted(user_id,0);


            // Launching the login activity
            Intent intent = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }



    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        session.isconncted(user_id,0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        session.isconncted(user_id,1);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        adapter1.getFilter().filter(newText);

        // use to enable search view popup text
        if (TextUtils.isEmpty(newText)) {
            listview_cour.clearTextFilter();
        }
      else {
            listview_cour.setAdapter(adapter1);
       }
        return true;
    }


    public static void sendNotification(final String user_email) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....

                        send_email = user_email;
                 //   fetch = "true";


                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MDg2OGM4MDUtNGY1MS00YmU4LWE5ZjctNzJjMGFkMDBkNTQz");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"21128ac1-8b35-454e-99c7-378a5073d951\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"gi wanna get cours \"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });




    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this,languageCode);
        Resources resources = context.getResources();
        setTitle(resources.getString(R.string.app_name));


    }


    public  String  test(String user_id)
    {
        //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_comment = "http://192.168.153.1/TA/user_image.php?unique_id="+user_id;

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
                            org.json.JSONArray array = response.getJSONArray("image");
                            // Loop through the array elements

                            // Get current json object
                            JSONObject student = array.getJSONObject(0);
                            // Get the current student (json object) data
                            imageurl = student.getString("photo");


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
        return  imageurl;
    }





}





