package suecada.example.com.suecada;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;


public class ItaPontosRankedFrag extends Fragment {

    public ToggleButton j1, j2, j3, j4, j5;
    public TextView p1, p2, p3, p4, p5;


    int[] ronda = new int[5];
    ArrayList<String> historico = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(suecada.example.com.suecada.R.layout.fragment_ita_pontos_ranked, container, false);

        //  Log.d("RONDA",getArguments().getString("jogador1"));

        j1 = (ToggleButton) rootView.findViewById(suecada.example.com.suecada.R.id.tBJ1R);
        j2 = (ToggleButton) rootView.findViewById(suecada.example.com.suecada.R.id.tBJ2R);
        j3 = (ToggleButton) rootView.findViewById(suecada.example.com.suecada.R.id.tBJ3R);
        j4 = (ToggleButton) rootView.findViewById(suecada.example.com.suecada.R.id.tbJ4R);
        j5 = (ToggleButton) rootView.findViewById(suecada.example.com.suecada.R.id.tBJ5R);
        p1 = (TextView) rootView.findViewById(suecada.example.com.suecada.R.id.tVJ1R);
        p2 = (TextView) rootView.findViewById(suecada.example.com.suecada.R.id.tVJ2R);
        p3 = (TextView) rootView.findViewById(suecada.example.com.suecada.R.id.tVJ3R);
        p4 = (TextView) rootView.findViewById(suecada.example.com.suecada.R.id.tVJ4R);
        p5 = (TextView) rootView.findViewById(suecada.example.com.suecada.R.id.tVJ5R);

        /*Button editar = (Button) rootView.findViewById(R.id.btnEditarR);
        editar.setOnClickListener(editarClickListener);
        buttonEffect(editar);*/

