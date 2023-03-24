package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityXingyunMainBinding;


public class XingyunMain extends AppCompatActivity {
    // m2

    ActivityXingyunMainBinding binding;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Requirement 1: Each person’s project must have a RecyclerView somewhere to present items in a list.
        */
        binding = ActivityXingyunMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_xingyun_main);

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<NYTRowHolder>() {
            @NonNull
            @Override
            public NYTRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return  null;
            }

            @Override
            public void onBindViewHolder(@NonNull NYTRowHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });

        /*
        Requirement 4 part 1/3: Each activity must have at least 1 Toast.
        */
        Context context = getApplicationContext();
        CharSequence text = "Finished creating the NewYorkTimes Activity.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        /*
        Requirement 4 part 2/3: Each activity must have at least 1 Snackbar (1/2).
        */

        Button buttonSearchArticles = findViewById(R.id.buttonSearchArticles);
        buttonSearchArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "The database is offline!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Requirement 4 part 3/3: Each activity must have at least 1 AlertDialog (1/2).
    */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(XingyunMain.this);

        exitApp.setMessage("Are you sure?")
                .setTitle("Exiting to MainMenu")
                .setCancelable(false)
                .setPositiveButton("Confirm",
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            finish();
                        })
                .setNegativeButton("Cancel",
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                .create()
                .show();
    }
}

