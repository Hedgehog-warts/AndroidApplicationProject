package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityMainBinding;
// m2
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_toolbar, menu);

        return true;
    }

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