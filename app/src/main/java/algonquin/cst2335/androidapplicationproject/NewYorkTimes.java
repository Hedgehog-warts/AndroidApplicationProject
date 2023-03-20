package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityNewYorkTimesBinding;

public class NewYorkTimes extends AppCompatActivity {

    ActivityNewYorkTimesBinding binding;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }
}