package suecada.example.com.suecada;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import static suecada.example.com.suecada.R.layout.activity_menu_ranked;

public class MenuRankedActivity extends AppCompatActivity {

    EventBus myEventBus = EventBus.getDefault();
    TextView tvGrupoAtual;
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_menu_ranked);


        //Associar views
        tvGrupoAtual = (TextView) findViewById(suecada.example.com.suecada.R.id.btnGrupoAtual);
        btnLogout = (Button) findViewById(suecada.example.com.suecada.R.id.btnLogout);

        //Inicializar sharedpreferences e obter o nome do grupo atual
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String grupo = sharedPreferences.getString(Config.GRUPO_SHARED_PREF, "Not Available");

        //Mostrar grupo atual
        String ligado_como = getResources().getString(suecada.example.com.suecada.R.string.ligado_grupo);
        String login_info = ligado_como + grupo;
        tvGrupoAtual.setText(login_info);

        btnLogout.setOnClickListener(logoutClickListener);
    }

    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Tem a certeza que deseja terminar sessão?");
        alertDialogBuilder.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.GRUPO_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.apply();

                        //Starting login activity
                        Intent intent = new Intent(MenuRankedActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private View.OnClickListener logoutClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            logout();
        }

    };

    public void SuecaOffline(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, SuecaActivity.class);

        startActivity(myIntent);
    }


    public void ItalianaOffline(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, ItaNomesActivity.class);

        startActivity(myIntent);
    }

    public void ItalianaRanked(View v) {

        //Configurar adaptador Bluetooth utilizado durante Sueca Italiana modo Ranked
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Caso o dispositivo não suporte bluetooth mostrar Toast com essa informação
        if (mBluetoothAdapter == null) {
            String btNaoSuportado="Este dispositivo não suporta a funcionalidade Bluetooth!";
            Toast.makeText(MenuRankedActivity.this,btNaoSuportado,Toast.LENGTH_LONG).show();
        }

        Intent myIntent = new Intent(MenuRankedActivity.this, ItaNomesRankedActivity.class);

        startActivity(myIntent);
    }

    public void GerirGrupo(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, GerirGrupoActivity.class);

        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}
