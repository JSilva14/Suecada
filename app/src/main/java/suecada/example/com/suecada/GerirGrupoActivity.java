package suecada.example.com.suecada;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerirGrupoActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    private RecyclerView rvListaJogadores;
    private Button btnAdicionarJogador;
    private Button btnEliminarJogador;
    private RecyclerViewAdapter rvAdapter;
    private Context mContext=this;

    List<String> JOGADORES = new ArrayList<>();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_grupo);

        rvListaJogadores = findViewById(R.id.rvListaJogadores);
        rvListaJogadores.setItemAnimator(new DefaultItemAnimator());
        rvListaJogadores.setLayoutManager(new LinearLayoutManager(this));


        rvAdapter = new RecyclerViewAdapter(mContext, JOGADORES, R.id.jogadorRow);
        rvAdapter.setClickListener(this);

        sharedPreferences = GerirGrupoActivity.this.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        getJogadores();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicaste em " + rvAdapter.getItem(position) + " na fila numero: " + position+1, Toast.LENGTH_SHORT).show();
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
                        String nome="";

                        int i;

                        try {
                            JSONObject jsonObject = new JSONObject(resposta);
                            JSONArray result = jsonObject.getJSONArray("resultjogadores");
                            for(i=0; i<result.length(); i++) {
                                JSONObject grupoData = result.getJSONObject(i);

                                JOGADORES.add(grupoData.getString("nome"));
                                rvListaJogadores.setAdapter(rvAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(GerirGrupoActivity.this, "NOMES= " +nome,Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}


