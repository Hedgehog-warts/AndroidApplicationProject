package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityDoyoungMainBinding;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungPhotoItemBinding;
// m3
public class DoyoungMain extends AppCompatActivity {

    ActivityDoyoungMainBinding binding;
    DoyoungViewModel dataModel;
    private RecyclerView.Adapter photoAdapter;
    protected RequestQueue queue = null;
    Bitmap imgSr;

    private ArrayList<Bitmap> photos = new ArrayList<>();

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

        super.setSupportActionBar(binding.myToolbar);

        binding.searchBtn.setOnClickListener(clk -> {
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
                        JSONObject position0 = photoArray.getJSONObject(0);
                        String imgURL = position0.getString("img_src");
                        String replaceURL = imgURL.replace("mars.jpl.nasa.gov","mars.nasa.gov");
                        Log.w("MainAct",imgURL);

                        ImageRequest imageRequest = new ImageRequest(replaceURL, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                try {
                                    imgSr = bitmap;
                                    Log.w("MainAct1",imgSr.toString());
                                    photos.add(imgSr);
                                    photoAdapter.notifyItemInserted(photos.size()-1);
                                    Log.w("MainAct1",photos.toString());

                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 512, 512, ImageView.ScaleType.CENTER, null, (error) -> { });
                        queue.add(imageRequest);

                        String snackString = getString(R.string.kdy_foundPhoto) +
                                ", " + String.valueOf(photoArray.length()) + getString(R.string.kdy_units);
                        Snackbar.make(binding.photoRecycler, snackString, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.kdy_undo), undoclk ->{
                                    Context context = getApplicationContext();
                                    CharSequence text = getString(R.string.kdy_cancelled);
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast.makeText(context, text, duration).show();
                                }).show();

                        runOnUiThread( (  )  -> {
//                            binding.photoRecycler.setVisibility(View.VISIBLE);
                        });
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
                DoyoungPhotoItemBinding binding
                        = DoyoungPhotoItemBinding.inflate(getLayoutInflater(),
                        parent, false);
                return new photoHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull photoHolder holder, int position) {
                Bitmap obj = photos.get(position);
                Log.w("main",obj.toString());
                holder.thumbnail.setImageBitmap(obj);
            }

            @Override
            public int getItemCount() {
                return photos.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
    }

    class photoHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        public photoHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}