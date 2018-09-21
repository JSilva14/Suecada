package suecada.example.com.suecada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Adapter para preencher Recycler View
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // passar dados para o construtor
    MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // mostra a linha jogador_row (inflate) apenas quando necessário
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.jogador_row, parent, false);
        return new ViewHolder(view);
    }

    // Colocar dados na textview de cada jogador_row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String jogador = mData.get(position);
        holder.myTextView.setText(jogador);
    }

    // num total de items
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // guarda e recicla views quando desaparecem do ecrã
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvJogadorNome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // método para adquirir dados na posição clicada
    String getItem(int id) {
        return mData.get(id);
    }

    // listener para eventos click
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // método implementado pela activity para responder a eventos
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}