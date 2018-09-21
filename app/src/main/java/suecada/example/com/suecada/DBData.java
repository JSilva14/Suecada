package suecada.example.com.suecada;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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

public class DBData {


    private String TAG = "tagdb";
    private List<String> JOGADORES = new ArrayList<>();

    //passar o contexto da ativity onde este método será chamado para poder utilizar SharedPreferences
    //este método é utilizado para consultar na BD todos os jogadores do grupo onde o utilizador está logado no momento
    //e devolver uma ArrayList com esses dados
    protected List<String> getJogadores(final Context activityContext) {

        SharedPreferences sharedPreferences = activityContext.getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String grupoAtualID=sharedPreferences.getString(Config.GRUPOID_SHARED_PREF,"Not Available");
        String url = Config.JOGADORES_URL+grupoAtualID;
        //Toast.makeText(this, url,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                //JogadoresList=showJSON(response);

                int i;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY_JOGADORES);
                    for(i=0; i<result.length(); i++) {
                        JSONObject grupoData = result.getJSONObject(i);


                        JOGADORES.add(grupoData.getString(Config.KEY_NOME));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TESTE LOG conteudo arraylist
                StringBuilder sb = new StringBuilder();
                for (String s : JOGADORES){
                    sb.append(s);
                }
                if(JOGADORES.isEmpty())
                    Log.d("tagtemparray",sb.toString()+" vazio ");
                else
                    Log.d("tagtemparray",sb.toString()+ " nao vazio");


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activityContext, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(activityContext);
        requestQueue.add(stringRequest);

        //TESTE LOG conteudo arraylist
        StringBuilder sb = new StringBuilder();
        for (String s : JOGADORES){
            sb.append(s);
        }
        if(JOGADORES.isEmpty())
            Log.d(TAG,sb.toString()+" vazio " + url);
        else
            Log.d(TAG,sb.toString()+ " nao vazio");
        return JOGADORES;

    }

    private List<String> showJSON(String response){
        int i;
        List<String> temparray = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY_JOGADORES);
            for(i=0; i<result.length(); i++) {
                JSONObject grupoData = result.getJSONObject(i);


                temparray.add(grupoData.getString(Config.KEY_NOME));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TESTE LOG conteudo arraylist
        StringBuilder sb = new StringBuilder();
        for (String s : temparray){
            sb.append(s);
        }
        if(temparray.isEmpty())
            Log.d("tagtemparray",sb.toString()+" vazio ");
        else
            Log.d("tagtemparray",sb.toString()+ " nao vazio");

        return temparray;
    }
}
