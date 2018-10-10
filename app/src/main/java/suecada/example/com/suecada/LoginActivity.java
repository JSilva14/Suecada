package suecada.example.com.suecada;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

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

    //Instanciar sharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_login);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

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

        //Verificar nas SharedPreferences se existe um jogador logado
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

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

        //Criar Hashmap com os parametros que queremos colocar no pedido à BD
        Map<String, String> parametros = new HashMap<>();
        parametros.put("username", username);
        parametros.put("password", password);

        //Instanciar DBData para efetuar um request à API
        DBData dbData = new DBData();

        dbData.fetchResponse(mContext, Config.LOGIN_URL,
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

                                    String idJogador = result.getString("id");
                                    String usernameJogador = result.getString("username");
                                    String nomeJogador = result.getString("nome");
                                    String apelidoJogador = result.getString("apelido");
                                    String emailJogador = result.getString("email");

                                    //Criar editor para editar valores nas shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    //Adicionar valores ao editor
                                    //Indicar que o user está logado
                                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                    //Editar flags de info do user atual
                                    editor.putString(Config.JOGADORUSERNAME_SHARED_PREF, usernameJogador);
                                    editor.putString(Config.JOGADORID_SHARED_PREF, idJogador);
                                    editor.putString(Config.JOGADORNOME_SHARED_PREF, nomeJogador);
                                    editor.putString(Config.JOGADORAPELIDO_SHARED_PREF, apelidoJogador);
                                    editor.putString(Config.JOGADOREMAIL_SHARED_PREF, emailJogador);


                                    //Aplicar alterações
                                    editor.apply();

                                    //verificar qual o id do jogador na BD
                                    //getIdJogador();

                                    //Iniciar acivity Menu
                                    Intent intent = new Intent(mContext, MenuRankedActivity.class);
                                    startActivity(intent);
                                    //Terminar activity atual
                                    finish();
                                    break;

                                case "0":
                                    //Caso a resposta do servidor não seja successo
                                    //Mostrar "Toast" com mensagem de erro
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    btnLogin.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                    etLoginUsername.setText("");
                                    etLoginPassword.setText("");
                                    break;

                                case "2":
                                    //Caso a resposta do servidor não seja successo
                                    //Mostrar "Toast" com mensagem de erro
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    btnLogin.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                    etLoginUsername.setText("");
                                    etLoginPassword.setText("");
                                    break;

                                default:
                                    Toast.makeText(mContext, "Ocorreu um erro na ligação ao servidor",
                                            Toast.LENGTH_LONG).show();
                                    btnLogin.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                    etLoginUsername.setText("");
                                    etLoginPassword.setText("");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            btnLogin.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            etLoginUsername.setText("");
                            etLoginPassword.setText("");
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                        btnLogin.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        etLoginUsername.setText("");
                        etLoginPassword.setText("");
                    }
                });
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
                finishAffinity();
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