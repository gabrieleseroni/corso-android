package it.elbuild.corso.intentexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String STRINGA = "stringa";
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString(STRINGA);
        text = (TextView)findViewById(R.id.text_view);
        if(s!=null) text.setText(s);
    }
}
