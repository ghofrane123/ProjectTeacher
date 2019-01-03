package wolfsoft.routes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wolfsoft.routes.Adapter.CourByUserAdapter;
import wolfsoft.routes.Adapter.ReclamationAdapter;
import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.reclamation;
import wolfsoft.routes.helper.SQLiteHandler;

public class FetchReclamation extends AppCompatActivity {


    static ReclamationAdapter adapter1;
    private SQLiteHandler db;
    private List<reclamation> reclamations = new ArrayList<reclamation>();
    private ListView listview_reclamation;
    private static String TAG = FetchReclamation.class.getSimpleName();
    String user_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_reclamation);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Rerclamation");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        user_email = user.get("email");


        adapter1 = new ReclamationAdapter(FetchReclamation.this,reclamations) ;
        listview_reclamation = (ListView) findViewById(R.id.LV);
        listview_reclamation.setAdapter(adapter1);
        Animation animation = AnimationUtils.loadAnimation(FetchReclamation.this, R.anim.swing_up_left);
        listview_reclamation.startAnimation(animation);

        //SWITCH LIST VIEW
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_user_cour = "http://192.168.153.1/TA/teacher-reclamation.php?teacherEmail="+user_email;

        // prepare the Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_user_cour,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response.toString());


                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("rec");
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                JSONObject student = array.getJSONObject(i);



                                // Get current json object
                                reclamation reclam = new reclamation();

                                // Get the current student (json object) data
                                reclam.setDescription(student.getString("description"));
                                reclam.setRating(student.getInt("rating"));


                                reclamations.add(reclam);


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
                        Toast.makeText(getApplicationContext(),"jjjjj"+
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.getStackTrace();
                        Log.d(TAG, "onErrorResponse:123123 ");
                    }
                }





        );



        // add it to the RequestQueue
        queue.add(stringRequest);

    }
}
