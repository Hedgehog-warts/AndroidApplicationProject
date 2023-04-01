package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDetailFragmentBinding;

public class DoyoungPhotoFragment extends Fragment {

    DoyoungImgDetail selected;

    public DoyoungPhotoFragment(DoyoungImgDetail m) {
        selected = m;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.kdyToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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

        if (selected.image != null) {
            binding.originImage.setImageBitmap(selected.image);
        } else {
            binding.originImage.setImageResource(R.drawable.doyoung_nasa_icon);
        }
        if (selected.cameraName.isEmpty()) {
            binding.cameraName.setText("No Data");
        } else {
            binding.cameraName.setText("Camera: " + selected.cameraName);
        }
        if (selected.imgURL.isEmpty()) {
            binding.imageURL.setText("No Data");
        } else {
            binding.imageURL.setText(selected.imgURL);
        }

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

            selected.imgPath = file.toString();
            imgDAO.insertMessage(selected);
        });

        return binding.getRoot();
    }

}
