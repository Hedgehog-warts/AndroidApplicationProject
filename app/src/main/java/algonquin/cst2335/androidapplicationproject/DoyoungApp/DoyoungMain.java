package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import algonquin.cst2335.androidapplicationproject.NickMain;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.RongMain;
import algonquin.cst2335.androidapplicationproject.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityDoyoungMainBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDetailFragmentBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungPhotoEvenBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungPhotoOddBinding;

public class DoyoungMain extends AppCompatActivity {

    ActivityDoyoungMainBinding binding;
    DoyoungViewModel dataModel;

    private RecyclerView.Adapter photoAdapter;
    DoyoungPhotoFragment photoFragment;

    protected RequestQueue queue = null;
    Bitmap imgSr;

    private ArrayList<DoyoungThumbnail> thumbnails = new ArrayList<>();
    private ArrayList<DoyoungImgDetail> infoDetails = new ArrayList<>();

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.doyoung_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent nextPage;
        Class pageClass;

        switch (item.getItemId()) {
            case R.id.nickApp:
                pageClass= NickMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.rongApp:
                pageClass= RongMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.xingyunApp:
                pageClass= XingyunMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.help:
                AlertDialog.Builder exitApp = new AlertDialog.Builder(DoyoungMain.this);

                exitApp.setMessage(
                        getString(R.string.kdy_firstStep) + "\n" + getString(R.string.kdy_secondStep) + "\n" +
                        getString(R.string.kdy_thirdStep) + "\n" + getString(R.string.kdy_additionalStep))
                        .setTitle(getString(R.string.kdy_userGuide))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.kdy_close),
                            (DialogInterface.OnClickListener) (dialog, which) -> {
                                dialog.cancel();
                            })
                        .create()
                        .show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageDatabase db = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, "imageDatabase").allowMainThreadQueries().build();
        DoyoungImgDetailDao imgDAO = db.imgDAO();

        dataModel = new ViewModelProvider(this).get(DoyoungViewModel.class);
        binding = ActivityDoyoungMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedDate = getSharedPreferences("Date",MODE_PRIVATE);
        SharedPreferences.Editor editDate = sharedDate.edit();
        binding.date.setText(sharedDate.getString("solDate","0"));

        binding.photoRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                editDate.putString("solDate",editable.toString());
                editDate.apply();
            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());

        super.setSupportActionBar(binding.kdyToolbar);

        dataModel.selectedThumbnail.observe(this, infoDetails -> {
            photoFragment = new DoyoungPhotoFragment(infoDetails);
            getSupportFragmentManager().beginTransaction().addToBackStack("")
                    .replace(R.id.doyoungFragment, photoFragment)
                    .commit();
        });

        binding.searchBtn.setOnClickListener(clk -> {
            clearData();
            String date = binding.date.getText().toString();
            String apiKey = "buaI6tozzO0yekYiJB1lOjDlurYXRHfxpeXodJni";
            String url = String.format(
                    "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?" +
                            "sol=%s&" +
                            "api_key=%s",date, apiKey);
//            Log.w("MainAct",url);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (response)-> {
                    try {
                        JSONArray photoArray = response.getJSONArray("photos");
                        ArrayList<ImageRequest> imageRequests = new ArrayList<>();

                        for (int i=0; i<photoArray.length(); i ++) {
                            JSONObject photoItem = photoArray.getJSONObject(i);
                            String replaceURL = photoItem.getString("img_src")
                                    .replace("mars.jpl.nasa.gov","mars.nasa.gov");

                            JSONObject roverJSON = photoItem.getJSONObject("rover");
                            String roverName = roverJSON.getString("name");

                            JSONObject cameraJSON = photoItem.getJSONObject("camera");
                            String cameraName = cameraJSON.getString("name");

                            ImageRequest imageRequest = new ImageRequest(replaceURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    try {

                                        imgSr = bitmap;
                                        String imgNum = String.valueOf(photoAdapter.getItemCount()+1);
                                        DoyoungThumbnail thumbnail = new DoyoungThumbnail(imgNum,roverName,imgSr);
                                        thumbnails.add(thumbnail);
                                        photoAdapter.notifyItemInserted(thumbnails.size()-1);

                                        DoyoungImgDetail test = new DoyoungImgDetail(imgSr, cameraName, replaceURL);
                                        infoDetails.add(test);

                                    } catch(Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 512, 512, ImageView.ScaleType.CENTER, null, (error) -> { });
                            queue.add(imageRequest);
                        }
                        String snackString = getString(R.string.kdy_foundPhoto) +
                                ", " + String.valueOf(photoArray.length()) + getString(R.string.kdy_units);
                        Snackbar.make(binding.photoRecycler, snackString, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.kdy_undo), undoclk ->{
                                    clearData();
                                    Context context = getApplicationContext();
                                    CharSequence text = getString(R.string.kdy_cancelled);
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast.makeText(context, text, duration).show();
                                }).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, (error -> {}));
            queue.add(request);
        });

        binding.photoRecycler.setAdapter(photoAdapter = new RecyclerView.Adapter<photoHolder>() {
            @NonNull
            @Override
            public photoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    DoyoungPhotoOddBinding binding
                            = DoyoungPhotoOddBinding.inflate(getLayoutInflater(),
                            parent, false);
                    return new photoHolder(binding.getRoot());
                } else {
                    DoyoungPhotoEvenBinding binding
                            = DoyoungPhotoEvenBinding.inflate(getLayoutInflater(),
                            parent, false);
                    return new photoHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull photoHolder holder, int position) {
                Bitmap obj = thumbnails.get(position).thumbnail;
                String thumbnailNumber = thumbnails.get(position).imgNumber;
                String roverName = thumbnails.get(position).roverName;

                holder.itemNumber.setText(thumbnailNumber);
                holder.roverName.setText(roverName);
                holder.thumbnail.setImageBitmap(obj);
            }

            @Override
            public int getItemCount() {
                return thumbnails.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (position%2 == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    public void clearData() {
        photoAdapter.notifyItemRangeRemoved(0,photoAdapter.getItemCount());
        thumbnails.clear();
        infoDetails.clear();
    }

    class photoHolder extends RecyclerView.ViewHolder {

        TextView itemNumber;
        TextView roverName;
        ImageView thumbnail;

        public photoHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                DoyoungImgDetail selected = infoDetails.get(position);
                dataModel.selectedThumbnail.postValue(selected);
            });

            itemNumber = itemView.findViewById(R.id.itemNumber);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            roverName = itemView.findViewById(R.id.roverName);
        }
    }
}