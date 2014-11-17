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
import hashfunction.hig.ull.com.gjoviksimilarhash.utils.MerkleDamgard;
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

            inputA =      "0001110101010111010111101011";
            String init = "0000000000000000000000000000";

            DataSet.gen = new ArrayList<String>();
            DataSet.hd = 4;
            DataSet.generateUpToHD(inputA, init, 0, 4, true);

            int[] hda = new int[inputA.length()];

            int hdauxb, hdauxa;

            System.out.println("********** PRUEBA PARA "+DataSet.gen.size()+" VALORES ***********");

            String stateA, stateB;

            for (int i=0; i< DataSet.gen.size(); i++) {

                inputB = DataSet.gen.get(i);

                hdauxb = SpongeConstruction.getHammingDistance(inputA, inputB);

                // MERKLE-DAMGARD

                stateA = MerkleDamgard.apply(inputA, init.substring(0, n));
                stateB = MerkleDamgard.apply(inputB, init.substring(0, n));

                // SPONGE CONSTRUCTION

                //stateA = SpongeConstruction.absorbing(inputA, r, c);
                //stateB = SpongeConstruction.absorbing(inputB, r, c);
                //stateA = SpongeConstruction.squeezing(stateA, r, c, n);
                //stateB = SpongeConstruction.squeezing(stateB, r, c, n);

                hdauxa = SpongeConstruction.getHammingDistance(stateA, stateB);

                hda[Math.abs(hdauxa-hdauxb)]++;
            }

            System.out.println("********** FIN ***********");

            for (int i=0; i< hda.length; i++){
                System.out.println("Con "+i+" Cambios: "+hda[i]+" ("+((hda[i]*100)/DataSet.gen.size())+"%)");
            }

            return rootView;
        }
    }
}
