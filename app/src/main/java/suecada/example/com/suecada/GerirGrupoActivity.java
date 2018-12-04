package suecada.example.com.suecada;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class GerirGrupoActivity extends AppCompatActivity  {


    private Button btnAdicionarJogador;
    private Button btnEliminarJogador;

    private RecyclerView rvListaJogadores;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private Context mContext=this;

    List<Jogador> listaJogadores;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_grupo);

        sharedPreferences = GerirGrupoActivity.this.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        mSwipeRefreshLayout = findViewById(R.id.jogadoresSwipeRefreshLayout);

        rvListaJogadores = findViewById(R.id.rvListaJogadores);
        rvListaJogadores.setItemAnimator(new DefaultItemAnimator());
        rvListaJogadores.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvListaJogadores.setLayoutManager(layoutManager);

        listaJogadores = new ArrayList<>();

        adapter = new CardAdapterJogador(listaJogadores, mContext);

        rvListaJogadores.setAdapter(adapter);

        //rvAdapter.setClickListener(this);

        getJogadores();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getJogadores();
            }
        });

        mSwipeRefreshLayout.setNestedScrollingEnabled(true);
    }


    private void getJogadores() {


        //Criar Hashmap com os parametros que queremos colocar no pedido à BD
        Map<String, String> parametros = new HashMap<>();
        parametros.put("nome", sharedPreferences.getString(Config.GRUPONOME_SHARED_PREF,
                Config.GRUPONOME_SHARED_PREF));

        //Instanciar DBData para efetuar um request à API
        DBData dbData = new DBData();

        dbData.fetchResponse(mContext, Config.JOGADORES_URL,
                parametros, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String resposta) {

                        listaJogadores.clear();
                        adapter.notifyDataSetChanged();

                        Log.d("RESPOSTA: ", resposta);

                        try {
                            //obter o JSON "result" na resposta JSON
                            JSONObject result = new JSONObject(resposta);

                            //Extrair json object "status" do objeto "result"
                            JSONObject requestStatus = result.getJSONObject("Status");
                            String sucesso = requestStatus.getString("sucesso");
                            String mensagemResposta = requestStatus.getString("mensagem");

                            switch (sucesso) {

                                case "1":

                                    //Extrair informação dos grupos da resposta JSON
                                    JSONArray arrayJogadores = result.getJSONArray("Jogadores");

                                    for(int i=0; i<arrayJogadores.length(); i++) {
                                        Jogador jogador = new Jogador();
                                        try {
                                            JSONObject jogadorData = arrayJogadores.getJSONObject(i);

                                            jogador.setId(jogadorData.getString("id"));
                                            jogador.setUsername(jogadorData.getString("username"));
                                            jogador.setNome(jogadorData.getString("nome"));
                                            jogador.setApelido(jogadorData.getString("apelido"));
                                            jogador.setPontuacao(jogadorData.getString("pontuacao"));
                                            jogador.setEmail(jogadorData.getString("email"));

                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                        listaJogadores.add(jogador);

                                    }
                                    adapter.notifyDataSetChanged();
                                    mSwipeRefreshLayout.setRefreshing(false);

                                    break;

                                case "0":
                                    //Caso a resposta do servidor não seja successo
                                    //Mostrar "Toast" com mensagem de erro
                                    Toast.makeText(mContext, mensagemResposta,
                                            Toast.LENGTH_LONG).show();
                                    break;


                                default:
                                    break;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        /*int i;

                        try {
                            JSONObject jsonObject = new JSONObject(resposta);
                            JSONArray result = jsonObject.getJSONArray("resultjogadores");
                            for(i=0; i<result.length(); i++) {
                                Jogador jogador = new Jogador();

                                try{}
                                JSONObject resultJogadores = result.getJSONObject(i);

                                grupo.setNome(grupoData.getString("nomeGrupo"));
                                grupo.setNumJogadores(grupoData.getString("numJogadores"));
                                grupo.setFlgAdmin(grupoData.getString("flgAdmin"));

                                rvListaJogadores.setAdapter(rvAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(GerirGrupoActivity.this, "NOMES= " +nome,Toast.LENGTH_LONG).show();*/
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}


