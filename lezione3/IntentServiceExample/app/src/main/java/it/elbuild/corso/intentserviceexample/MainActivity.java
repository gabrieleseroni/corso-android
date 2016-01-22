package it.elbuild.corso.intentserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(WGetService.FILEPATH);
                int resultCode = bundle.getInt(WGetService.RESULT);
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                        bitmap = BitmapFactory.decodeFile(string);
                    if(bitmap!=null)
                       imageView.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.status);
        imageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(WGetService.NOTIFICATION));
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    public void onClick(View view) {

        Intent intent = new Intent(this, WGetService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(WGetService.FILENAME, "luca.png");
        intent.putExtra(WGetService.URL,
                "http://elbuild.it/images/luca.png");
        startService(intent);
        textView.setText("Service started");
    }
}
