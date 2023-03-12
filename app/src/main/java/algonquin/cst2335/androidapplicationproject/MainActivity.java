package algonquin.cst2335.androidapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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