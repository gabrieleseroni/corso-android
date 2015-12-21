package it.elbuild.mobile.calculatordemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {

    private EditText primoOperando;
    private EditText secondoOperando;
    private Button calcolaButton;
    private Spinner operazione;
    private int operazioneSelezionata = 0;
    private static final int SOMMA = 0;
    private static final int SOTTRAZIONE = 1;
    private static final int MOLTIPLICAZIONE = 2;
    private static final int DIVISIONE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primoOperando = (EditText)findViewById(R.id.primo_valore);
        secondoOperando = (EditText)findViewById(R.id.secondo_valore);
        calcolaButton = (Button)findViewById(R.id.calcola_button);
        operazione = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operazioni, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operazione.setAdapter(adapter);

         operazione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 operazioneSelezionata =  position;
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        calcolaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(primoOperando.getText().toString()!=null && !primoOperando.getText().toString().isEmpty() &&
                        secondoOperando.getText().toString()!=null &&!secondoOperando.getText().toString().isEmpty()){
                    Intent i = new Intent(getBaseContext(), ResultActivity.class);
                    i.putExtra("operazione", getResources().getStringArray(R.array.operazioni)[operazioneSelezionata]);
                    i.putExtra("primo_operando", primoOperando.getText().toString());
                    i.putExtra("secondo_operando", secondoOperando.getText().toString());
                    i.putExtra("risultato", calcolaRisultato());
                    startActivity(i);
                }

            }
        });
    }

    private String calcolaRisultato() {
        int operando1;
        int operando2;
        float reuslt = 0;

             operando1 =   Integer.parseInt(primoOperando.getText().toString());
             operando2 =   Integer.parseInt(secondoOperando.getText().toString());

            switch (operazioneSelezionata){
                case SOMMA:
                    reuslt = operando1 + operando2;
                    break;
                case SOTTRAZIONE:
                    reuslt = operando1 - operando2;
                    break;
                case MOLTIPLICAZIONE:
                    reuslt = operando1 * operando2;
                    break;
                case DIVISIONE:
                    if(operando2 != 0)
                     reuslt = (float)operando1 / operando2;
                    else
                     return "divisione per zero";
                    break;
            }




        return String.valueOf(reuslt);
    }
}
