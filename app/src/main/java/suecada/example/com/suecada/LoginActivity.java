package suecada.example.com.suecada;

import android.content.Context;
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

import static suecada.example.com.suecada.Config.GRUPO_SHARED_PREF;
import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class LoginActivity extends AppCompatActivity {

    private EditText etGrupo, etPassword;
    private Button btnLogin;
    private ProgressBar loading;

    boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_login);

        etGrupo = (EditText) findViewById(suecada.example.com.suecada.R.id.eTNomeGrupo);
        etPassword = (EditText) findViewById(suecada.example.com.suecada.R.id.eTPassword);
        btnLogin = (Button) findViewById(suecada.example.com.suecada.R.id.btnLogin);
        loading = (ProgressBar) findViewById(suecada.example.com.suecada.R.id.progressBar);

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

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MenuRankedActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        //Getting values from edit texts
        final String grupo = etGrupo.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {

                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                    Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(GRUPO_SHARED_PREF, grupo);

                            //Saving values to editor
                            editor.apply();

                            //Starting profile activity
                            Intent intent = new Intent(LoginActivity.this, MenuRankedActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Grupo ou Password Inválidos!", Toast.LENGTH_LONG).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            etGrupo.setText("");
                            etPassword.setText("");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Não foi possivel estabelecer ligação ao servidor",
                                Toast.LENGTH_LONG).show();
                        btnLogin.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_GRUPO, grupo);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        getIdGrupo();

    }

    private void getIdGrupo() {


        String url = Config.DATA_URL+etGrupo.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response){
        String grupoAtualID;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject grupoData = result.getJSONObject(0);
            String id = grupoData.getString(Config.KEY_ID);

            //Creating a shared preference
            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                    Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(Config.GRUPOID_SHARED_PREF, id);
            editor.apply();

            grupoAtualID=sharedPreferences.getString(Config.GRUPOID_SHARED_PREF,"Not Available");

            Toast.makeText(this,"ID= "+ grupoAtualID,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"ID= "+ e,Toast.LENGTH_LONG).show();

        }



          }

    private View.OnClickListener loginClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            final String grupo = etGrupo.getText().toString().trim();
            final String password = etPassword.getText().toString().trim();

            if (grupo.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                return;
            }

            btnLogin.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            login();



            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);


        }


    };

    public void ContinuarOffline(View v) {
        Intent myIntent = new Intent(LoginActivity.this, MenuOfflineActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void CriarGrupo(View v) {
        Intent myIntent = new Intent(LoginActivity.this, CriarGrupoActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}