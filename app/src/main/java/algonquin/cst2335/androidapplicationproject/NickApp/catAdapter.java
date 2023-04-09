package algonquin.cst2335.androidapplicationproject.NickApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class catAdapter extends RecyclerView.Adapter<catAdapter.favCatViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<cats> favCatList;

    public catAdapter(List<cats> savedCats) {
        this.favCatList = savedCats;
    }

    public void updatelist(List<cats> savedCats) {
        this.favCatList = savedCats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public favCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new favCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favCatViewHolder holder, int position) {
        holder.bind(favCatList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return favCatList.size();
    }

    public static class favCatViewHolder extends RecyclerView.ViewHolder{
        TextView catURLTV;

        public favCatViewHolder(@NonNull View itemView){
            super(itemView);
            catURLTV = itemView.findViewById(android.R.id.text1);
        }

        public void bind(cats cat, OnItemClickListener onItemClickListener ){
            catURLTV.setText(cat.getCatURL());
            itemView.setOnClickListener( view -> onItemClickListener.onItemClick(cat));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(cats cat);
    }
}
