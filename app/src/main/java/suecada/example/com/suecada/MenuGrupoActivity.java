package suecada.example.com.suecada;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuGrupoActivity extends AppCompatActivity {

    private Toolbar rankedToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navViewMenu;

    Context mContext=this;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_grupo);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        rankedToolbar = findViewById(R.id.menuGrupoToolbar);
        setSupportActionBar(rankedToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, rankedToolbar, R.string.abrir,
                R.string.fechar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navViewMenu = findViewById(R.id.navViewMenuGrupo);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        navViewMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_minha_conta:
                        //fazer algo
                        return true;

                    case R.id.nav_meus_grupos:
                        //
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
                        return MenuGrupoActivity.super.onNavigateUp();
                }
            }
        });

        String grupoAtual = getIntent().getStringExtra("nomeGrupo");
        String permissoes = getIntent().getStringExtra("permissoes");

        Toast.makeText(this,"Grupo: " + grupoAtual + " permissoes: "+permissoes,Toast.LENGTH_LONG).show();

        //colocar nome do grupo no titulo da toolbar e nas sharedpreferences para
        //consultar info do grupo através nome via API no método getGrupoInfo
        getSupportActionBar().setTitle(grupoAtual);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.GRUPONOME_SHARED_PREF, grupoAtual);
        //Atualizar tipo de permissoes do user neste grupo nas sharedprefs
        editor.putString(Config.GRUPOPERMISSOES_SHARED_PREF, permissoes);
        editor.apply();

        getInfoGrupoAtual();
    }

    //Não mostrar menu de overflow na toolbar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    private void getInfoGrupoAtual(){

        //Obter username e password inseridos pelo user
        final String nomeGrupo = sharedPreferences.getString(Config.GRUPONOME_SHARED_PREF,
                Config.GRUPONOME_SHARED_PREF);

        //Criar Hashmap com os parametros que queremos colocar no pedido à BD
        Map<String, String> parametros = new HashMap<>();
        parametros.put("nome", nomeGrupo);

        //Instanciar DBData para efetuar um request à API
        DBData dbData = new DBData();

        dbData.fetchResponse(mContext, Config.GRUPOINFO_URL,
                parametros, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String resposta) {

                        try {
                            Log.d("RESPOSTA", resposta);
                            //obter o array "result" na respostaJSON
                            JSONObject result = new JSONObject(resposta);

                            String sucesso = result.getString("sucesso");
                            String mensagemResposta = result.getString("mensagem");
                            Log.d("SUCESSO: ", sucesso);


                            switch (sucesso) {

                                case "1":

                                    String idGrupo = result.getString("id");
                                    String codigoAcesso = result.getString("codigo_acesso");

                                    //Criar editor para editar valores nas shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adicionar valores ao editor
                                    //Editar flags de info do grupo atual
                                    editor.putString(Config.GRUPOID_SHARED_PREF, idGrupo);
                                    editor.putString(Config.GRUPOCODIGOACESSO_SHARED_PREF, codigoAcesso);

                                    //Aplicar alterações
                                    editor.apply();
                                    break;

                                //Password errada
                                case "0":
                                    //Caso a resposta do servidor não seja successo
                                    //Mostrar "Toast" com mensagem de erro
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    finishAffinity();
                                    break;

                                default:
                                    Toast.makeText(mContext, "Ocorreu um erro na ligação ao servidor",
                                            Toast.LENGTH_LONG).show();
                                    finishAffinity();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finishAffinity();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                        finishAffinity();
                    }
                });
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

                        editor.clear();

                        //Saving the sharedpreferences
                        editor.apply();

                        //Starting login activity
                        Intent intent = new Intent(MenuGrupoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();

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

    private void doExit() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                mContext);

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        alertDialog.setNegativeButton("Não", null);

        alertDialog.setMessage("Tem a certeza que deseja sair?");
        alertDialog.setTitle("Suecada");
        alertDialog.show();
    }

    public void ItalianaRanked(View v) {

        Intent myIntent = new Intent(MenuGrupoActivity.this, ItaNomesRankedActivity.class);

        startActivity(myIntent);
    }

    public void GerirGrupo(View v) {

        Intent myIntent = new Intent(MenuGrupoActivity.this, GerirGrupoActivity.class);

        startActivity(myIntent);
    }

}
