package co.edu.udea.cmovil.gr03.yamba;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;


public class StatusActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        if (savedInstanceState == null) {
            // Create a fragment
            WindowManager wm = getWindowManager();

            Display d = wm.getDefaultDisplay();
            // StatusFragment fragment = new StatusFragment();
            //BlueFragment bfrag =new BlueFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


           /* if (d.getRotation() == Surface.ROTATION_90) {
                //---landscape mode-
                StatusFragment fragment = new StatusFragment();
                BlueFragment bfrag = new BlueFragment();

                fragmentTransaction.add(android.R.id.content, fragment, fragment.getClass().getSimpleName());
                fragmentTransaction.add(android.R.id.content, bfrag, bfrag.getClass().getSimpleName());
            } else

            {

                //---portrait mode---
                StatusFragment fragment = new StatusFragment();
                fragmentTransaction.add(android.R.id.content, fragment, fragment.getClass().getSimpleName());
            }*/
            int displaymode = getResources().getConfiguration().orientation;
            if (displaymode == 1) { // it portrait mode
                StatusFragment fragment = new StatusFragment();
                fragmentTransaction.add(android.R.id.content, fragment, fragment.getClass().getSimpleName());
            } else {// its landscape
                //StatusFragment fragment = new StatusFragment();
                BlueFragment bfrag = new BlueFragment();

                //fragmentTransaction.add(android.R.id.content, fragment, fragment.getClass().getSimpleName());
                fragmentTransaction.replace(android.R.id.content, bfrag, bfrag.getClass().getSimpleName());
            }


            fragmentTransaction.commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
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

}
