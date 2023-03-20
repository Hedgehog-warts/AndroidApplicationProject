package algonquin.cst2335.androidapplicationproject;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class NYTRowHolder extends RecyclerView.ViewHolder {

    TextView titleText;
    Context context;

    public NYTRowHolder(@NonNull View itemView, Context context) {
        super(itemView);

        itemView.setOnClickListener(clk -> {
            // itemView Click Event Goes Here
        });

        /*
        Requirement 9 part 3/3: Each activity must have at least 1 AlertDialog.
        */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Do you want to perform this action with: " + titleText.getText())
            .setTitle("Question")
            .setNegativeButton("No", (dialog, cl) -> {})
            .setPositiveButton("Yes", (dialog, cl) -> {

            /*
            Requirement 9 part 2/3: Each activity must have at least 1 Snackbar.
            */
            Snackbar.make(titleText, "Snackbar message.", Snackbar.LENGTH_LONG)
                .setAction("Action", click -> {
                    // Snackbar Action Goes Here
                }).show();

        })
        .create().show();
    }
}
