package suecada.example.com.suecada;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static suecada.example.com.suecada.R.id.headerItaliana;
import static suecada.example.com.suecada.R.id.headerJ1;
import static suecada.example.com.suecada.R.id.headerJ2;
import static suecada.example.com.suecada.R.id.headerRow;
import static suecada.example.com.suecada.R.id.headerj3;
import static suecada.example.com.suecada.R.id.headerj4;
import static suecada.example.com.suecada.R.id.headerj5;
import static suecada.example.com.suecada.R.id.tblPontuacaoIta;


public class ItaListaFrag extends Fragment {

    // ArrayList historico = ItaPontosActivity.historico;
    TextView tvj1, tvj2, tvj3, tvj4, tvj5, tvptsj1, tvptsj2, tvptsj3, tvptsj4, tvptsj5;
    TableLayout tabelaPontosIta, header;
    TableRow rowRonda;
    ScrollView scrollviewIta;


    public static ItaListaFrag newInstance() {

        return new ItaListaFrag();
    }

    public ItaListaFrag() {
    }


    //this method links the fragment to the layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this); //Subscreve o fragmento ao EventBus para que
        // possa receber informação do fragmento ItaPontosFrag
        View rootView = inflater.inflate(suecada.example.com.suecada.R.layout.fragment_ita_lista, container, false);

        tvj1 = (TextView) rootView.findViewById(headerJ1);
        tvj2 = (TextView) rootView.findViewById(headerJ2);
        tvj3 = (TextView) rootView.findViewById(headerj3);
        tvj4 = (TextView) rootView.findViewById(headerj4);
        tvj5 = (TextView) rootView.findViewById(headerj5);


        header = (TableLayout) rootView.findViewById(headerItaliana);
        TableRow jogadores = (TableRow) rootView.findViewById(headerRow);
        tabelaPontosIta = (TableLayout) rootView.findViewById(tblPontuacaoIta);
        scrollviewIta = ((ScrollView) rootView.findViewById(suecada.example.com.suecada.R.id.scrollViewIta));

