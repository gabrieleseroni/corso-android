package it.elbuild.mobile.fragmentlifecycle;


import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity  {

    private Button fragmentButton;
    private Button fragment2Button;
    private BlankFragment blankFragment;
    private Blank2Fragment blank2Fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blankFragment = new BlankFragment();
        blank2Fragment = new Blank2Fragment();
        getFragmentManager().beginTransaction().
                add(R.id.fragment, blankFragment).add(R.id.fragment, blank2Fragment).
                hide(blank2Fragment).hide(blankFragment).commit();

        fragmentButton = (Button)findViewById(R.id.button);
        fragment2Button = (Button)findViewById(R.id.button2);

        fragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blankFragment != null) {
                    if (!blankFragment.isVisible()) {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.slide_out).hide(blank2Fragment).show(blankFragment).commit();
                    }
                }
            }
        });

        fragment2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blank2Fragment != null) {
                    if (!blank2Fragment.isVisible()) {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.slide_out).hide(blankFragment).show(blank2Fragment).commit();
                    }
                }
            }
        });
    }


}
