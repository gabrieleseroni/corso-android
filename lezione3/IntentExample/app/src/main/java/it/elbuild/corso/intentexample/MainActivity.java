package it.elbuild.corso.intentexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startActivityButton;
    private Button startActivityForResultButton;
    private ImageView image;
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivityButton = (Button)findViewById(R.id.start_activity);
        startActivityForResultButton = (Button)findViewById(R.id.start_activity_for_result);
        image = (ImageView)findViewById(R.id.image);
        startActivityButton.setOnClickListener(this);
        startActivityForResultButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.start_activity:
                i = new Intent(this, SecondActivity.class);
                i.putExtra(SecondActivity.STRINGA, "Ciao Mondo");
                startActivity(i);
                break;
            case R.id.start_activity_for_result:
                 i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);

        }
    }
}
