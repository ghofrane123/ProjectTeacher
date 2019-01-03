package wolfsoft.routes.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by G on 19/12/2017.
 */

public class MainApplication extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
