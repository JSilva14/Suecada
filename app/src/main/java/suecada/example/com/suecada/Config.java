package suecada.example.com.suecada;

class Config {

    //##########################
    //Keys for Sharedpreferences
    //##########################

    //This would be the name of our shared preferences
    static final String SHARED_PREF_NAME = "suecadaPreferences";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //Guardar informação acerca do jogador logado
    static final String JOGADORUSERNAME_SHARED_PREF = "usernameJogador";
    static final String JOGADORID_SHARED_PREF = "idJogador";
    static final String JOGADORNOME_SHARED_PREF = "nomeJogador";
    static final String JOGADORAPELIDO_SHARED_PREF = "apelidoJogador";
    static final String JOGADOREMAIL_SHARED_PREF = "emailJogador";

    //Informações acerco do grupo atual
    static final String GRUPOID_SHARED_PREF = "idGrupo";
    static final String GRUPONOME_SHARED_PREF = "nomeGrupo";
    static final String GRUPOCODIGOACESSO_SHARED_PREF = "codigoAcesso";
    static final String GRUPOPERMISSOES_SHARED_PREF = "permissoes";

    //Informações acerca da sessão atual
    static final String SESSAOATIVA_SHARED_PREF = "uuidSessao";

    //########################
    //      API URLS
    //########################
    //URL servidor
    private static final String SERVER_URL = "http://35.178.49.133/suecada/";
    //URL to php files
    static final String LOGIN_URL = SERVER_URL + "/login.php";
    static final String REGISTO_URL = SERVER_URL + "registo.php";
    static final String JOGADORES_DISPONIVEIS_URL = SERVER_URL + "getJogadoresDisponiveis.php";
    static final String JOGADORES_URL = SERVER_URL + "getJogadores.php";
    static final String GRUPOS_URL = SERVER_URL + "getGrupos.php";
    static final String GRUPOINFO_URL = SERVER_URL + "getInfoGrupo.php";

    static final String INICIAR_SESSAO_URL = SERVER_URL + "iniciarSessaoItaliana.php";
}