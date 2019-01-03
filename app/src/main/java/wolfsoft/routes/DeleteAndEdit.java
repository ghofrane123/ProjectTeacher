package wolfsoft.routes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static wolfsoft.routes.Main3Activity.adapter;


public class DeleteAndEdit extends AppCompatActivity {

    EditText txtName;
    EditText txtPrice;
    EditText txtDesc;
    EditText txtCreatedAt;
    TextView btnSave;
    TextView btnDelete;
    String savedExtra,savedExtra1,savedExtra2,savedExtra3;

    private static String TAG = DeleteAndEdit.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_and_edit);


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


        txtPrice = (EditText) findViewById(R.id.price);
        txtDesc = (EditText) findViewById(R.id.inputDesc);

        btnSave = (TextView) findViewById(R.id.buttonsave);
        btnDelete = (TextView) findViewById(R.id.buttondelete);

         savedExtra = getIntent().getStringExtra("cour_id");
         savedExtra1 = getIntent().getStringExtra("cour_matiere");
         savedExtra2 = getIntent().getStringExtra("cour_price");
         savedExtra3 = getIntent().getStringExtra("cour_description");


        txtPrice.setText(savedExtra2);
        txtDesc.setText(savedExtra3);



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url_delete = "http://192.168.153.1/TA/delete_cours.php?cid="+savedExtra;

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url_delete, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                adapter.notifyDataSetChanged();

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });


                queue.add(jsonObjReq);


                Intent i = new Intent(getApplicationContext(),
                        Main3Activity.class);
                startActivity(i);
                finish();




            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url_delete = "http://192.168.153.1/TA/update_cours.php?cid="+savedExtra+"&matiere="+txtName.getText().toString()+"&price="+txtPrice.getText().toString()+"&description="+txtDesc.getText().toString();

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url_delete, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                adapter.notifyDataSetChanged();

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });


                queue.add(jsonObjReq);

                Intent i = new Intent(getApplicationContext(),
                        Main3Activity.class);
                startActivity(i);
                finish();




            }
        });
    }
}
