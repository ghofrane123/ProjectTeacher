package wolfsoft.routes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simplify.payments.domain.InvoiceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wolfsoft.routes.Adapter.CourAdapter;
import wolfsoft.routes.Adapter.CourByUserAdapter;
import wolfsoft.routes.Adapter.HistoriqueAdapter;
import wolfsoft.routes.Adapter.ReclamationAdapter;
import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.teacher_cour;
import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.helper.SessionManager;

public class Historique extends AppCompatActivity {


    private SQLiteHandler db;
    private ListView listview_cour;
    static String user_id,user_role;
    ImageLoader imageLoader;
    static HistoriqueAdapter adapter1;
    static  HistoriqueAdapter adapter2;

    private static String TAG = Historique.class.getSimpleName();

    private List<CourByUser> teacherlist= new ArrayList<CourByUser>();
    private List<CourByUser> studentlist= new ArrayList<CourByUser>();
    private Toolbar mToolbar;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        textView = (TextView) findViewById(R.id.no_reminder_text);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
        });

        ///get extra
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");
        user_role = user.get("name");





            //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);
        if(user_role.equals("Student"))
        {
            adapter1 = new HistoriqueAdapter(Historique.this,teacherlist) ;
            listview_cour = (ListView) findViewById(R.id.LV);
            listview_cour.setAdapter(adapter1);
            Animation animation = AnimationUtils.loadAnimation(Historique.this, R.anim.swing_up_left);
            listview_cour.startAnimation(animation);
            textView.setText("");




            //String savedExtra = getIntent().getStringExtra("user_id");
            Log.d("userid", "Fetching user id: " + user_id +" "+ user_role);
            String url_fetch = "http://192.168.153.1/TA/historique_student.php?unique_id="+user_id;

            // prepare the Request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_fetch,
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
                                    CourByUser.setEmail(student.getString("email"));
                                    CourByUser.setImage(student.getString("photo"));
                                    CourByUser.setLastname(student.getString("lastname"));
                                    CourByUser.setRole(student.getString("role"));
                                    teacherlist.add(CourByUser);


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
            adapter2 = new HistoriqueAdapter(Historique.this,studentlist) ;
            listview_cour = (ListView) findViewById(R.id.LV);
            listview_cour.setAdapter(adapter2);
            Animation animation = AnimationUtils.loadAnimation(Historique.this, R.anim.swing_up_left);
            listview_cour.startAnimation(animation);



            // prepare the Request
            //String savedExtra = getIntent().getStringExtra("user_id");
            Log.d("userid", "Fetching user id: " + user_id +" "+ user_role);
            String url_fetch = "http://192.168.153.1/TA/historique_teacher.php?unique_id="+user_id;
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_fetch, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());

                            try{
                                // Get the JSON array
                                org.json.JSONArray array = response.getJSONArray("test");

                                // Loop through the array elements
                                for(int i=0;i<array.length();i++){
                                    // Get current json object
                                    JSONObject student = array.getJSONObject(i);
                                    // Get current json object
                                    CourByUser CourByUser = new CourByUser();



                                    CourByUser.setMatiere(student.getString("matiere"));
                                    CourByUser.setPrice(student.getString("price"));
                                    CourByUser.setEmail(student.getString("email"));
                                    CourByUser.setImage(student.getString("photo"));
                                    CourByUser.setLastname(student.getString("lastname"));
                                    CourByUser.setRole(student.getString("role"));
                                    studentlist.add(CourByUser);
                                    textView.setText("");


                                    //mTextView.append(firstName +" " + lastName +"\nage : " + age);
                                    //mTextView.append("\n\n");


                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            adapter2.notifyDataSetChanged();

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

    }
}
