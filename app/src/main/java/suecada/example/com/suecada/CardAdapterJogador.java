package suecada.example.com.suecada;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class CardAdapterJogador extends RecyclerView.Adapter<CardAdapterJogador.ViewHolder> {

    private Context mContext;

    //guardar todos os jogadores recebidos
    List<Jogador> jogadores;
    CardView cardView;

    //Constructor of this class
    public CardAdapterJogador(List<Jogador> jogadores, Context mContext) {
        super();
        //Getting all superheroes
        this.jogadores = jogadores;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jogador_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Escolher item especifico da lista
        Jogador jogador = jogadores.get(position);
        final String idJogador = jogador.getId();
        final String usernameJogador = jogador.getUsername();
        final String nomeJogador = jogador.getNome();
        final String apelidoJogador = jogador.getApelido();
        final String emailJogador = jogador.getEmail();
        final String pontuacaoJogador = jogador.getPontuacao();
        final String nomeCompletoJogador = nomeJogador+' '+apelidoJogador;

        //Mostrar dados nas Views


        holder.tvNomeJogador.setText(nomeCompletoJogador);
        holder.tvUsernameJogador.setText(usernameJogador);
        holder.tvPontuacaoJogador.setText(pontuacaoJogador);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //TODO: DO SOMETHING ON CLICK


            }
        });
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Views
        public TextView tvNomeJogador, tvUsernameJogador, tvPontuacaoJogador;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            tvNomeJogador = itemView.findViewById(R.id.tvJogadorNome);
            tvUsernameJogador = itemView.findViewById(R.id.tvJogadorUsername);
            tvPontuacaoJogador = itemView.findViewById(R.id.tvJogadorPontuacao);
            cardView = itemView.findViewById(R.id.jogadorRow);
        }
    }
}