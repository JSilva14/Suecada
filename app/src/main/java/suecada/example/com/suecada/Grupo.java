package suecada.example.com.suecada;

//Classe grupo utilizada para instanciar grupos com informação recebida
//do servidor acerca dos grupos a que um jogador pertence
public class Grupo {

    private String nome;
    private String numJogadores;
    private String flgAdmin;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumJogadores() {
        return numJogadores;
    }

    public void setNumJogadores(String numJogadores) {
        this.numJogadores = numJogadores;
    }

    public String getFlgAdmin() {
        return flgAdmin;
    }

    public void setFlgAdmin(String flgAdmin) {
        this.flgAdmin = flgAdmin;
    }
}
