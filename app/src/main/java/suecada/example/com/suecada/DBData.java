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

import org.json.JSONException;

import java.util.Map;

//classe para facilitar pedidos volley à API
public class DBData implements VolleyCallback{

    //Context da activity DBData onde é instanciado
    private Context activityContext;
    private String requestURL;
    private Map<String, String> parametrosRequest;
    private String data;

    //construtor
    public DBData(Context activityContext, String requestURL, Map<String, String> parametrosRequest)
    {
        this.activityContext = activityContext;
        this.requestURL = requestURL;
        this.parametrosRequest = parametrosRequest;

    }

//a única forma de captar a resposta obtida no método onResponse abaixo é utilizando um callback
public void fetchResponse(final VolleyCallback volleycallback) {
     StringRequest stringRequest = new StringRequest(Request.Method.POST,
            requestURL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                volleycallback.onSuccessResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activityContext, error.getMessage(), Toast.LENGTH_LONG).show();
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

    //utilizar a resposta obtida em fetchResponse

        @Override
            public String onSuccessResponse(String result) {
                    String data=result;
                    return data;
            }




}


