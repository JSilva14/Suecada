package suecada.example.com.suecada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class ItaNomesRankedActivity extends AppCompatActivity {

    AutoCompleteTextView j1, j2, j3, j4, j5;
    String j1n, j2n, j3n, j4n, j5n;

    List<String> listaJogadores = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_ita_nomes_ranked);

        SharedPreferences sharedPreferences = ItaNomesRankedActivity.this.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String grupoAtualID=sharedPreferences.getString(Config.JOGADORID_SHARED_PREF,"Not Available");
        String url = Config.JOGADORES_URL+grupoAtualID;

        Button seguinte = (Button) findViewById(suecada.example.com.suecada.R.id.btnrSeguinte);
        buttonEffect(seguinte);

        j1 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj1);
        j2 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj2);
        j3 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj3);
        j4 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj4);
        j5 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj5);

        j1.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j2.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j3.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j4.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j5.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);

        preencherJogadoresList(url);
    }

    /*private List<String> getJogadoresGrupo(String url) {

        return;
    }*/

    private void preencherJogadoresList(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("resultJogadores");

                    //preencher um JSON Object com os nomes dos jogadores do grupo atual
                    //e adicionar os nomes à List listaJogadores
                    for(int i=0; i<result.length(); i++) {

                        JSONObject grupoData = result.getJSONObject(i);

                        //nome += grupoData.getString(Config.KEY_NOME);
                       listaJogadores.add(grupoData.getString("nome"));

                       //Associar List ao adapter
                       adapter = new ArrayAdapter<String>(mContext,
                                android.R.layout.simple_dropdown_item_1line, listaJogadores);

                       //associar adapter às AutoCompleteTextViews
                        j1.setAdapter(adapter);
                        j2.setAdapter(adapter);
                        j3.setAdapter(adapter);
                        j4.setAdapter(adapter);
                        j5.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItaNomesRankedActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);

    }



    public Intent guardaNomesRanked(View view) {

        this.j1n = this.j1.getText().toString();
        this.j2n = this.j2.getText().toString();
        this.j3n = this.j3.getText().toString();
        this.j4n = this.j4.getText().toString();
        this.j5n = this.j5.getText().toString();

        Intent i = new Intent(this, ItaPontosRankedActivity.class);

        if (this.j1n.equals("") || this.j2n.equals("") || this.j3n.equals("") || this.j4n.equals("") || this.j5n.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else if (this.j1n.equals(this.j2n) || this.j1n.equals(this.j3n) || this.j1n.equals(this.j4n) || this.j1n.equals(this.j5n) || this.j2n.equals(this.j3n) || this.j2n.equals(this.j4n) || this.j2n.equals(this.j5n) || this.j3n.equals(this.j4n) || this.j3n.equals(this.j5n) || this.j4n.equals(this.j5n)) {
            Toast.makeText(getApplicationContext(), "Nome de jogador repetido!", Toast.LENGTH_SHORT).show();
        }
        else if(!listaJogadores.contains(this.j1n) || !listaJogadores.contains(this.j2n) || !listaJogadores.contains(this.j3n)
                || !listaJogadores.contains(this.j4n)|| !listaJogadores.contains(this.j5n)){
            Toast.makeText(getApplicationContext(), "Jogador não existente!", Toast.LENGTH_SHORT).show();
        } else {
            i.putExtra("jogador1", this.j1n);
            i.putExtra("jogador2", this.j2n);
            i.putExtra("jogador3", this.j3n);
            i.putExtra("jogador4", this.j4n);
            i.putExtra("jogador5", this.j5n);
            startActivity(i);
            finish();
            return i;
        }
        return null;
    }


}
