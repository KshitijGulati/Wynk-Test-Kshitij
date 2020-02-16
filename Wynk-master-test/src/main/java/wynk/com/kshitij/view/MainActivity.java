package wynk.com.kshitij.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import wynk.com.kshitij.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fm.findFragmentByTag("main_fragment");
        // create the fragment and data the first time
        if (mainFragment == null) {
            // add the fragment
            mainFragment = new MainFragment();
            fm.beginTransaction().add(R.id.activity_main, mainFragment, "main_fragment").commit();
        }
    }
}
