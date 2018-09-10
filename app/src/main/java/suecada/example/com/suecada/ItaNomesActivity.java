package suecada.example.com.suecada;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static suecada.example.com.suecada.SuecaActivity.buttonEffect;

public class ItaNomesActivity extends Activity {
    EditText j1;
    String j1n;
    EditText j2;
    String j2n;
    EditText j3;
    String j3n;
    EditText j4;
    String j4n;
    EditText j5;
    String j5n;
    Button btnSeguinte;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(suecada.example.com.suecada.R.layout.activity_ita_nomes);
        btnSeguinte = (Button) findViewById(suecada.example.com.suecada.R.id.btnSeguinte);
        buttonEffect(btnSeguinte);
    }

    public Intent guardaNomes(View view) {

        this.j1 = (EditText) findViewById(suecada.example.com.suecada.R.id.etj1);
        this.j2 = (EditText) findViewById(suecada.example.com.suecada.R.id.etj2);
        this.j3 = (EditText) findViewById(suecada.example.com.suecada.R.id.etj3);
        this.j4 = (EditText) findViewById(suecada.example.com.suecada.R.id.etj4);
        this.j5 = (EditText) findViewById(suecada.example.com.suecada.R.id.etj5);


        this.j1n = this.j1.getText().toString().trim();
        this.j2n = this.j2.getText().toString().trim();
        this.j3n = this.j3.getText().toString().trim();
        this.j4n = this.j4.getText().toString().trim();
        this.j5n = this.j5.getText().toString().trim();
        Intent i = new Intent(this, ItaPontosActivity.class);
        if (this.j1n.equals("") || this.j2n.equals("") || this.j3n.equals("") || this.j4n.equals("") || this.j5n.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else if (this.j1n.equals(this.j2n) || this.j1n.equals(this.j3n) || this.j1n.equals(this.j4n) || this.j1n.equals(this.j5n) || this.j2n.equals(this.j3n) || this.j2n.equals(this.j4n) || this.j2n.equals(this.j5n) || this.j3n.equals(this.j4n) || this.j3n.equals(this.j5n) || this.j4n.equals(this.j5n)) {
            Toast.makeText(getApplicationContext(), "Nome de jogador repetido!", Toast.LENGTH_SHORT).show();
        } else {
            i.putExtra("jogador1", this.j1n);
            i.putExtra("jogador2", this.j2n);
            i.putExtra("jogador3", this.j3n);
            i.putExtra("jogador4", this.j4n);
            i.putExtra("jogador5", this.j5n);
            finish();
            startActivity(i);
            return i;
        }
        return null;
    }



}