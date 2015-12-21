package it.elbuild.mobile.fragmentlifecycle;


import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements BlankFragment.OnFragmentInteractionListener {

    private Button fragmentButton;
    private BlankFragment blankFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentButton = (Button)findViewById(R.id.button);
        fragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blankFragment != null) {
                    if (blankFragment.isVisible()) {
                        getFragmentManager().beginTransaction().remove(blankFragment).commit();
                        fragmentButton.setText("show fragment");
                        blankFragment = null;
                    }
                } else {
                    blankFragment = new BlankFragment();
                    fragmentButton.setText("remove fragment");
                    getFragmentManager().beginTransaction().add(R.id.fragment, blankFragment, BlankFragment.TAG).commit();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
