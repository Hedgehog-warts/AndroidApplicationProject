package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDetailFragmentBinding;

public class DoyoungPhotoFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    DoyoungImgDetail selected;
    ArrayList<String> favourites = new ArrayList<>();
    List<DoyoungImgDetail> savedFavourites;
    DoyoungAdapterFragment adapter;
    int position;

    public DoyoungPhotoFragment(DoyoungImgDetail m) {
        selected = m;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.kdyToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ImageDatabase db = Room.databaseBuilder(getActivity(),
                ImageDatabase.class, "imageDatabase").allowMainThreadQueries().build();
        DoyoungImgDetailDao imgDAO = db.imgDAO();
        savedFavourites = imgDAO.getAllImages();
        for (DoyoungImgDetail saved: savedFavourites) {
            String imageListItem =  "Date: " + saved.photoDate +
                                    ", ID: " + saved.photoID;
            favourites.add(imageListItem);
        }

        recyclerView = view.findViewById(R.id.databaseView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new DoyoungAdapterFragment(getContext(),favourites);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.doyoung_toolbar, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        DoyoungDetailFragmentBinding binding = DoyoungDetailFragmentBinding.inflate(inflater);
        detailInitialize(binding);

        ImageDatabase db = Room.databaseBuilder(getActivity(),
                ImageDatabase.class, "imageDatabase").allowMainThreadQueries().build();
        DoyoungImgDetailDao imgDAO = db.imgDAO();

        binding.saveImage.setOnClickListener(clk -> {
            String fileName = selected.image.toString();
            File directory = new ContextWrapper(getActivity())
                    .getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory,fileName+".png");
            try {
                FileOutputStream out = new FileOutputStream(file);
                selected.image.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String imageListItem =  "Date: " + selected.photoDate +
                                    ", ID: " + selected.photoID;
            List<DoyoungImgDetail> duplicates = imgDAO.checkDuplication(selected.photoDate, selected.photoID);
            if(duplicates.isEmpty()) {
                selected.imgPath = file.toString();
                imgDAO.insertImage(selected);
                favourites.add(imageListItem);
                adapter.notifyItemInserted(adapter.getItemCount()-1);
            } else {
                customToast(getString(R.string.kdy_existed) + " (" + imageListItem + ")");
            }

        });

        binding.deleteImage.setOnClickListener(clk -> {
            favourites.remove(position);
            DoyoungImgDetail deleteImage = savedFavourites.get(position);
            imgDAO.deleteImage(deleteImage);
            adapter.notifyItemRemoved(position);
        });

        binding.viewImage.setOnClickListener(clk -> {
            customToast("TBD");
        });
        return binding.getRoot();
    }

    public void detailInitialize(DoyoungDetailFragmentBinding binding) {
        if (selected.image != null) {
            binding.originImage.setImageBitmap(selected.image);
        } else {
            binding.originImage.setImageResource(R.drawable.doyoung_nasa_icon);
        }
        if (selected.cameraName.isEmpty()) {
            binding.cameraName.setText(getString(R.string.kdy_noData));
        } else {
            binding.cameraName.setText(getString(R.string.kdy_camera) + selected.cameraName);
        }
        if (selected.imgURL.isEmpty()) {
            binding.imageURL.setText(getString(R.string.kdy_noData));
        } else {
            binding.imageURL.setText(selected.imgURL);
        }
    }

    public void customToast(String t) {
        Context context = getContext();
        CharSequence text = t;
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null) {
            recyclerView.addOnItemTouchListener(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        int action = e.getAction() & MotionEvent.ACTION_MASK;
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        position = childView != null ? rv.getChildAdapterPosition(childView) : -1;

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
