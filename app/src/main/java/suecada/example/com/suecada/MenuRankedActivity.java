package suecada.example.com.suecada;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;


public class MenuRankedActivity extends AppCompatActivity {

    EventBus myEventBus = EventBus.getDefault();
    Toolbar rankedToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navViewMenuRanked;
    MenuItem mItemMinhaConta, mItemMeusGrupos, mItemTerminarSessao,
            mItemInfo, mItemSair;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ranked);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        rankedToolbar = findViewById(R.id.menuRankedToolbar);
        setSupportActionBar(rankedToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, rankedToolbar, R.string.abrir,
                R.string.fechar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navViewMenuRanked = findViewById(R.id.navViewMenuRanked);
        mItemMinhaConta = findViewById(R.id.nav_minha_conta);
        mItemMeusGrupos = findViewById(R.id.nav_meus_grupos);
        mItemInfo = findViewById(R.id.nav_info);
        mItemTerminarSessao = findViewById(R.id.nav_terminar_sessao);
        mItemSair = findViewById(R.id.nav_sair);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        navViewMenuRanked.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_minha_conta:
                        //fazer algo
                        return true;

                    case R.id.nav_meus_grupos:
                        GerirGrupo();
                        return true;

                    case R.id.nav_info:
                        //info
                        return true;

                    case R.id.nav_terminar_sessao:
                        logout();
                        return true;

                    case R.id.nav_sair:
                        doExit();
                        return true;

                    default:
                        // If we got here, the user's action was not recognized.
                        // Invoke the superclass to handle it.
                        return MenuRankedActivity.super.onNavigateUp();
                }
            }
        });

        //Inicializar sharedpreferences e obter o username do jogador atual
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String jogador = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "Not Available");


    }

    //Não mostrar menu de overflow na toolbar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
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
                        //Putting blank value to username
                        editor.putString(Config.USERNAME_SHARED_PREF, "");

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

   // private MenuItem.OnMenuItemClickListener logoutClickListener = new MenuItem.OnMenuItemClickListener() {

        //public void onMenuItemClick(MenuItem m) {
          //  logout();
       // }

   // };

    public void SuecaOffline(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, SuecaActivity.class);

        startActivity(myIntent);
    }


    public void ItalianaOffline(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, ItaNomesActivity.class);

        startActivity(myIntent);
    }

    public void ItalianaRanked(View v) {

        Intent myIntent = new Intent(MenuRankedActivity.this, ItaNomesRankedActivity.class);

        startActivity(myIntent);
    }

    public void GerirGrupo() {

        Intent myIntent = new Intent(MenuRankedActivity.this, GerirGrupoActivity.class);

        startActivity(myIntent);
    }

    private void doExit() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                mContext);

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("Não", null);

        alertDialog.setMessage("Tem a certeza que deseja sair?");
        alertDialog.setTitle("Suecada");
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        doExit();
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}
