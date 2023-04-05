package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.RongMain;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityNickMainBinding;

// final
public class NickMain extends AppCompatActivity {


    ActivityNickMainBinding binding;
    private ArrayList<String> pics = new ArrayList<>();

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nick_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent nextPage;
        Class pageClass;

        switch (item.getItemId()) {
            case R.id.doyoung:
                pageClass= DoyoungMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.rong:
                pageClass= RongMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.xingyun:
                pageClass= XingyunMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.help:
                AlertDialog.Builder exitApp = new AlertDialog.Builder(NickMain.this);

                exitApp.setMessage(
                                "1. " + getString(R.string.help1) + "\n" +"2. "+ getString(R.string.help2) + "\n" +"3. "+
                                        getString(R.string.help3) + "\n"+"4. " + getString(R.string.help4))
                        .setTitle(getString(R.string.howto))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.done),
                                (DialogInterface.OnClickListener) (dialog, which) -> {
                                    dialog.cancel();
                                })
                        .create()
                        .show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_main);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        binding = ActivityNickMainBinding.inflate(getLayoutInflater());

    }
}
