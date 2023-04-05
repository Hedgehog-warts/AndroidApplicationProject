package algonquin.cst2335.androidapplicationproject.XingyunApp;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.androidapplicationproject.R;

/**
 * NYTRowHolder class
 */
public class NYTRowHolder extends RecyclerView.ViewHolder {

    int num;
    TextView headlineView;
    XingyunMain xingyunMain;
    Button btnRemoveFav;
    XingyunArticle article;


    /**
     *
     * @param itemView itemView
     * @param context context
     * @param xingyunMain xingyunMain
     * @param fav fav
     */
    public NYTRowHolder(@NonNull View itemView, Context context, XingyunMain xingyunMain, boolean fav) {
        super(itemView);
        this.xingyunMain = xingyunMain;

        headlineView = itemView.findViewById(R.id.headline);

        itemView.setOnClickListener(clk -> {
            // itemView Click Event Goes Here


//                int position = getAbsoluteAdapterPosition();
//                XingyunArticle selected = xingyunMain.articles.get(position);
//                xingyunMain.dataModel.selectedArticle.postValue(selected);
//
//                xingyunMain.selectedArticlePosition = position;

            xingyunMain.selectArticle(this);
            Snackbar.make(headlineView, "selectArticle: #" + num, Snackbar.LENGTH_LONG)
                .setAction("Action", click -> {
                    // Snackbar Action Goes Here
                }).show();

        });

//        /*
//        Requirement 4 part 3/3: Each activity must have at least 1 AlertDialog (2/2).
//        */
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        builder.setMessage("Do you want to perform this action with: " + headlineView.getText())
//            .setTitle("Question")
//            .setNegativeButton("No", (dialog, cl) -> {})
//            .setPositiveButton("Yes", (dialog, cl) -> {
//
//            /*
//            Requirement 4 part 2/3: Each activity must have at least 1 Snackbar (2/2).
//            */
//            Snackbar.make(headlineView, "Snackbar message.", Snackbar.LENGTH_LONG)
//                .setAction("Action", click -> {
//                    // Snackbar Action Goes Here
//                }).show();
//
//        })
//        .create().show();

        if(fav) {
            btnRemoveFav = itemView.findViewById(R.id.btnRemoveFav);
            btnRemoveFav.setOnClickListener(clk -> {

                xingyunMain.removeFromFav(article);

            });
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int position) {
        this.num = position;
    }

    public void setArticle(XingyunArticle article) {
        this.article = article;
    }


}