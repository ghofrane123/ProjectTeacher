package wolfsoft.routes;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.HashMap;

import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.utils.NotificationPublisher;

public class Reclamation extends AppCompatActivity {
    private  Button button;
    private  String savedExtra;
    private  TextView textView;
    private  RatingBar ratingBar;
    private TextView comment,statusMessage;
    private EditText description;
    private int rating1;
    private  String com,user_id;
    private static String TAG = Reclamation.class.getSimpleName();
    private SQLiteHandler db;
    String reclamation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);

        ///get extra
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");

        savedExtra = getIntent().getStringExtra("email_user");


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Rerclamation");

         statusMessage = (TextView)findViewById(R.id.textView6);
        comment = (TextView)findViewById(R.id.comment);
        description = (EditText)findViewById(R.id.editText2);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        reclamation = description.getText().toString();

        comment.setText(" ");






        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar rtBar, float rating,boolean fromUser) {
                 rating1 = (int) rating;
               // Toast.makeText(this,"Rating:"+String.valueOf(rating), Toast.LENGTH_LONG).show();
                //comment.setText(String.valueOf(rating1));

                ImageView imgb= (ImageView) findViewById(R.id.imageView4);

                if(rating1 == 1)
                {
                    comment.setText("Poor");
                    imgb.setImageResource(R.drawable.poor1);
                }

                if(rating1 == 2)
                {
                    comment.setText("Below Average");
                    imgb.setImageResource(R.drawable.poor2);
                }

                if(rating1 == 3)
                {
                    comment.setText("Average");
                    imgb.setImageResource(R.drawable.poor3);
                }
                if(rating1 == 4)
                {
                    comment.setText("Above Average");
                    imgb.setImageResource(R.drawable.poor4);
                }

                if(rating1 == 5)
                {
                    comment.setText("Excellent");
                    imgb.setImageResource(R.drawable.poor5);
                }






            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
        });



        if(String.valueOf(rating1).equals("1"))
        {
            comment.setText("Poor");
        }




        button = (Button) findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((EditText) findViewById(R.id.editText2)).getText().toString().equals(""))

                {
                    Toast.makeText(getApplicationContext(),
                            " write description ", Toast.LENGTH_SHORT).show();
                }
  else
                {

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.153.1/TA/create_reclamation.php?teacherEmail="+savedExtra+"&rating="+String.valueOf(rating1)+"&studentid="+user_id+"&description="+((EditText) findViewById(R.id.editText2)).getText().toString();
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                            url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d(TAG, response.toString());
                                    ((EditText) findViewById(R.id.editText2)).getText().clear();

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    });


                    queue.add(jsonObjReq);

                }


            }
        });
    }




}
