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

    ArrayList<DatabaseHolder> holders = new ArrayList<>();
    int position;

    public DoyoungAdapterFragment(Context c, ArrayList<String> f) {
        context = c;
        favourites = f;
    }

    @NonNull
    @Override
    public DatabaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doyoung_database_view,parent,false);
        DatabaseHolder addedHolder = new DatabaseHolder(v);
        holders.add(addedHolder);
        return new DatabaseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseHolder holder, int position) {
        holder.database.setText(favourites.get(position));

        holder.background.setOnClickListener(clk-> {
            for (DatabaseHolder selection: holders) {
                selection.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            holder.background.setBackgroundColor(Color.parseColor("#FFC8C8C8"));
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

    public int getPosition() {
        return 0;
    }

    public class DatabaseHolder extends RecyclerView.ViewHolder {
        TextView database;
        ConstraintLayout background;

        public DatabaseHolder(View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.databaseBackground);
            database = itemView.findViewById(R.id.favourite);
        }
    }
}
