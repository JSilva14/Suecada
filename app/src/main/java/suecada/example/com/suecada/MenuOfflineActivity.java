package suecada.example.com.suecada;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuOfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_menu_offline);
    }

    public void SuecaOffline(View v){

        Intent myIntent = new Intent(MenuOfflineActivity.this, SuecaActivity.class);

        startActivity(myIntent);
    }


    public void ItalianaOffline(View v){

        Intent myIntent = new Intent(MenuOfflineActivity.this, ItaNomesActivity.class);

        startActivity(myIntent);
    }

    public void Login(View v){

        Intent myIntent = new Intent(MenuOfflineActivity.this, LoginActivity.class);

        startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(MenuOfflineActivity.this, LoginActivity.class);

        startActivity(myIntent);
        finish();
    }
}
