package suecada.example.com.suecada;

class Config {

    //URL servidor
    private static final String SERVER_URL = "http://192.168.1.6/suecada/";

    //URL to our login.php file
    static final String LOGIN_URL = SERVER_URL + "/loginhash.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    static final String KEY_GRUPO = "grupo";
    static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    static final String SHARED_PREF_NAME = "suecadalogin";

    //This would be used to store the email of current logged in user
    static final String GRUPO_SHARED_PREF = "grupo";

    static final String GRUPOID_SHARED_PREF = "id";


    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    static final String LOGGEDIN_SHARED_PREF = "loggedin";

    static final String DATA_URL = SERVER_URL + "getIdGrupo.php?nome=";
    static final String KEY_ID = "id";
    static final String JSON_ARRAY = "result";

    static final String JOGADORES_URL = SERVER_URL + "getJogadores.php?id=";
    static final String KEY_NOME = "nome";
    static final String JSON_ARRAY_JOGADORES = "resultjogadores";

    static final String REGISTO_URL = SERVER_URL + "registo.php";

}