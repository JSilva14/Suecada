package suecada.example.com.suecada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

//Adapter para preencher Recycler View
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    //passar por parametro qual o layout que queremos inserir na recyclerview
    private int listRowToInflate;
    private LinearLayout jogadorRow;

    // passar dados para o construtor
    RecyclerViewAdapter(Context context, List<String> data, int listRowToInflate) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.listRowToInflate=listRowToInflate;
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
        holder.tvJogadorNome.setText(jogador);
        holder.tvJogadorPontuacao.setText("0");
    }

    // num total de items
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // guarda e recicla views quando desaparecem do ecrã
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvJogadorNome, tvJogadorPontuacao;
        RadioButton rbJogadorCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJogadorNome = itemView.findViewById(R.id.tvJogadorNome);
            tvJogadorPontuacao = itemView.findViewById(R.id.tvJogadorPontuacao);
            rbJogadorCheck = itemView.findViewById(R.id.tvAdmin);
            jogadorRow = itemView.findViewById(R.id.jogadorRow);
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