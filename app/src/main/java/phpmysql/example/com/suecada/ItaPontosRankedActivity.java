package phpmysql.example.com.suecada;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class ItaPontosRankedActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        Toast.makeText(getApplicationContext(), "Arraste para a esquerda para consultar o histórico de pontuações por ronda!"
                , Toast.LENGTH_LONG).show();

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("Não", null);

        alertDialog.setMessage("Tem a certeza que deseja sair?");
        alertDialog.setTitle("Sueca Italiana");
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        doExit();
    }


    public void sair(View view) {
        doExit();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String tabtitles[] = new String[] { "Registar Pontos", "Rondas" };

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    Intent intent = getIntent();

                    String jogador1 = intent.getExtras().getString("jogador1");
                    String jogador2 = intent.getExtras().getString("jogador2");
                    String jogador3 = intent.getExtras().getString("jogador3");
                    String jogador4 = intent.getExtras().getString("jogador4");
                    String jogador5 = intent.getExtras().getString("jogador5");
                    ItaPontosRankedFrag itaPontosRankedFrag = ItaPontosRankedFrag.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("jogador1", jogador1.trim());
                    bundle.putString("jogador2", jogador2.trim());
                    bundle.putString("jogador3", jogador3.trim());
                    bundle.putString("jogador4", jogador4.trim());
                    bundle.putString("jogador5", jogador5.trim());
                    // set Fragmentclass Arguments

                    itaPontosRankedFrag.setArguments(bundle);

                    return itaPontosRankedFrag;
                case 1:

                    intent = getIntent();

                    jogador1 = intent.getExtras().getString("jogador1");
                    jogador2 = intent.getExtras().getString("jogador2");
                    jogador3 = intent.getExtras().getString("jogador3");
                    jogador4 = intent.getExtras().getString("jogador4");
                    jogador5 = intent.getExtras().getString("jogador5");
                    ItaListaRankedFrag itaListaRankedFrag;
                    itaListaRankedFrag = ItaListaRankedFrag.newInstance();
                    bundle = new Bundle();
                    bundle.putString("jogador1", jogador1.trim());
                    bundle.putString("jogador2", jogador2.trim());
                    bundle.putString("jogador3", jogador3.trim());
                    bundle.putString("jogador4", jogador4.trim());
                    bundle.putString("jogador5", jogador5.trim());
                    // set Fragmentclass Arguments


                    itaListaRankedFrag.setArguments(bundle);

                    return itaListaRankedFrag;
            }
            return null;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}