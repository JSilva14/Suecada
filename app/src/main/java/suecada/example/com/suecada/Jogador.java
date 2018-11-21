package suecada.example.com.suecada;

//Classe grupo utilizada para instanciar grupos com informação recebida
//do servidor acerca dos grupos a que um jogador pertence
public class Jogador {

    private String id;
    private String username;
    private String nome;
    private String apelido;
    private String email;
    private String pontuacao;

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {this.apelido = apelido;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPontuacao() {return pontuacao;}

    public void setPontuacao(String pontuacao) {this.pontuacao = pontuacao;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

}
