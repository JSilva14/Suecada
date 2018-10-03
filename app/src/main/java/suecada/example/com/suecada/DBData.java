package suecada.example.com.suecada;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//classe para facilitar pedidos volley à API
public class DBData {

    //Context da activity DBData onde é instanciado
    private Context activityContext;
    private String requestURL;
    private Map<String, String> parametrosRequest = new HashMap<>();
    private JSONArray result;


     private StringRequest stringRequest = new StringRequest(Request.Method.POST,
            requestURL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                //JSONObject que recebe resposta da API
                JSONObject jsonObject = new JSONObject(response);
                //obter o array "result" do JSONObject recebido acima
                result = jsonObject.getJSONArray(Config.JSON_ARRAY);


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activityContext, error.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
    {
        @Override
        protected Map<String, String> getParams() {

            //parametros da request à API
            return parametrosRequest;
        }
    };

    private JSONArray requestData() {
        RequestQueue requestQueue = Volley.newRequestQueue(activityContext);
        requestQueue.add(stringRequest);
        return result;
    }
}
