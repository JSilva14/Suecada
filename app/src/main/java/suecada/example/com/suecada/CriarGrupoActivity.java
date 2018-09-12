package suecada.example.com.suecada;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class CriarGrupoActivity extends AppCompatActivity {

    private static final String TAG = "SUECADALOG";

    EditText eTNovoGrupo, eTNovaPassword, eTConfirmarPassword;

    Context ctx = this;
    ProgressBar spinner;
    Button criarGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_criar_grupo);

        spinner = (ProgressBar) findViewById(suecada.example.com.suecada.R.id.progressBar1);
        eTNovoGrupo = (EditText) findViewById(suecada.example.com.suecada.R.id.eTNomeNovoGrupo);
        eTNovaPassword = (EditText) findViewById(suecada.example.com.suecada.R.id.eTNovaPassword);
        eTConfirmarPassword = (EditText) findViewById(suecada.example.com.suecada.R.id.eTConfirmarPassword);
        criarGrupo = (Button) findViewById(suecada.example.com.suecada.R.id.btnCriarNovoGrupo);
        buttonEffect(criarGrupo);
        spinner.setVisibility(View.INVISIBLE);
    }


    public void registar(View v) {
        criarGrupo.setVisibility(View.GONE);
        final String grupo = eTNovoGrupo.getText().toString();
        final String password = eTNovaPassword.getText().toString();
        final String confirmarPassword = eTConfirmarPassword.getText().toString();
        final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Verificar se os campos estão vazios
        if (grupo.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            Toast.makeText(ctx, "Preencha todos os campos.", Toast.LENGTH_LONG).show();
        }
        //Verificar se o nome e pass tem os carateres permitidos
        else if (!grupo.matches("[a-zA-Z0-9_]+") || !password.matches("[a-zA-Z0-9_]+")) {
            Toast.makeText(ctx, "Utilize apenas letras maiusculas ou minúsculas de \"A\" a \"Z\" e underscores.", Toast.LENGTH_LONG).show();
        }
        //Verificar se password e confirmação de password coincidem
        else if (!password.equals(confirmarPassword)) {
            Toast.makeText(ctx, "As passwords não coincidem.", Toast.LENGTH_LONG).show();
        } else if (password.equals(confirmarPassword)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d(TAG,"mensagem" + response);
                                JSONObject jsonObject = new JSONObject(response);
                                String sucesso = jsonObject.getString("sucesso");
                                String mensagemErro = jsonObject.getString("mensagem");

                                if (sucesso.equals("1")) {
                                    Toast.makeText(CriarGrupoActivity.this, mensagemErro, Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else if (sucesso.equals("-1")){
                                    mgr.hideSoftInputFromWindow(eTNovaPassword.getWindowToken(), 0);
                                    Toast.makeText(CriarGrupoActivity.this, mensagemErro, Toast.LENGTH_LONG).show();
                                    spinner.setVisibility(View.GONE);
                                    criarGrupo.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CriarGrupoActivity.this, "Ocorreu um erro: " + e.toString(), Toast.LENGTH_LONG).show();
                                mgr.hideSoftInputFromWindow(eTNovaPassword.getWindowToken(), 0);
                                spinner.setVisibility(View.GONE);
                                criarGrupo.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CriarGrupoActivity.this, "Ocorreu um erro: " + error.toString(), Toast.LENGTH_LONG).show();
                            spinner.setVisibility(View.GONE);
                            criarGrupo.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("grupo", grupo);
                    params.put("password", password);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            mgr.hideSoftInputFromWindow(eTNovaPassword.getWindowToken(), 0);
            spinner.setVisibility(View.VISIBLE);
        }
    }
}