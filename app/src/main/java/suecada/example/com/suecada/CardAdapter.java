package suecada.example.com.suecada;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context mContext;

    //guardar todos os grupos recebidos
    List<Grupo> grupos;
    CardView cardView;

    //Constructor of this class
    public CardAdapter(List<Grupo> grupos, Context mContext) {
        super();
        //Getting all superheroes
        this.grupos = grupos;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gupo_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Escolher item especifico da lista
        Grupo grupo = grupos.get(position);
        final String nomeGrupo = grupo.getNome();
        String numJogadores = grupo.getNumJogadores();
        final String flgAdmin;
        if(grupo.getFlgAdmin().equals("1"))
            flgAdmin="Administrador";
        else
            flgAdmin="Membro";

        //Mostrar dados nas Views
        holder.tvNomeGrupo.setText(nomeGrupo);
        holder.tvNumJogadores.setText(numJogadores);
        holder.tvAdmin.setText(flgAdmin);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext, MenuGrupoActivity.class);
                myIntent.putExtra("nomeGrupo",nomeGrupo);
                myIntent.putExtra("permissoes",flgAdmin);

                mContext.startActivity(myIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Views
        public TextView tvNomeGrupo, tvNumJogadores, tvAdmin;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            tvNomeGrupo = itemView.findViewById(R.id.tvNomeGrupo);
            tvNumJogadores = itemView.findViewById(R.id.tvNumJogadores);
            tvAdmin = itemView.findViewById(R.id.tvAdmin);
            cardView = itemView.findViewById(R.id.grupo_row);
        }
    }
}