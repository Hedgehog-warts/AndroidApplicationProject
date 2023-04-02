package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import algonquin.cst2335.androidapplicationproject.R;

/** This is a RecyclerView adapter for saved images list
 * @author Doyoung Kim
 * @version 1.0
 */
public class DoyoungAdapterFragment extends RecyclerView.Adapter<DoyoungAdapterFragment.DatabaseHolder> {

    /** RecyclerView context for the saved images list */
    Context context;

    /** Storage variable to hold saved images list */
    ArrayList<String> favourites;

    /** This is used to track the user's click */
    ArrayList<DatabaseHolder> holders = new ArrayList<>();

    /** Constructor with context and saved images
     * @param c a fragment context
     * @param f String data for saved images list
     */
    public DoyoungAdapterFragment(Context c, ArrayList<String> f) {
        context = c;
        favourites = f;
    }

    /** Keep all generated doyoung_database_view xml and return a new DatabaseHolder
     * @param parent the ViewGroup of saved images list
     * @param viewType the view type of saved images list
     * @return a new DatabaseHolder
     */
    @NonNull
    @Override
    public DatabaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doyoung_database_view,parent,false);
        DatabaseHolder addedHolder = new DatabaseHolder(v);
        holders.add(addedHolder);
        return new DatabaseHolder(v);
    }

    /** Set the text data in doyoung_database_view and change the background color of a selection
     * @param holder the DatabaseHOlder of saved images list
     * @param position the position of saved images list
     */
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

    /** Return the number of saved images list
     * @return the number of saved images list
     */
    @Override
    public int getItemCount() {
        return favourites.size();
    }

    /** Return the view type (not use)
     * @param position the position of saved images list
     * @return the view type (not use)
     */
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /** Define the objects in doyoung_database_view xml
     */
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
