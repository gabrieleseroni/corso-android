package it.elbuild.mobile.layouts;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button linearLayoutButton;
    private Button relativeLayoutButton;
    private Button gridLayoutButton;
    private Button FrameLayoutButton;
    private Button webViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutButton = (Button)findViewById(R.id.linear_layout_button);
        relativeLayoutButton = (Button)findViewById(R.id.relative_button);
        gridLayoutButton = (Button)findViewById(R.id.grid_button);
        FrameLayoutButton = (Button)findViewById(R.id.frame_button);
        webViewButton = (Button)findViewById(R.id.webView_button);

        linearLayoutButton.setOnClickListener(this);
        relativeLayoutButton.setOnClickListener(this);
        gridLayoutButton.setOnClickListener(this);
        FrameLayoutButton.setOnClickListener(this);
        webViewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.linear_layout_button:
                i = new Intent(this,LinearLayoutActivity.class);
                break;
            case R.id.grid_button:
                i = new Intent(this,GridLayoutActivity.class);
                break;
            case R.id.relative_button:
                i = new Intent(this,RelativeLayoutActivity.class);
                break;
            case R.id.frame_button:
                i = new Intent(this,FrameLayoutActivity.class);
                break;
            case R.id.webView_button:
                i = new Intent(this,WebViewActivity.class);
                break;
        }
        startActivity(i);
    }
}
