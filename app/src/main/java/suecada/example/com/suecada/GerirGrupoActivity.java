package suecada.example.com.suecada;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        rvListaJogadores = findViewById(R.id.rvListaJogadores);
        rvListaJogadores.setItemAnimator(new DefaultItemAnimator());
        rvListaJogadores.setLayoutManager(new LinearLayoutManager(this));

        getJogadores();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicaste em " + rvAdapter.getItem(position) + " na fila numero: " + position+1, Toast.LENGTH_SHORT).show();
    }


    private void getJogadores() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String grupoAtualID=sharedPreferences.getString(Config.GRUPOID_SHARED_PREF,"Not Available");
        String url = Config.JOGADORES_URL+grupoAtualID;

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

                JOGADORES.add(grupoData.getString(Config.KEY_NOME));

                rvAdapter = new MyRecyclerViewAdapter(this, JOGADORES);
                rvAdapter.setClickListener(this);
                rvListaJogadores.setAdapter(rvAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(GerirGrupoActivity.this, "NOMES= " +nome,Toast.LENGTH_LONG).show();
    }

}


