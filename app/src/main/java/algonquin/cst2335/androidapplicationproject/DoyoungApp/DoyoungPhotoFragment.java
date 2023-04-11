package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityDoyoungMainBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDetailFragmentBinding;

/** This is a fragment to show the thumbnail's details
 * @author Doyoung Kim
 * @version 1.0
 */

public class DoyoungPhotoFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    /** This is for all objects in the activity_doyoung_main xml*/
    DoyoungDetailFragmentBinding binding;

    /** RecyclerView for saved images list */
    private RecyclerView recyclerView;

    /** Selected thumbnail */
    DoyoungImgDetail selected;

    /** Selected item in saved images */
    DoyoungImgDetail favoriteSelected;

    /** This is used to display the saved images list */
    ArrayList<String> favourites = new ArrayList<>();

    /** Storage place for images list from database */
    List<DoyoungImgDetail> savedFavourites;

    /** RecyclerView adapter to display saved images list from database */
    DoyoungAdapterFragment adapter;

    /** The position of a selected item in the saved images list */
    int position;

    /** Queue for toast messages */
    Queue<String> toastQueue = new LinkedList<>();

    /** Constructor with DoyoungImgDetail object
     * @param m a object of DoyoungImgDetail
     */
    public DoyoungPhotoFragment(DoyoungImgDetail m) {
        selected = m;
    }

    /** Display the images list from the current database
     * @param view a reference of a View
     * @param savedInstanceState a savedInstanceState bundle
     * */
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

    /** Use OnCreate default
     * @param savedInstanceState a savedInstanceState bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** Inflate doyoung_toolbar
     * @param menu a reference of a Menu
     * @param inflater inflater to use a custom toolbar
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.doyoung_toolbar, menu);
    }

    /** Save, delete, and view the detail of a selected thumbnail
     * @param inflater a reference of a Inflater
     * @param container a reference of a ViewGroup
     * @param savedInstanceState a savedInstanceState bundle
     * @return a View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        DoyoungDetailFragmentBinding binding = DoyoungDetailFragmentBinding.inflate(inflater);
        detailInitialize(binding);

        ImageDatabase db = Room.databaseBuilder(getActivity(),
                ImageDatabase.class, "imageDatabase").allowMainThreadQueries().build();
        DoyoungImgDetailDao imgDAO = db.imgDAO();

        binding.saveImage.setOnClickListener(clk -> {
            File file = null;
            if (selected.image != null) {
                String fileName = selected.image.toString();
                File directory = new ContextWrapper(getActivity())
                        .getDir("imageDir", Context.MODE_PRIVATE);
                file = new File(directory,fileName+".png");
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    selected.image.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            String imageListItem =  "Date: " + selected.photoDate +
                                    ", ID: " + selected.photoID;
            List<DoyoungImgDetail> duplicates = imgDAO.checkDuplication(selected.photoDate, selected.photoID);
            if(duplicates.isEmpty()) {
                selected.imgPath = file.toString();
                imgDAO.insertImage(selected);
                favourites.add(imageListItem);
                adapter.notifyItemInserted(adapter.getItemCount()-1);

                savedFavourites = imgDAO.getAllImages();
            } else {
                customToast(getString(R.string.kdy_existed) + " (" + imageListItem + ")");
            }

        });


        binding.deleteImage.setOnClickListener(clk -> {
            String deletedItem =  "Date: " + savedFavourites.get(position).photoDate +
                    ", ID: " + savedFavourites.get(position).photoID;
            DoyoungImgDetail deleteImage = savedFavourites.get(position);

            favourites.remove(position);
            imgDAO.deleteImage(deleteImage);
            adapter.notifyItemRemoved(position);

            savedFavourites = imgDAO.getAllImages();

            customToast(deletedItem + " " + getString(R.string.kdy_isDeleted));
        });

        binding.viewImage.setOnClickListener(clk -> {
            String pathName = savedFavourites.get(position).imgPath;
            Bitmap savedImage = BitmapFactory.decodeFile(pathName);
            binding.originImage.setImageBitmap(savedImage);
            binding.cameraName.setText(getString(R.string.kdy_camera) + " "
                    + savedFavourites.get(position).cameraName);
            binding.imageURL.setText(savedFavourites.get(position).imgURL);

            selected = savedFavourites.get(position);
        });
        return binding.getRoot();
    }

    /** Initialize the detail of a selected thumbnail
     * @param binding a reference of DoyoungDetailFragmentBinding
     */
    public void detailInitialize(DoyoungDetailFragmentBinding binding) {
        if (selected.image != null) {
            binding.originImage.setImageBitmap(selected.image);
        } else {
            binding.originImage.setImageResource(R.drawable.doyoung_nasa_icon);
        }
        if (selected.cameraName.isEmpty()) {
            binding.cameraName.setText(getString(R.string.kdy_noData));
        } else {
            binding.cameraName.setText(getString(R.string.kdy_camera) + " " + selected.cameraName);
        }
        if (selected.imgURL.isEmpty()) {
            binding.imageURL.setText(getString(R.string.kdy_noData));
        } else {
            binding.imageURL.setText(selected.imgURL);
        }
    }

    /** Pop up the custom toast message
     * @param t the message to be displayed
     */
    public void customToast(String t) {
        Context context = getContext();
        CharSequence text = t;
        int duration = Toast.LENGTH_SHORT;
        toastQueue.add(t);

        if (!toastQueue.isEmpty()) {
            String message = toastQueue.poll();
            Toast.makeText(context, message, duration).show();
        }
    }

    /** Listener to track the user's click position in the saved list RecyclerView
     * @param savedInstanceState a savedInstanceState bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null) {
            recyclerView.addOnItemTouchListener(this);
        }
    }

    /** Get the user's click position in the saved list RecyclerView
     * @param rv a reference of a RecyclerView
     * @param e a reference of a MotionEvent
     * @return false
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        int action = e.getAction() & MotionEvent.ACTION_MASK;
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        position = childView != null ? rv.getChildAdapterPosition(childView) : -1;
        Log.w("mainact",String.valueOf(position));
        return false;
    }

    /** Not use */
    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

    /** Not use */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
