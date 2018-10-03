package suecada.example.com.suecada;

class Config {

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    static final String SHARED_PREF_NAME = "suecadalogin";

    //URL servidor
    private static final String SERVER_URL = "http://35.178.49.133/suecada/";

    //URL to php files
    static final String LOGIN_URL = SERVER_URL + "/login.php";
    static final String REGISTO_URL = SERVER_URL + "registo.php";
    static final String JOGADOR_ATUAL_URL = SERVER_URL + "getInfoJogador.php";
    static final String JOGADORES_URL = SERVER_URL + "getJogadores.php?id=";

    //If server response is equal to this that means login is successful
    static final String LOGIN_SUCCESS = "success";

    //Keys for username and password as defined in our $_POST['key'] in login.php
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";

    static final String KEY_GRUPO = "grupo";


    //This would be used to store the username of current logged in user
    static final String USERNAME_SHARED_PREF = "";
    static final String JOGADORID_SHARED_PREF = "";


    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    static final String LOGGEDIN_SHARED_PREF = "loggedin";


    static final String KEY_ID = "id";
    static final String JSON_ARRAY = "result";


    static final String KEY_NOME = "nome";
    static final String JSON_ARRAY_JOGADORES = "resultjogadores";



}