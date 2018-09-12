package suecada.example.com.suecada;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class GerirGrupoActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private String TAG ="taggerir";

    private RecyclerView rvListaJogadores;
    private Button btnAdicionarJogador;
    private Button btnEliminarJogador;
    private MyRecyclerViewAdapter rvAdapter;
    private Context mContext=this;

    List<String> JOGADORES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_grupo);

        rvListaJogadores = (RecyclerView) findViewById(R.id.rvListaJogadores);
        rvListaJogadores.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new MyRecyclerViewAdapter(this, JOGADORES);
        rvAdapter.setClickListener(this);
        rvListaJogadores.setAdapter(rvAdapter);

        /*DBData dbData = new DBData();
        JogadoresList=dbData.getJogadores(mContext);*/
        JOGADORES.clear();
        getJogadores();



        //TESTE LOG conteudo arraylist
        StringBuilder sb = new StringBuilder();
        for (String s : JOGADORES){
            sb.append(s);
        }
        if(JOGADORES.isEmpty())
            Log.d(TAG,sb.toString()+"vazio");
        else
            Log.d(TAG,sb.toString() + "nao vazio");

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicaste em " + rvAdapter.getItem(position) + " na fila numero: " + position, Toast.LENGTH_SHORT).show();
    }


    private void getJogadores() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String grupoAtualID=sharedPreferences.getString(Config.GRUPOID_SHARED_PREF,"Not Available");
        String url = Config.JOGADORES_URL+grupoAtualID;
        //Toast.makeText(this, url,Toast.LENGTH_LONG).show();

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
                        Toast.makeText(GerirGrupoActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String nome="";
        int i;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY_JOGADORES);
            for(i=0; i<result.length(); i++) {
                JSONObject grupoData = result.getJSONObject(i);

                nome += grupoData.getString(Config.KEY_NOME);
                JOGADORES.add(grupoData.getString(Config.KEY_NOME));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(GerirGrupoActivity.this, "NOMES= " +nome,Toast.LENGTH_LONG).show();

    }

}


