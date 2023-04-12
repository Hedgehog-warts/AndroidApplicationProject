package algonquin.cst2335.androidapplicationproject.NickApp;

import static algonquin.cst2335.androidapplicationproject.NickApp.AppDatabase.MIGRATION_1_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidapplicationproject.R;

public class catAdapter extends RecyclerView.Adapter<catAdapter.myViewHolder> {
    
    private ArrayList<String> favCatList;
    public CatDAO catDAO;
    private Context context;

    /**
     * Constructs a new adapter for displaying a list of favorite cat names, using the given
     * list of names and application context. Also initializes a Room database and gets a reference
     * to the CatDAO for accessing and manipulating the cat data
     * @param favCatList the list of favorite cat names to display
     * @param context the application context to use for initializing the Room database
     */
    public catAdapter(ArrayList<String> favCatList, Context context) {
        this.favCatList = favCatList;
        this.context = context;
        catDAO = Room.databaseBuilder(context, AppDatabase.class, "cat_database")
                .addMigrations(MIGRATION_1_2)
                .build()
                .getCatDAO();
    }

    /**
     * ViewHolder class for the RecyclerView adapter used to display a list of items
     * the ViewHolder holds the view references for each item's layout components
     * including a TextView to display the image link a TextView to display the time and a Button to delete the item
     */
    public static class myViewHolder extends RecyclerView.ViewHolder{
        public TextView myTextView;
        public TextView myTimeView;
        public Button delete;

        public myViewHolder (View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.image_link_textview);
            myTimeView = itemView.findViewById(R.id.time_textview);
            delete = itemView.findViewById(R.id.delete_button);
        }

    }

    /**
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return A new ViewHolder that holds a View of the given view type
     */
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_cat_pictures, parent, false);
        return new myViewHolder(itemView);
    }

    /**
     * Binds the data at the specified position to the views in the ViewHolder
     * @param holder the ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position the position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        SimpleDateFormat timeSaved = new SimpleDateFormat("EEE, dd-MMM-yyyy hh-mm-ss a");
        String time = timeSaved.format(new Date());
        position = holder.getAdapterPosition();
        String myDataItem = favCatList.get(position);
        holder.myTextView.setText(myDataItem);
        holder.myTimeView.setText(time);

        int finalPosition = position;
        String deletedCatUrl = favCatList.get(finalPosition);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a backup of the deleted item
                String catUrlBackup = favCatList.get(finalPosition);

                // Delete the item from the list
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> { catDAO.delete(new cats(catUrlBackup)); });
                favCatList.remove(finalPosition);
                notifyItemRemoved(finalPosition);

                // Display a Snackbar with an undo button
                Snackbar snackbar = Snackbar.make(v, "Item deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Restore the deleted item to the list
                        favCatList.add(finalPosition, catUrlBackup);
                        notifyItemInserted(finalPosition);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> { catDAO.insert(new cats(catUrlBackup)); });
                    }
                });
                snackbar.show();
            }
        });

    }


    /**
     * Returns the total number of items in the data set held by the adapter
     * @return the total number of items in this adapter
     */
    @Override
    public int getItemCount() {
        return favCatList.size();
    }

}