package wolfsoft.routes.splach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import wolfsoft.routes.Main3Activity;
import wolfsoft.routes.R;
import wolfsoft.routes.ViewPagerTest;
import wolfsoft.routes.helper.SQLiteHandler;
import wolfsoft.routes.helper.SessionManager;
import wolfsoft.routes.loginandregister.MainActivity;

public class Splashscreen extends AppCompatActivity {

    Thread splashTread;
    TextView textView;
    private static int has_profile;
    private SessionManager session;
    private SQLiteHandler db;
    private  String savedExtra1;
    private    int  savedExtra2;
    private final  int number = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        textView = (TextView)findViewById(R.id.textView7);
        Animation animation1 = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.translate);
        Animation animation2 = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.alpha);
        textView.startAnimation(animation1);
        savedExtra2 = getIntent().getIntExtra("profile_user",number);
        Toast.makeText(getApplicationContext(), "Json error: " + savedExtra2, Toast.LENGTH_LONG).show();











        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    savedExtra1 = getIntent().getStringExtra("uid");





                    if ( savedExtra2 == 1)
                    {
                        Intent intent = new Intent(Splashscreen.this,
                                Main3Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("userid", savedExtra1);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(Splashscreen.this,
                                ViewPagerTest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("userid", savedExtra1);
                        startActivity(intent);
                        finish();
                    }
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();







    }


}

