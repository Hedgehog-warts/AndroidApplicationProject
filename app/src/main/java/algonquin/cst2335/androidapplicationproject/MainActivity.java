package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.NickApp.NickMain;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityMainBinding;

/** This is main activity for 4 applications
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /** This is for the reference of xml objects */
    ActivityMainBinding binding;

    /** Inflate a custom toolbar
     * @param menu a reference of toolbar
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_toolbar, menu);

        return true;
    }

    /** Move to each applications
     * @param item a reference of toolbar item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent nextPage;
        Class pageClass;

        switch (item.getItemId()) {
            case R.id.doyoungApp:
                pageClass= DoyoungMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.nickApp:
                pageClass= NickMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.rongApp:
                pageClass= RongMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.xingyunApp:
                pageClass= XingyunMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
        }
        return true;
    }

    /** Set the listeners
     * @param savedInstanceState a reference of bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar);

        binding.doyoungButton.setOnClickListener(this);
        binding.nickButton.setOnClickListener(this);
        binding.rongButton.setOnClickListener(this);
        binding.xingyunButton.setOnClickListener(this);
    }

    /** Set classes to move to another activity
     * @param v a reference of View
     */
    @Override
    public void onClick(View v) {
        Intent nextPage=null;
        Class pageClass=null;

        if (v.getId() == binding.doyoungButton.getId()) {
            pageClass= DoyoungMain.class;
        } else if (v.getId() == binding.nickButton.getId()) {
            pageClass= NickMain.class;
        } else if (v.getId() == binding.rongButton.getId()) {
            pageClass= RongMain.class;
        } else {
            pageClass= XingyunMain.class;
        }

        nextPage = new Intent(MainActivity.this, pageClass);
        startActivity(nextPage);
    }
}