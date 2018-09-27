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

public class RegistoActivity extends AppCompatActivity {

    private static final String TAG = "SUECADALOG";

    EditText etUsernameRegisto, etEmailRegisto, etNomeProprio, etApelido, etNovaPassword, etConfirmarNovaPassword;
    Button btnCriarUser;
    Context ctx = this;
    ProgressBar spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        spinner = findViewById(R.id.progressBar1);
        etUsernameRegisto = findViewById(R.id.etNovoUsername);
        etEmailRegisto = findViewById(R.id.etEmail);
        etNomeProprio = findViewById(R.id.etNomeProprio);
        etApelido = findViewById(R.id.etApelido);
        etNovaPassword = findViewById(R.id.eTNovaPassword);
        etConfirmarNovaPassword = findViewById(R.id.eTConfirmarPassword);
        btnCriarUser = findViewById(R.id.btnEfetuarRegisto);
        buttonEffect(btnCriarUser);
        spinner.setVisibility(View.INVISIBLE);
    }


    public void registar(View v) {

        final String username = etUsernameRegisto.getText().toString().trim();
        final String email = etEmailRegisto.getText().toString().trim();
        final String nomeProprio = etNomeProprio.getText().toString().trim();
        final String apelido = etApelido.getText().toString().trim();
        final String password = etNovaPassword.getText().toString().trim();
        final String confirmarPassword = etConfirmarNovaPassword.getText().toString().trim();
        final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //limpar avisos de erro nos campos que possam ter tido
        //erros num clique anterior do botão "Registar"
        etUsernameRegisto.setError(null);
        etEmailRegisto.setError(null);
        etNomeProprio.setError(null);
        etNovaPassword.setError(null);
        etConfirmarNovaPassword.setError(null);

        String regexUserPass="([0-9_.-]*[a-zA-Z]+[0-9_.-]*)+";
        String regexNomes="[a-zA-ZàáãâèéêíìòóõôùúÀÁÃÂÈÉÊÌÍÒÓÕÔÙÚ]+( [a-zA-ZàáãâèéêíìòóõôùúÀÁÃÂÈÉÊÌÍÒÓÕÔÙÚ]+)*";

        //Usado para contar o número de campos preenchidos corretamente. Será incrementado
        //sempre que se verifique que um campo foi preenchido com sucesso.
        //Só é possível submeter um novo registo caso os 6 campos estejam preenchidos corretamente
        int numCamposCorretos=0;

        //Verificar condições do username
        if (username.length()<2 || !username.matches(regexUserPass)) {
            etUsernameRegisto.setError("Min. 2 caracteres Max. 11 e pelo menos 1 letra. " +
                    "Pode conter números, pontos, underscores e hífens");
        }
        else {numCamposCorretos++;}

        //Verificar se email tem formato válido
        if(!emailChecker(email)){
            etEmailRegisto.setError("Endereço de email inválido!");
        }
        else{numCamposCorretos++;}

        //Verificar se nome próprio é válido
        if(!nomeProprio.matches(regexNomes)){
            etNomeProprio.setError("Caractere(s) inválido(s)!");
        }
        else{numCamposCorretos++;}

        //verificar se apelido tem caracteres inválidos
        if(!apelido.matches(regexNomes))
            etApelido.setError("Caractere(s) Inválido(s)!");
        else{numCamposCorretos++;}

        //Verificar condições da pass
        if(password.length()<6 || !password.matches(regexUserPass)){
            etNovaPassword.setError("A password deve conter no mínimo 6 caracteres e pelo menos uma letra. " +
                    "São também válidos números, pontos, hífens e underscores");
        }
        else{numCamposCorretos++;}

        //Verificar se password e confirmação de password coincidem
        //Caso a password esteja de acordo com os critérios verifica se o campo de confirmação coincide
        if (!password.equals(confirmarPassword)) {
            etConfirmarNovaPassword.setError("As passwords não coincidem!");
        }
        else{numCamposCorretos++;}

        if (password.equals(confirmarPassword)&&numCamposCorretos==6) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                switchViewVisibility();
                                Log.d(TAG,"mensagem" + response);
                                JSONObject jsonObject = new JSONObject(response);
                                String sucesso = jsonObject.getString("sucesso");
                                String mensagemResposta = jsonObject.getString("mensagem");

                                switch (sucesso) {
                                    case "1":
                                        Toast.makeText(RegistoActivity.this, mensagemResposta, Toast.LENGTH_LONG).show();
                                        finish();
                                        break;
                                    case "-1":
                                        mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                        etUsernameRegisto.setError(mensagemResposta);
                                        switchViewVisibility();
                                        break;
                                    case "-2":
                                        mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                        etEmailRegisto.setError(mensagemResposta);
                                        switchViewVisibility();
                                        break;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(RegistoActivity.this, "Ocorreu um erro: " + e.toString(),
                                        Toast.LENGTH_LONG).show();
                                mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                switchViewVisibility();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistoActivity.this, "Ocorreu um erro. " +
                                            "Verifique a sua ligação à Internet!", Toast.LENGTH_LONG).show();
                                    //+ error.toString(), Toast.LENGTH_LONG).show();
                            switchViewVisibility();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("nome", nomeProprio);
                    params.put("apelido", apelido);
                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else{/*do nothing. Permitir que o utilizador corrija os campos errados*/}
    }

    //método para alternar visibilidade do botao "Registar"
    //e do spinner
    private void switchViewVisibility()
    {
        //caso o botao esteja visivel, alterar para o spinner
        if(btnCriarUser.getVisibility()==View.VISIBLE){
            btnCriarUser.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);
        }
        //caso o spinner esteja visivel alterar para o botao
        else{
            btnCriarUser.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
        }
    }

    //método para verificar se email inserido tem formato válido
    private static boolean emailChecker(String emailString)
    {
        return emailString != null && android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches();
    }
}