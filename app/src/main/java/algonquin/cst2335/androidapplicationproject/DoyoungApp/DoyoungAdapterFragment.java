package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDatabaseViewBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungPhotoOddBinding;

public class DoyoungAdapterFragment extends RecyclerView.Adapter<DoyoungAdapterFragment.DatabaseHolder> {

    Context context;
    ArrayList<String> favourites;

    ArrayList<DatabaseHolder> tests = new ArrayList<>();

    public DoyoungAdapterFragment(Context c, ArrayList<String> f) {
        context = c;
        favourites = f;
    }

    @NonNull
    @Override
    public DatabaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doyoung_database_view,parent,false);
        DatabaseHolder test = new DatabaseHolder(v);
        tests.add(test);
        return new DatabaseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseHolder holder, int position) {
        Log.w("mainact",String.valueOf(position));
        holder.database.setText(favourites.get(position));

        holder.background.setOnClickListener(clk-> {
            for (DatabaseHolder test: tests) {
                test.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            holder.background.setBackgroundColor(Color.parseColor("#567845"));
        });


    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class DatabaseHolder extends RecyclerView.ViewHolder {
        TextView database;
        ConstraintLayout background;

        public DatabaseHolder(View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.databaseBackground);
            database = itemView.findViewById(R.id.favourite);
        }
    }
}
