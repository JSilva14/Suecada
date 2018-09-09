package phpmysql.example.com.suecada;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Splash extends Activity {


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        final ActivityOptions options;
        View logo = findViewById(R.id.splashscreen);
        options = ActivityOptions
                .makeSceneTransitionAnimation(this, logo, "logo");

        /* Duration of wait */
        final int SPLASH_DISPLAY_LENGTH = 1500;


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(Splash.this,LoginActivity.class);

                Splash.this.startActivity(mainIntent, options.toBundle());
                

            }
        }, SPLASH_DISPLAY_LENGTH);


    }
    }
