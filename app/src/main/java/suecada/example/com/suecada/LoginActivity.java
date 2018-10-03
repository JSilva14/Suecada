package suecada.example.com.suecada;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private Button btnLogin;
    private ProgressBar loading;

    boolean loggedIn = false;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_login);

        etLoginUsername = findViewById(suecada.example.com.suecada.R.id.eTUsername);
        etLoginPassword = findViewById(suecada.example.com.suecada.R.id.eTPassword);
        btnLogin = findViewById(suecada.example.com.suecada.R.id.btnLogin);
        loading = findViewById(suecada.example.com.suecada.R.id.progressBar);

        btnLogin.setOnClickListener(loginClickListener);
        buttonEffect(btnLogin);
        btnLogin.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(mContext, MenuRankedActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Obter username e password inseridos pelo user
        final String username = etLoginUsername.getText().toString().trim();
        final String password = etLoginPassword.getText().toString().trim();


        //Criar a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Caso a resposta do servidor seja "success"
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                            //Instanciar sharedPreferences
                            SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                                    Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Criar editor para editar valores nas shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adicionar valores ao editor
                            //Indicar que o user está logado
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            //Editar flag do user atual para o username do user logado
                            editor.putString(Config.USERNAME_SHARED_PREF, username);
                            //editor.putInt(USERID_SHARED_PREF, id);

                            //Aplicar alterações
                            editor.apply();

                            //verificar qual o id do jogador na BD
                            getIdJogador();

                            //Iniciar acivity Menu
                            Intent intent = new Intent(mContext, MenuRankedActivity.class);
                            startActivity(intent);
                            //Terminar activity atual
                            finish();

                        } else {
                            //Caso a resposta do servidor não seja successo
                            //Mostrar "Toast" com mensagem de erro
                            Toast.makeText(mContext, "Username ou Password Inválidos!",
                                    Toast.LENGTH_LONG).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            etLoginUsername.setText("");
                            etLoginPassword.setText("");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Não foi possivel estabelecer ligação ao servidor",
                                Toast.LENGTH_LONG).show();
                        btnLogin.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getIdJogador() {

        //Obter username e password inseridos pelo user
        final String username = etLoginUsername.getText().toString().trim();
        //final String password = etLoginPassword.getText().toString().trim();

        //usado para get
        //String url = Config.JOGADOR_ATUAL_URL + etLoginUsername.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.JOGADOR_ATUAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                String jogadorAtualID;
                try {
                    //JSONObject que recebe json enviado via echo com id do jogador
                    JSONObject jsonObject = new JSONObject(response);
                    //obter o array "result" do JSONObject recebido
                    JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                    //novo JSON Object para receber o id do jogador presente no index 0 do array
                    JSONObject jogadorData = result.getJSONObject(0);
                    //transformar o id numa string
                    String id = jogadorData.getString(Config.KEY_ID);

                    //Instanciar Shared Preferences
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                            Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Criar editor para as shared references
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Colocar o id recebido na flag JOGADORID das shared preferences
                    //desta forma o id do jogador fica guardado "globalmente" caso seja necessário
                    //utilizá-lo em queries futuras (p. ex: em outra activity)
                    editor.putString(Config.JOGADORID_SHARED_PREF, id);
                    editor.apply();

                    //TODO -- eliminar este teste
                    //Consultar o valor da flag "JOGADORID" nas shared preferences
                    //para saber qual o id do jogador logado.
                    //colocar valor numa string para mostrar no ecrã se necessário
                    jogadorAtualID=sharedPreferences.getString(Config.JOGADORID_SHARED_PREF,"Not Available");

                    Toast.makeText(mContext,"ID= "+ jogadorAtualID,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"Erro: "+ e,Toast.LENGTH_LONG).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                //params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private View.OnClickListener loginClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            final String username = etLoginUsername.getText().toString().trim();
            final String password = etLoginPassword.getText().toString().trim();

            if (username.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                return;
            }

            btnLogin.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            login();



            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(etLoginPassword.getWindowToken(), 0);


        }
    };

    public void ContinuarOfflineIntent(View v) {
        Intent myIntent = new Intent(mContext, MenuOfflineActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void novoRegistoIntent(View v) {
        Intent myIntent = new Intent(mContext, RegistoActivity.class);
        startActivity(myIntent);
    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
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
}