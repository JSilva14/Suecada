package suecada.example.com.suecada;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


public class SuecaActivity extends Activity {

    private NumberPicker npNos;
    private NumberPicker npVos;
    private TableLayout pontuacao;
    private TextView tvNos, tvVos;
    Button reiniciar, registar, anular;

    public void setUp() {

        registar = findViewById(suecada.example.com.suecada.R.id.btnRegistar);
        reiniciar = findViewById(suecada.example.com.suecada.R.id.btnReiniciar);
        anular = findViewById(suecada.example.com.suecada.R.id.btnAnular);
        buttonEffect(reiniciar);
        buttonEffect(registar);
        buttonEffect(anular);
        npNos = findViewById(suecada.example.com.suecada.R.id.nPNos);
        npVos = findViewById(suecada.example.com.suecada.R.id.nPVos);
        pontuacao = findViewById(suecada.example.com.suecada.R.id.tLPontos);
        String valores[] = {"0", "1", "2", "4"};

        npNos.setMaxValue(3);
        npNos.setMinValue(0);
        npNos.setDisplayedValues(valores);

        npVos.setMaxValue(3);
        npVos.setMinValue(0);
        npVos.setDisplayedValues(valores);

        npNos.setWrapSelectorWheel(false);
        npVos.setWrapSelectorWheel(false);
        npNos.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        npVos.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pontuacao.setClickable(false);

        npNos.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npVos.setValue(0);
            }
        });

        npVos.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npNos.setValue(0);
            }
        });


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_sueca);
        setUp();


    }


    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SuecaActivity.this);

        alertDialog.setPositiveButton("Sim", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("Não", null);

        alertDialog.setMessage("Tem a certeza que deseja sair?");
        alertDialog.setTitle("Sueca");
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        doExit();
    }


    public void tabela(View view) {

        //declaraçao de variaveis
        String pontos_nos;
        String pontos_vos;
        String pts;
        String quatro = "4";
        pontos_nos = String.valueOf(npNos.getValue());
        pontos_vos = String.valueOf(npVos.getValue());
        tvNos = (TextView) findViewById(suecada.example.com.suecada.R.id.tvTotalNos);
        tvVos = (TextView) findViewById(suecada.example.com.suecada.R.id.tvTotalVos);
        int nos_inc, vos_inc;
        nos_inc = Integer.valueOf(tvNos.getText().toString());
        vos_inc = Integer.valueOf(tvVos.getText().toString());

        //linha da tabela
        TableRow tr = new TableRow(this);
//        tr.setId(20);
        tr.setLayoutParams(new LayoutParams(
                (TableLayout.LayoutParams.MATCH_PARENT),
                TableLayout.LayoutParams.WRAP_CONTENT));

        //caixa de texto Nos
        TextView nospontos = new TextView(this);
        nospontos.setGravity(Gravity.CENTER);
//        nospontos.setId(20);

        if (pontos_nos.equals("3")) {
            nospontos.setText(quatro);
            nos_inc += 4;
            pts=Integer.toString(nos_inc);
            tvNos.setText(pts);

        } else {
            nospontos.setText(pontos_nos);
            nos_inc += Integer.valueOf(pontos_nos);
            pts=Integer.toString(nos_inc);
            tvNos.setText(pts);
        }

        nospontos.setPadding(5, 5, 5, 5);

        TextView vospontos = new TextView(this);
        vospontos.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        vospontos.setGravity(Gravity.CENTER);
//        vospontos.setId(20);

        if (pontos_vos.equals("3")) {
            vospontos.setText(quatro);
            vos_inc += 4;
            pts=Integer.toString(vos_inc);
            tvVos.setText(pts);
        } else {
            vospontos.setText(pontos_vos);
            vos_inc += Integer.valueOf(pontos_vos);
            pts=Integer.toString(vos_inc);
            tvVos.setText(pts);

        }
        vospontos.setPadding(5, 5, 5, 5);

        int linhas=pontuacao.getChildCount();
        if(linhas%2==0)
            tr.setBackgroundColor(Color.parseColor("#53a2be"));


        vospontos.setTextColor(Color.parseColor("#212121"));
        nospontos.setTextColor(Color.parseColor("#212121"));
        tr.addView(nospontos);// add the column to the table row here
        tr.addView(vospontos);
        tr.setPadding(0,2,0,2);


        pontuacao.addView(tr);
        pontuacao.setStretchAllColumns(true);

        final ScrollView scrollview = ((ScrollView) findViewById(suecada.example.com.suecada.R.id.ScrollView01));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        npNos.setValue(0);
        npVos.setValue(0);


    }

    public void reiniciar(View v) {

        if(pontuacao.getChildCount()!=0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    SuecaActivity.this);

            alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    npNos.setValue(0);
                    npVos.setValue(0);
                    tvVos.setText("0");
                    tvNos.setText("0");

                    pontuacao.removeAllViews();

                }

            });

            alertDialog.setNegativeButton("Não", null);

            alertDialog.setMessage("Tem a certeza que deseja reiniciar?");
            alertDialog.setTitle("Sueca");
            alertDialog.show();
        }
        else
            Toast.makeText(getApplicationContext(), "Operação Inválida!", Toast.LENGTH_SHORT).show();



    }

    public void anular(View v) {

        int ultima_linha = pontuacao.getChildCount();
        int prev_nos;
        int prev_vos;


        if (ultima_linha == 0) {
            Toast.makeText(getApplicationContext(), "A tabela está vazia!",
                    Toast.LENGTH_LONG).show();
        } else {
            //Subtrair valor da ultima linha da tabela ao total
            TableRow row1 = (TableRow) pontuacao.getChildAt(ultima_linha - 1);
            TextView tv = (TextView) row1.getChildAt(0);
            prev_nos = Integer.parseInt(tv.getText().toString());

            TextView tv1 = (TextView) row1.getChildAt(1);
            prev_vos = Integer.parseInt(tv1.getText().toString());

            tvNos = (TextView) findViewById(suecada.example.com.suecada.R.id.tvTotalNos);
            tvVos = (TextView) findViewById(suecada.example.com.suecada.R.id.tvTotalVos);
            int nos_inc, vos_inc;
            nos_inc = Integer.valueOf(tvNos.getText().toString());
            vos_inc = Integer.valueOf(tvVos.getText().toString());


            tvVos.setText(String.valueOf(vos_inc - prev_vos));
            tvNos.setText(String.valueOf(nos_inc - prev_nos));

            pontuacao.removeViewAt(ultima_linha - 1);
        }


    }

    //método para dar efeito de clique aos botoes
    //código retirado do endereço:
    // http://stackoverflow.com/questions/7175873/click-effect-on-button-in-android
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        int color = Color.parseColor("#B2DFDB");
                        v.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }


}
