package it.elbuild.mobile.calculatordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

  private TextView primoOperando;
  private TextView secondoOperando;
  private TextView risultatoOperando;
    private TextView operazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        primoOperando = (TextView)findViewById(R.id.primo_operando);
        secondoOperando = (TextView)findViewById(R.id.secondo_operando);
        risultatoOperando = (TextView)findViewById(R.id.risultato);
        operazione = (TextView)findViewById(R.id.operazione);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            primoOperando.setText(bundle.getString("primo_operando"));
            secondoOperando.setText(bundle.getString("secondo_operando"));
            operazione.setText(bundle.getString("operazione"));
            risultatoOperando.setText(bundle.getString("risultato"));
        }



    }
}
