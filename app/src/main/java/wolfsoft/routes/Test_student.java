package wolfsoft.routes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.helper.SessionManager;
import wolfsoft.routes.loginandregister.MainActivity;

public class Test_student extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_student);


        Button button = (Button) findViewById(R.id.button2);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);

                db.deleteUsers();
                // Launching the login activity
                Intent intent = new Intent(Test_student.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
