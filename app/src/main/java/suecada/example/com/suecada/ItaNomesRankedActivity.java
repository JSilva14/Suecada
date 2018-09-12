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

    List<String> JogadoresList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_ita_nomes_ranked);

        Button seguinte = (Button) findViewById(suecada.example.com.suecada.R.id.btnrSeguinte);
        buttonEffect(seguinte);
        JogadoresList.clear();
        getJogadoresList();




       ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, JogadoresList);

        AutoCompleteTextView j1 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj1);
        AutoCompleteTextView j2 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj2);
        AutoCompleteTextView j3 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj3);
        AutoCompleteTextView j4 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj4);
        AutoCompleteTextView j5 = (AutoCompleteTextView)
                findViewById(suecada.example.com.suecada.R.id.actvj5);

        j1.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j2.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j3.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j4.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);
        j5.setDropDownBackgroundResource(suecada.example.com.suecada.R.color.primary_light);

        j1.setAdapter(adapter);
        j2.setAdapter(adapter);
        j3.setAdapter(adapter);
        j4.setAdapter(adapter);
        j5.setAdapter(adapter);

        //TESTE LOG conteudo arraylist
        StringBuilder sb = new StringBuilder();
        for (String s : JogadoresList){
            sb.append(s);
        }
        if(JogadoresList.size()==0)
            Log.d("tagitaliana",sb.toString()+"vazio");
        else
            Log.d("tagitaliana",sb.toString() + "nao vazio");

    }

    private void getJogadoresList() {

        SharedPreferences sharedPreferences = ItaNomesRankedActivity.this.getSharedPreferences(
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
                        Toast.makeText(ItaNomesRankedActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
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

                //APAGAR TESTE
                int tamanho_inicial =0;
                int tamanho_final=0;

                JSONObject grupoData = result.getJSONObject(i);

                nome += grupoData.getString(Config.KEY_NOME);
                JogadoresList.add(grupoData.getString(Config.KEY_NOME));
                tamanho_final= JogadoresList.size();

                StringBuilder sb = new StringBuilder();
                for (String s : JogadoresList){
                    sb.append(s);
                }
                if (tamanho_final>tamanho_inicial) {
                    tamanho_inicial += 1;
                    Log.d("tagitaliana", "Posiçao " + i+1 + sb + "adicionado à Lista");
                }
                else{
                    Log.d("tagitaliana", "O tamanho do array nao aumentou. Magia Negra");
                }





            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(ItaNomesRankedActivity.this, "NOMES= " +nome,Toast.LENGTH_LONG).show();

    }



    public Intent guardaNomesRanked(View view) {

        this.j1 = (AutoCompleteTextView) findViewById(suecada.example.com.suecada.R.id.actvj1);
        this.j2 = (AutoCompleteTextView) findViewById(suecada.example.com.suecada.R.id.actvj2);
        this.j3 = (AutoCompleteTextView) findViewById(suecada.example.com.suecada.R.id.actvj3);
        this.j4 = (AutoCompleteTextView) findViewById(suecada.example.com.suecada.R.id.actvj4);
        this.j5 = (AutoCompleteTextView) findViewById(suecada.example.com.suecada.R.id.actvj5);


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
        else if(!JogadoresList.contains(this.j1n) || !JogadoresList.contains(this.j2n) || !JogadoresList.contains(this.j3n)
                || !JogadoresList.contains(this.j4n)|| !JogadoresList.contains(this.j5n)){
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
