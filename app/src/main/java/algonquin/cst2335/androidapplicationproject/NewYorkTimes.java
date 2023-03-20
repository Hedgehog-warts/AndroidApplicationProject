package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityNewYorkTimesBinding;

public class NewYorkTimes extends AppCompatActivity {

    ActivityNewYorkTimesBinding binding;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Requirement 1: Each person’s project must have a RecyclerView somewhere to present items in a list.
        */
        binding = ActivityNewYorkTimesBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_new_york_times);

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
        Requirement 9 part 1/3: Each activity must have at least 1 Toast.
        */
        Context context = getApplicationContext();
        CharSequence text = "Finished creating the NewYorkTimes Activity.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}

