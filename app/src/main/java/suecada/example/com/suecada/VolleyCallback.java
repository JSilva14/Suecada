package suecada.example.com.suecada;

import com.android.volley.VolleyError;

//Interface para captar dados obtidos no método onResponse do StringRequest
public interface VolleyCallback {
    void onSuccessResponse(String result);
    void onErrorResponse(VolleyError error);
}
