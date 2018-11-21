package suecada.example.com.suecada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class ItaNomesRankedActivity extends AppCompatActivity {

    AutoCompleteTextView actvj1, actvj2, actvj3, actvj4, actvj5;

    List<String> listaJogadores = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Context mContext=this;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_ita_nomes_ranked);

        sharedPreferences = ItaNomesRankedActivity.this.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Button seguinte = findViewById(suecada.example.com.suecada.R.id.btnrSeguinte);
        buttonEffect(seguinte);

        seguinte.setOnClickListener(iniciarSessaoClickListener);

        actvj1 = findViewById(suecada.example.com.suecada.R.id.actvj1);
        actvj2 = findViewById(suecada.example.com.suecada.R.id.actvj2);
        actvj3 = findViewById(suecada.example.com.suecada.R.id.actvj3);
        actvj4 = findViewById(suecada.example.com.suecada.R.id.actvj4);
        actvj5 = findViewById(suecada.example.com.suecada.R.id.actvj5);

        actvj1.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        actvj2.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        actvj3.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        actvj4.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        actvj5.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);

        preencherJogadoresList();
    }


    private void preencherJogadoresList(){


        //Criar Hashmap com os parametros que queremos colocar no pedido à BD
        Map<String, String> parametros = new HashMap<>();
        parametros.put("nome", sharedPreferences.getString(Config.GRUPONOME_SHARED_PREF,
                Config.GRUPONOME_SHARED_PREF));

        //Instanciar DBData para efetuar um request à API
        DBData dbData = new DBData();

        dbData.fetchResponse(mContext, Config.JOGADORES_DISPONIVEIS_URL,
                parametros, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String resposta) {
                    try {
                    JSONObject jsonObject = new JSONObject(resposta);
                    JSONArray result = jsonObject.getJSONArray("resultjogadores");

                    //preencher um JSON Object com os nomes dos jogadores do grupo atual
                    //e adicionar os nomes à List listaJogadores
                    for(int i=0; i<result.length(); i++) {

                        JSONObject grupoData = result.getJSONObject(i);

                        //nome += grupoData.getString(Config.KEY_NOME);
                       listaJogadores.add(grupoData.getString("nome"));

                       //Associar List ao adapter
                       adapter = new ArrayAdapter<>(mContext,
                                android.R.layout.simple_dropdown_item_1line, listaJogadores);

                       //associar adapter às AutoCompleteTextViews
                        actvj1.setAdapter(adapter);
                        actvj2.setAdapter(adapter);
                        actvj3.setAdapter(adapter);
                        actvj4.setAdapter(adapter);
                        actvj5.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void guardaNomesRanked(String j1, String j2, String j3, String j4, String j5) {

        if (j1.equals("") || j2.equals("") || j3.equals("") || j4.equals("") || j5.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else if (j1.equals(j2) || j1.equals(j3) || j1.equals(j4) || j1.equals(j5) || j2.equals(j3) || j2.equals(j4) || j2.equals(j5) || j3.equals(j4) || j3.equals(j5) || j4.equals(j5)) {
            Toast.makeText(getApplicationContext(), "Nome de jogador repetido!", Toast.LENGTH_SHORT).show();
        }
        else if(!listaJogadores.contains(j1) || !listaJogadores.contains(j2) || !listaJogadores.contains(j3)
                || !listaJogadores.contains(j4)|| !listaJogadores.contains(j5)){
            Toast.makeText(getApplicationContext(), "Jogador não existente!", Toast.LENGTH_SHORT).show();
        } else {
            iniciarSessaoItaliana(j1, j2, j3, j4, j5);
        }
    }


    public void iniciarSessaoItaliana(final String j1, final String j2, final String j3,
                                      final String j4, final String j5){

        //gerar uuid para utilizar como id de integraçao da sessão
        //Chances de haver uuids (gerados aleatoriamente) duplicados numa lista de 103 triliões é de 1 em 1 bilião.
        final String uuid = UUID.randomUUID().toString();

        //Criar Hashmap com os parametros que queremos colocar no pedido à BD
        Map<String, String> parametros = new HashMap<>();
        parametros.put("id_grupo", sharedPreferences.getString(Config.GRUPOID_SHARED_PREF,
                Config.GRUPOID_SHARED_PREF));
        parametros.put("UUID", uuid);
        parametros.put("criador", sharedPreferences.getString(Config.JOGADORUSERNAME_SHARED_PREF,
                "usernameJogador"));
        parametros.put("jogador1", j1);
        parametros.put("jogador2", j2);
        parametros.put("jogador3", j3);
        parametros.put("jogador4", j4);
        parametros.put("jogador5", j5);

        //Instanciar DBData para efetuar um request à API
        DBData dbData = new DBData();

        dbData.fetchResponse(mContext, Config.INICIAR_SESSAO_URL,
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

                                //Sucesso
                                case "1":
                                    //Iniciar nova sessão de jogo
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();

                                    //Iniciar nova activity
                                    Intent i = new Intent(mContext, ItaPontosRankedActivity.class);
                                    i.putExtra("jogador1", j1);
                                    i.putExtra("jogador2", j2);
                                    i.putExtra("jogador3", j3);
                                    i.putExtra("jogador4", j4);
                                    i.putExtra("jogador5", j5);
                                    i.putExtra("uuid", uuid);

                                    startActivity(i);
                                    finish();

                                    break;

                                //Caso um dos jogadores já se encontre numa sessão
                                case "-1":
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    break;

                                //Erro
                                case "0":
                                    //Caso a resposta do servidor não seja successo
                                    //Mostrar "Toast" com mensagem de erro
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    break;

                                default:
                                    Toast.makeText(mContext, "Ocorreu um erro na ligação ao servidor",
                                            Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private View.OnClickListener iniciarSessaoClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            String j1,j2,j3,j4,j5;
            j1= actvj1.getText().toString();
            j2= actvj2.getText().toString();
            j3= actvj3.getText().toString();
            j4= actvj4.getText().toString();
            j5= actvj5.getText().toString();

            guardaNomesRanked(j1,j2,j3,j4,j5);

        }
    };
}
