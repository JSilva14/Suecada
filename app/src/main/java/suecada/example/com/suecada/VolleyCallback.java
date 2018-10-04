package suecada.example.com.suecada;

//Interface para captar dados obtidos no método onResponse do StringRequest
public interface VolleyCallback {
    String onSuccessResponse(String result);
}
