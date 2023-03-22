package algonquin.cst2335.androidapplicationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityDoyoungMainBinding;

public class DoyoungMain extends AppCompatActivity {

    ActivityDoyoungMainBinding binding;
    DoyoungViewModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doyoung_main);

        dataModel = new ViewModelProvider(this).get(DoyoungViewModel.class);
        binding = ActivityDoyoungMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedDate = getSharedPreferences("date",MODE_PRIVATE);
        SharedPreferences.Editor editDate = sharedDate.edit();

//        myEdit.putString("name", name.getText().toString());
//        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
//        myEdit.commit();

        //retrieve
//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
//        String s1 = sh.getString("name", "");
//        int a = sh.getInt("age", 0);
//        name.setText(s1);
//        age.setText(String.valueOf(a));

        binding.searchBtn.setOnClickListener(clk -> {
            Context context = getApplicationContext();
            CharSequence text = "We found photos";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(DoyoungMain.this);

        exitApp.setMessage("Do you want to exit ?")
                .setTitle("Thank You!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                    (DialogInterface.OnClickListener) (dialog, which) -> {
                        finish();
                })
                .setNegativeButton("No",
                    (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                 })
                .create()
                .show();
    }
}