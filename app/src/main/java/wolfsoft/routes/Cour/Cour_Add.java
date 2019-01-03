package wolfsoft.routes.Cour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import wolfsoft.routes.Main3Activity;
import wolfsoft.routes.R;
import wolfsoft.routes.helper.SQLiteHandler;

public class Cour_Add extends AppCompatActivity {
    private Spinner matiere,etudiant,heure;
    EditText description , price ;
    private SQLiteHandler db;
    Button button ;
    static String user_id;
    private static String TAG = Main3Activity.class.getSimpleName();
    String mat, he ,et;
    String spinner_text;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cour__add);

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

        RequestQueue queue = Volley.newRequestQueue(this);

        ///get extra
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        user_id = user.get("uid");

        matiere = (Spinner) findViewById(R.id.spinner1);
        etudiant = (Spinner) findViewById(R.id.spinner2);
        heure = (Spinner) findViewById(R.id.spinner);
        description = (EditText) findViewById(R.id.editText2);
        price = (EditText) findViewById(R.id.price);
        button = (Button) findViewById(R.id.saveBtn);


        heure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                he = heure.getSelectedItem().toString();
                Toast.makeText(Cour_Add.this,he, Toast.LENGTH_LONG).show();

            }

            /**
             * Callback method to be invoked when the selection disappears from this
             * view. The selection can disappear for instance when touch is activated
             * or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );



        matiere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mat = matiere.getSelectedItem().toString();
                Toast.makeText(Cour_Add.this,mat, Toast.LENGTH_LONG).show();

            }

            /**
             * Callback method to be invoked when the selection disappears from this
             * view. The selection can disappear for instance when touch is activated
             * or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


        etudiant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                et = etudiant.getSelectedItem().toString();
                Toast.makeText(Cour_Add.this,et, Toast.LENGTH_LONG).show();

            }

            /**
             * Callback method to be invoked when the selection disappears from this
             * view. The selection can disappear for instance when touch is activated
             * or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                ///get extra
                db = new SQLiteHandler(getApplicationContext());
                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();
                user_id = user.get("uid");
                String url = "http://192.168.153.1/TA/create_cours.php?matiere="+mat+"&price="+price.getText().toString()+"&description="+description.getText().toString()+"&user_id="+user_id+"&etudiant="+et+"&heure="+he;
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                Log.d(TAG, "Error: "+user_id);
                                ((EditText) findViewById(R.id.price)).getText().clear();
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



        });


    }
}