        jogadores.setLayoutParams(new TableRow.LayoutParams(
                (TableLayout.LayoutParams.MATCH_PARENT),
                TableLayout.LayoutParams.WRAP_CONTENT));

        
        tvj1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        tvj2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        tvj3.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        tvj4.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        tvj5.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);


        tvj1.setPadding(1, 15, 15, 15);
        tvj2.setPadding(15, 15, 15, 15);
        tvj3.setPadding(15, 15, 15, 15);
        tvj4.setPadding(15, 15, 15, 15);
        tvj5.setPadding(15, 15, 1, 15);


        tvj1.setAllCaps(true);
        tvj2.setAllCaps(true);
        tvj3.setAllCaps(true);
        tvj4.setAllCaps(true);
        tvj5.setAllCaps(true);


        tvj1.setLines(1);
        tvj2.setLines(1);
        tvj3.setLines(1);
        tvj4.setLines(1);
        tvj5.setLines(1);

        tvj1.setTextColor(Color.parseColor("#000000"));
        tvj2.setTextColor(Color.parseColor("#000000"));
        tvj3.setTextColor(Color.parseColor("#000000"));
        tvj4.setTextColor(Color.parseColor("#000000"));
        tvj5.setTextColor(Color.parseColor("#000000"));

        tvj1.setTextSize(12);
        tvj2.setTextSize(12);
        tvj3.setTextSize(12);
        tvj4.setTextSize(12);
        tvj5.setTextSize(12);

        tvj1.setEllipsize(TextUtils.TruncateAt.END);
        tvj2.setEllipsize(TextUtils.TruncateAt.END);
        tvj3.setEllipsize(TextUtils.TruncateAt.END);
        tvj4.setEllipsize(TextUtils.TruncateAt.END);
        tvj5.setEllipsize(TextUtils.TruncateAt.END);

        String jogador1 = getArguments().getString("jogador1");
        String jogador2 = getArguments().getString("jogador2");
        String jogador3 = getArguments().getString("jogador3");
        String jogador4 = getArguments().getString("jogador4");
        String jogador5 = getArguments().getString("jogador5");

        tvj1.setText(jogador1);
        tvj2.setText(jogador2);
        tvj3.setText(jogador3);
        tvj4.setText(jogador4);
        tvj5.setText(jogador5);

        //jogadores.setBackgroundColor(Color.parseColor("#53a2be"));
        jogadores.setBackgroundColor(Color.parseColor("#dcedff"));

        jogadores.setPadding(1, 5, 1, 5);
        header.setStretchAllColumns(true);


        //  ArrayAdapter<ArrayList> arrayAdapter = new ArrayAdapter<ArrayList>();


        return rootView;
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(ItaPontosFrag.jogadorEditadoEvent event) {

        tvj1.setText(event.getJogador1());
        tvj2.setText(event.getJogador2());
        tvj3.setText(event.getJogador3());
        tvj4.setText(event.getJogador4());
        tvj5.setText(event.getJogador5());

        header.setStretchAllColumns(true);

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(ItaPontosFrag.rondaAnuladaEvent event) {

        int ultima_linha=tabelaPontosIta.getChildCount();

        if (ultima_linha == 0) {
            Toast.makeText(getActivity(), "A tabela está vazia!",
                    Toast.LENGTH_LONG).show();
        } else {
            tabelaPontosIta.removeViewAt(ultima_linha - 1);
        }

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(ItaPontosFrag.jogoReiniciadoEvent event) {

       tabelaPontosIta.removeAllViews();

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(ItaPontosFrag.rondaTerminadaEvent event) {

        if (event.getPontosj1() != 0 || event.getPontosj2() != 0 || event.getPontosj3() != 0 ||
                event.getPontosj4() != 0 || event.getPontosj5() != 0) {
            tvptsj1 = new TextView(getActivity());
            tvptsj2 = new TextView(getActivity());
            tvptsj3 = new TextView(getActivity());
            tvptsj4 = new TextView(getActivity());
            tvptsj5 = new TextView(getActivity());

            rowRonda = new TableRow(getActivity());

            rowRonda.setLayoutParams(new TableRow.LayoutParams(
                    (TableLayout.LayoutParams.MATCH_PARENT),
                    TableLayout.LayoutParams.WRAP_CONTENT));


            tvptsj1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tvptsj2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tvptsj3.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tvptsj4.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tvptsj5.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);


            tvptsj1.setPadding(1, 15, 15, 15);
            tvptsj2.setPadding(15, 15, 15, 15);
            tvptsj3.setPadding(15, 15, 15, 15);
            tvptsj4.setPadding(15, 15, 15, 15);
            tvptsj5.setPadding(15, 15, 1, 15);

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            tvptsj1.setLayoutParams(params);
            tvptsj2.setLayoutParams(params);
            tvptsj3.setLayoutParams(params);
            tvptsj4.setLayoutParams(params);
            tvptsj5.setLayoutParams(params);


            tvptsj1.setTextSize(12);
            tvptsj2.setTextSize(12);
            tvptsj3.setTextSize(12);
            tvptsj4.setTextSize(12);
            tvptsj5.setTextSize(12);

            //jogadores.setBackgroundColor(Color.parseColor("#53a2be"));
            int linhas = tabelaPontosIta.getChildCount();
            if (linhas % 2 == 0)
                rowRonda.setBackgroundColor(Color.parseColor("#53a2be"));

            rowRonda.setPadding(1, 5, 1, 5);
            tabelaPontosIta.setStretchAllColumns(true);

            String rondaj1 = Integer.toString(event.getPontosj1());
            String rondaj2 = Integer.toString(event.getPontosj2());
            String rondaj3 = Integer.toString(event.getPontosj3());
            String rondaj4 = Integer.toString(event.getPontosj4());
            String rondaj5 = Integer.toString(event.getPontosj5());

            tvptsj1.setText(rondaj1);
            tvptsj2.setText(rondaj2);
            tvptsj3.setText(rondaj3);
            tvptsj4.setText(rondaj4);
            tvptsj5.setText(rondaj5);

            if (event.getPontosj1() < 0)
                tvptsj1.setTextColor(Color.parseColor("#ff0000"));
            else if (event.getPontosj1() > 0)
                tvptsj1.setTextColor(Color.parseColor("#00ff00"));
            else
                tvptsj1.setTextColor(Color.parseColor("#000000"));

            if (event.getPontosj2() < 0)
                tvptsj2.setTextColor(Color.parseColor("#ff0000"));
            else if (event.getPontosj2() > 0)
                tvptsj2.setTextColor(Color.parseColor("#00ff00"));
            else
                tvptsj2.setTextColor(Color.parseColor("#000000"));

            if (event.getPontosj3() < 0)
                tvptsj3.setTextColor(Color.parseColor("#ff0000"));
            else if (event.getPontosj3() > 0)
                tvptsj3.setTextColor(Color.parseColor("#00ff00"));
            else
                tvptsj3.setTextColor(Color.parseColor("#000000"));

            if (event.getPontosj4() < 0)
                tvptsj4.setTextColor(Color.parseColor("#ff0000"));
            else if (event.getPontosj4() > 0)
                tvptsj4.setTextColor(Color.parseColor("#00ff00"));
            else
                tvptsj4.setTextColor(Color.parseColor("#000000"));

            if (event.getPontosj5() < 0)
                tvptsj5.setTextColor(Color.parseColor("#ff0000"));
            else if (event.getPontosj5() > 0)
                tvptsj5.setTextColor(Color.parseColor("#00ff00"));
            else
                tvptsj5.setTextColor(Color.parseColor("#000000"));


            rowRonda.addView(tvptsj1);
            rowRonda.addView(tvptsj2);
            rowRonda.addView(tvptsj3);
            rowRonda.addView(tvptsj4);
            rowRonda.addView(tvptsj5);

            tabelaPontosIta.addView(rowRonda);


            scrollviewIta.post(new Runnable() {
                @Override
                public void run() {
                    scrollviewIta.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

        }
    }
}