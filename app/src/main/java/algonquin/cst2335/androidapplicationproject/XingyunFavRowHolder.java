package algonquin.cst2335.androidapplicationproject;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class XingyunFavRowHolder extends RecyclerView.ViewHolder {

    int num;
    TextView headlineView;
    XingyunMain xingyunMain;
    Button btnRemoveFav;

    public XingyunFavRowHolder(@NonNull View itemView, Context context, XingyunMain xingyunMain) {
        super(itemView);
        this.xingyunMain = xingyunMain;

        headlineView = itemView.findViewById(R.id.headline);
        btnRemoveFav = itemView.findViewById(R.id.btnRemoveFav);

        btnRemoveFav.setOnClickListener(clk -> {
            // delete this favorited article

        });

    }

    public int getNum() {
        return num;
    }

    public void setNum(int position) {
        this.num = position;
    }


}
