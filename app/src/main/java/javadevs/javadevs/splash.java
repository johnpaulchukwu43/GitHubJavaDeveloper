package javadevs.javadevs;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class splash extends Activity {
    private static final int SPLASH_TIME_OUT = 6000;//time taken before the next activity will be called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        //trying to render Layouts based on the screen width of device in use
        if (config.smallestScreenWidthDp < 800)
        {
            setContentView(R.layout.splash_screen);
        }
        //
        else if(config.smallestScreenWidthDp >=800 && config.smallestScreenWidthDp <= 1200) {
            setContentView(R.layout.splash_screen_tablet_sm);
        }
        else if(config.smallestScreenWidthDp >1200)

        {
            setContentView(R.layout.splash_screen_tablet);
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer
             *  To show case app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start the app main activity
                Intent i = new Intent(splash.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