        Button reiniciar = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnReiniciarItaR);
        reiniciar.setOnClickListener(reiniciarClickListener);
        buttonEffect(reiniciar);

        Button anular = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnAnularItaR);
        anular.setOnClickListener(anularClickListener);
        buttonEffect(anular);

        Button soma1 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btn1R);
        soma1.setOnClickListener(soma1ClickListener);
        buttonEffect(soma1);


        Button soma2 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btn2R);
        soma2.setOnClickListener(soma2ClickListener);
        buttonEffect(soma2);

        Button soma4 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btn4R);
        soma4.setOnClickListener(soma4ClickListener);
        buttonEffect(soma4);

        Button sub1 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnMenos1R);
        sub1.setOnClickListener(sub1ClickListener);
        buttonEffect(sub1);

        Button sub2 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnMenos2R);
        sub2.setOnClickListener(sub2ClickListener);
        buttonEffect(sub2);

        Button sub4 = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnMenos4R);
        sub4.setOnClickListener(sub4ClickListener);
        buttonEffect(sub4);

        Button sair = (Button) rootView.findViewById(suecada.example.com.suecada.R.id.btnSairIR);
        buttonEffect(sair);

        setUp();
        // return our View
        return rootView;



    }

    private View.OnClickListener anularClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            anular();
        }
    };

    private View.OnClickListener soma1ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            soma1();
        }
    };

    private View.OnClickListener soma2ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            soma2();
        }
    };

    private View.OnClickListener soma4ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            soma4();
        }
    };

    private View.OnClickListener sub1ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            sub1();
        }
    };

    private View.OnClickListener sub2ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            sub2();
        }
    };

    private View.OnClickListener sub4ClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            sub4();
        }
    };



    private View.OnClickListener reiniciarClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            reiniciar();
        }
    };

    private void soma(int pts) {
        int pontos;
        int soma;
        String resultado;

        if (j1.isChecked() && (j2.isChecked() || j3.isChecked() || j4.isChecked() || j5.isChecked())) {

            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);
            ronda[0] = pts;

        } else if (j1.isChecked() && (!j2.isChecked() || !j3.isChecked() || !j4.isChecked() || !j5.isChecked())) {
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();

        } else if (!j1.isChecked() && (j2.isChecked() || j3.isChecked() || j4.isChecked() || j5.isChecked())) {
            ronda[0] = 0;
        }

        if (j2.isChecked() && (j1.isChecked() || j3.isChecked() || j4.isChecked() || j5.isChecked())) {
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);
            ronda[1] = pts;

        } else if (j2.isChecked() && (!j1.isChecked() || !j3.isChecked() || !j4.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (!j2.isChecked() && (j1.isChecked() || j3.isChecked() || j4.isChecked() || j5.isChecked()))
            ronda[1] = 0;

        if (j3.isChecked() && (j1.isChecked() || j2.isChecked() || j4.isChecked() || j5.isChecked())) {
            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);
            ronda[2] = pts;

        } else if (j3.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j4.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (!j3.isChecked() && (j1.isChecked() || j2.isChecked() || j4.isChecked() || j5.isChecked()))
            ronda[2] = 0;

        if (j4.isChecked() && (j1.isChecked() || j2.isChecked() || j3.isChecked() || j5.isChecked())) {
            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);
            ronda[3] = pts;

        } else if (j4.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j3.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (!j4.isChecked() && (j1.isChecked() || j2.isChecked() || j3.isChecked() || j5.isChecked()))
            ronda[3] = 0;

        if (j5.isChecked() && (j1.isChecked() || j2.isChecked() || j3.isChecked() || j4.isChecked())) {
            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);
            ronda[4] = pts;

        } else if (j5.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j3.isChecked() || !j4.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (!j5.isChecked() && (j1.isChecked() || j2.isChecked() || j3.isChecked() || j4.isChecked()))
            ronda[4] = 0;

        if (!j1.isChecked() && !j2.isChecked() && !j3.isChecked() && !j4.isChecked() && !j5.isChecked())
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();


        if (ronda[0] + ronda[1] + ronda[2] + ronda[3] + ronda[4] == 0 || ronda[0] + ronda[1] + ronda[2] + ronda[3] + ronda[4] == 1)
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else
            historico.add(Arrays.toString(ronda));

        j1.setChecked(false);
        j2.setChecked(false);
        j3.setChecked(false);
        j4.setChecked(false);
        j5.setChecked(false);

        EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                ,ronda[4]));

        Arrays.fill(ronda, 0);
    }

    private void sub(int pts) {
        int pontos;
        int soma;
        String resultado;

        if (j1.isChecked() && j2.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            ronda[0] = -pts;
            ronda[1] = -pts;
            ronda[2] = pts;
            ronda[3] = pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));
            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j1.isChecked() && j3.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            ronda[0] = -pts;
            ronda[1] = pts;
            ronda[2] = -pts;
            ronda[3] = pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));
            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j1.isChecked() && j4.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            ronda[0] = -pts;
            ronda[1] = pts;
            ronda[2] = pts;
            ronda[3] = -pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j1.isChecked() && j5.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            ronda[0] = -pts;
            ronda[1] = pts;
            ronda[2] = pts;
            ronda[3] = pts;
            ronda[4] = -pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j2.isChecked() && j3.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            ronda[0] = pts;
            ronda[1] = -pts;
            ronda[2] = -pts;
            ronda[3] = pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j2.isChecked() && j4.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);


            ronda[0] = pts;
            ronda[1] = -pts;
            ronda[2] = pts;
            ronda[3] = -pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j2.isChecked() && j5.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            ronda[0] = pts;
            ronda[1] = -pts;
            ronda[2] = pts;
            ronda[3] = pts;
            ronda[4] = -pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j3.isChecked() && j4.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            ronda[0] = pts;
            ronda[1] = pts;
            ronda[2] = -pts;
            ronda[3] = -pts;
            ronda[4] = pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j3.isChecked() && j5.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            ronda[0] = pts;
            ronda[1] = pts;
            ronda[2] = -pts;
            ronda[3] = pts;
            ronda[4] = -pts;
            historico.add(Arrays.toString(ronda));
            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0],ronda[1],ronda[2],ronda[3]
                    ,ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j4.isChecked() && j5.isChecked()) {
            //subtrair a quem perdeu
            pontos = Integer.valueOf(p4.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p4.setText(resultado);

            pontos = Integer.valueOf(p5.getText().toString());
            soma = pontos - pts;
            resultado = Integer.toString(soma);
            p5.setText(resultado);

            //somar a quem ganhou
            pontos = Integer.valueOf(p1.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p1.setText(resultado);

            pontos = Integer.valueOf(p2.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p2.setText(resultado);

            pontos = Integer.valueOf(p3.getText().toString());
            soma = pontos + pts;
            resultado = Integer.toString(soma);
            p3.setText(resultado);

            ronda[0] = pts;
            ronda[1] = pts;
            ronda[2] = pts;
            ronda[3] = -pts;
            ronda[4] = -pts;
            historico.add(Arrays.toString(ronda));

            EventBus.getDefault().post(new rondaTerminadaEvent(ronda[0], ronda[1], ronda[2], ronda[3]
                    , ronda[4]));

            //Log.d("RONDA", "sub: " + Arrays.toString(ronda));

        } else if (j1.isChecked() && (!j2.isChecked() || !j3.isChecked() || !j4.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (j2.isChecked() && (!j1.isChecked() || !j3.isChecked() || !j4.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (j3.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j4.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (j4.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j3.isChecked() || !j5.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (j5.isChecked() && (!j1.isChecked() || !j2.isChecked() || !j3.isChecked() || !j4.isChecked()))
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();
        else if (!j1.isChecked() && !j2.isChecked() && !j3.isChecked() && !j4.isChecked() && !j5.isChecked())
            Toast.makeText(getActivity(), "Selecione dois jogadores!", Toast.LENGTH_SHORT).show();


        for (int i = 0; i < historico.size(); i++) {
            Log.d("HIST", "Ronda: " + i + " pontos: " + historico.get(i));
        }

        j1.setChecked(false);
        j2.setChecked(false);
        j3.setChecked(false);
        j4.setChecked(false);
        j5.setChecked(false);
        Arrays.fill(ronda, 0);

    }

    public void soma1() {
        soma(1);
    }

    public void soma2() {
        soma(2);
    }

    public void soma4() {
        soma(4);
    }

    public void sub1() {
        sub(1);
    }

    public void sub2() {
        sub(2);
    }

    public void sub4() {
        sub(4);
    }

    public void anular() {
        int jog1;
        int jog2;
        int jog3;
        int jog4;
        int jog5;


        //if (ronda[0] == 0 && ronda[1] == 0 && ronda[2] == 0 && ronda[3] == 0 && ronda[4] == 0) {
        if (historico.size() == 0) {
            Toast.makeText(getActivity(), "Operação Inválida!", Toast.LENGTH_SHORT).show();
        } else {

            String ultima_ronda_temp = historico.get(historico.size() - 1).replaceAll("[\\[\\]]", "");
            String[] ultima_ronda = ultima_ronda_temp.split(",");
            for (int i = 0; i < ultima_ronda.length; i++) {
                ronda[i] = Integer.parseInt(ultima_ronda[i].trim());
            }

            jog1 = (Integer.parseInt(p1.getText().toString())) - ronda[0];
            jog2 = (Integer.parseInt(p2.getText().toString())) - ronda[1];
            jog3 = (Integer.parseInt(p3.getText().toString())) - ronda[2];
            jog4 = (Integer.parseInt(p4.getText().toString())) - ronda[3];
            jog5 = (Integer.parseInt(p5.getText().toString())) - ronda[4];

            p1.setText(String.valueOf(jog1));
            p2.setText(String.valueOf(jog2));
            p3.setText(String.valueOf(jog3));
            p4.setText(String.valueOf(jog4));
            p5.setText(String.valueOf(jog5));


            //remover ultima linha do historico ao anular
            historico.remove(historico.size() - 1);
            Log.d("RONDA", Arrays.toString(ronda));
            //ronda = new int[ronda.length];
            Arrays.fill(ronda, 0);

            EventBus.getDefault().post(new rondaAnuladaEvent());

            Toast.makeText(getActivity(), "Anulado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void reiniciar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                p1.setText("0");
                p2.setText("0");
                p3.setText("0");
                p4.setText("0");
                p5.setText("0");

                historico.clear();
                Arrays.fill(ronda, 0);
                EventBus.getDefault().post(new jogoReiniciadoEvent());

            }
        });

        alertDialog.setNegativeButton("Não", null);

        alertDialog.setMessage("Tem a certeza que deseja reiniciar?");
        alertDialog.setTitle("Sueca Italiana");
        alertDialog.show();


    }


    public class rondaTerminadaEvent {
        private final int pontosj1, pontosj2, pontosj3, pontosj4, pontosj5;


        public rondaTerminadaEvent(int pontosj1, int pontosj2, int pontosj3, int pontosj4,
                                   int pontosj5) {
            this.pontosj1 = pontosj1;
            this.pontosj2 = pontosj2;
            this.pontosj3 = pontosj3;
            this.pontosj4 = pontosj4;
            this.pontosj5 = pontosj5;
        }

        public int getPontosj1() {
            return pontosj1;
        }
        public int getPontosj2() {
            return pontosj2;
        }
        public int getPontosj3() {
            return pontosj3;
        }
        public int getPontosj4() {
            return pontosj4;
        }
        public int getPontosj5() {
            return pontosj5;
        }
    }

    public class rondaAnuladaEvent {
        public rondaAnuladaEvent(){
        }
    }


    public class jogoReiniciadoEvent {
        public jogoReiniciadoEvent(){
        }
    }


    public static ItaPontosRankedFrag newInstance() {
        return new ItaPontosRankedFrag();
    }

    public ItaPontosRankedFrag() {
    }

    public void setUp() {


        //Receber os nomes dos jogadores definidos na activity anterior
        String jogador1 = getArguments().getString("jogador1");
        String jogador2 = getArguments().getString("jogador2");
        String jogador3 = getArguments().getString("jogador3");
        String jogador4 = getArguments().getString("jogador4");
        String jogador5 = getArguments().getString("jogador5");


        //Atribuir os nomes aos respetivos botoes
        j1.setText(jogador1.trim());
        j1.setTextOn(jogador1.trim());
        j1.setTextOff(jogador1.trim());
        j2.setText(jogador2.trim());
        j2.setTextOn(jogador2.trim());
        j2.setTextOff(jogador2.trim());
        j3.setText(jogador3.trim());
        j3.setTextOn(jogador3.trim());
        j3.setTextOff(jogador3.trim());
        j4.setText(jogador4.trim());
        j4.setTextOn(jogador4.trim());
        j4.setTextOff(jogador4.trim());
        j5.setText(jogador5.trim());
        j5.setTextOn(jogador5.trim());
        j5.setTextOff(jogador5.trim());

        p1.setText("0");
        p2.setText("0");
        p3.setText("0");
        p4.setText("0");
        p5.setText("0");

        j1.setOnCheckedChangeListener(changeChecker);
        j2.setOnCheckedChangeListener(changeChecker);
        j3.setOnCheckedChangeListener(changeChecker);
        j4.setOnCheckedChangeListener(changeChecker);
        j5.setOnCheckedChangeListener(changeChecker);

        historico.clear();

    }


    //método de controlo dos toggleButtons que impede que sejam selecionados mais do que 2 botoes

    private CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String idbotao;
            if (isChecked) {
                idbotao = buttonView.getTag().toString();
                buttonView.setBackgroundColor(Color.parseColor("#B2DFDB"));
                if (idbotao.equals("actvj1")) {
                    if ((j2.isChecked() && (j3.isChecked() || j4.isChecked() || j5.isChecked()))
                            || (j3.isChecked() && (j4.isChecked() || j5.isChecked()))
                            || (j4.isChecked() && j5.isChecked())) {
                        j2.setChecked(false);
                        j3.setChecked(false);
                        j4.setChecked(false);
                        j5.setChecked(false);
                    }

                } else if (idbotao.equals("actvj2")) {
                    if ((j1.isChecked() && (j3.isChecked() || j4.isChecked() || j5.isChecked()))
                            || (j3.isChecked() && (j4.isChecked() || j5.isChecked()))
                            || (j4.isChecked() && j5.isChecked())) {
                        j1.setChecked(false);
                        j3.setChecked(false);
                        j4.setChecked(false);
                        j5.setChecked(false);
                    }

                } else if (idbotao.equals("actvj3")) {
                    if ((j1.isChecked() && (j2.isChecked() || j4.isChecked() || j5.isChecked()))
                            || (j2.isChecked() && (j4.isChecked() || j5.isChecked()))
                            || (j4.isChecked() && j5.isChecked())) {
                        j1.setChecked(false);
                        j2.setChecked(false);
                        j4.setChecked(false);
                        j5.setChecked(false);
                    }
                } else if (idbotao.equals("actvj4")) {
                    if ((j1.isChecked() && (j2.isChecked() || j3.isChecked() || j5.isChecked()))
                            || (j2.isChecked() && (j3.isChecked() || j5.isChecked()))
                            || (j3.isChecked() && j5.isChecked())) {
                        j1.setChecked(false);
                        j2.setChecked(false);
                        j3.setChecked(false);
                        j5.setChecked(false);
                    }
                } else if (idbotao.equals("actvj5")) {
                    if ((j1.isChecked() && (j2.isChecked() || j3.isChecked() || j4.isChecked()))
                            || (j2.isChecked() && (j3.isChecked() || j4.isChecked()))
                            || (j3.isChecked() && j4.isChecked())) {
                        j1.setChecked(false);
                        j2.setChecked(false);
                        j3.setChecked(false);
                        j4.setChecked(false);
                    }

                }
            } else {
                buttonView.setBackgroundColor(Color.parseColor("#dcedff"));
            }
        }
    };


}

