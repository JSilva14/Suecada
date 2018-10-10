package suecada.example.com.suecada;

import android.content.Context;
import android.util.Log;
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

import java.util.Map;

//classe para facilitar pedidos volley à API
public class DBData implements VolleyCallback {

    //construtor
    public DBData() {
    }

    //a única forma de captar a resposta obtida no método onResponse abaixo é utilizando um callback
    //que executa quando é recebida uma resposta do servidor
    public void fetchResponse(final Context activityContext, final String requestURL,
                              final Map<String, String> parametrosRequest, final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    callback.onSuccessResponse(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                //parametros da request à API
                return parametrosRequest;
            }
        };
        //adicionar request à queue
        RequestQueue requestQueue = Volley.newRequestQueue(activityContext);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onSuccessResponse(String result) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}


