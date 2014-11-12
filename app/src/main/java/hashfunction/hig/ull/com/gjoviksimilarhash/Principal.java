package hashfunction.hig.ull.com.gjoviksimilarhash;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;

import hashfunction.hig.ull.com.gjoviksimilarhash.utils.DataSet;
import hashfunction.hig.ull.com.gjoviksimilarhash.utils.SpongeConstruction;


public class Principal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_principal, container, false);

            String inputA, inputB;

            int r = 7;
            int c = 3;
            int n = 7;

            for (int i=0; i< DataSet.comp1.length; i++) {

                inputA = DataSet.comp1[i];
                inputB = DataSet.comp2[i];

                System.out.println("********** PRUEBA "+i+" ***********");

                System.out.println("A: " + inputA);
                System.out.println("B: " + inputB);
                System.out.println("Hamming Distance A vs B: " + SpongeConstruction.getHammingDistance(inputA, inputB));

                String stateA = SpongeConstruction.absorbing(inputA, r, c);
                String stateB = SpongeConstruction.absorbing(inputB, r, c);

                //System.out.println("Absorbing A: "+stateA);
                //System.out.println("Absorbing B: "+stateB);
                //System.out.println("Absorbing Hamming Distance A vs B: "+SpongeConstruction.getHammingDistance(stateA, stateB));

                stateA = SpongeConstruction.squeezing(stateA, r, c, n);
                stateB = SpongeConstruction.squeezing(stateB, r, c, n);

                System.out.println("Squeezing: " + stateA);
                System.out.println("Squeezing: " + stateB);
                System.out.println("Squeezing Hamming Distance A vs B: " + SpongeConstruction.getHammingDistance(stateA, stateB));
            }

            DataSet.gen = new ArrayList<String>();
            DataSet.hd = 6;
            DataSet.generateUpToHD("0110111", "0000000", 0, 6, true);

            for (String s: DataSet.gen) {
                System.out.println(s);
            }

            return rootView;
        }
    }
}
