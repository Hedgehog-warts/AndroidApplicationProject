package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


import java.util.List;

import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.RongMain;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityNickMainBinding;

// final
public class NickMain extends AppCompatActivity {

    private List<ImageData> imageDataList;
    ActivityNickMainBinding binding;
    protected RequestQueue queue = null;
    private catAdapter catAdapt;

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nick_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent nextPage;
        Class pageClass;

        switch (item.getItemId()) {
            case R.id.doyoung:
                pageClass= DoyoungMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.rong:
                pageClass= RongMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.xingyun:
                pageClass= XingyunMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.help:
                AlertDialog.Builder exitApp = new AlertDialog.Builder(NickMain.this);

                exitApp.setMessage(
                                "1. " + getString(R.string.help1) + "\n" +"2. "+ getString(R.string.help2) + "\n" +"3. "+
                                        getString(R.string.help3) + "\n"+"4. " + getString(R.string.help4))
                        .setTitle(getString(R.string.howto))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.done),
                                (DialogInterface.OnClickListener) (dialog, which) -> {
                                    dialog.cancel();
                                })
                        .create()
                        .show();
                break;
        }
        return true;
    }

    private void validateInputs() {
        EditText widthEditText = findViewById(R.id.widthEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);

        String widthStr = widthEditText.getText().toString().trim();
        String heightStr = heightEditText.getText().toString().trim();

        if (widthStr.isEmpty() && heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter width and height values.", Toast.LENGTH_SHORT).show();
        } else if (widthStr.isEmpty()) {
            Toast.makeText(this, "Please enter width value.", Toast.LENGTH_SHORT).show();
        } else if (heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter height value.", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("width", widthStr);
            editor.putString("height", heightStr);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayFav();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_main);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        binding = ActivityNickMainBinding.inflate(getLayoutInflater());
        Button NicksSearchButton = findViewById(R.id.searchButtonNick);
        EditText widthEditText = findViewById(R.id.widthEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String widthStr = sharedPreferences.getString("width", "");
        String heightStr = sharedPreferences.getString("height", "");
        widthEditText.setText(widthStr);
        heightEditText.setText(heightStr);

        NicksSearchButton.setOnClickListener(clk -> {
            validateInputs();
            String url = "https://placekitten.com/" + widthEditText.getText().toString() + "/" + heightEditText.getText().toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            ImageRequest request = new ImageRequest(url,
                    response -> {
                        try {
                            // Load the image into the ImageView
                            ImageView catImage = findViewById(R.id.catImageView);
                            catImage.setImageBitmap(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(NickMain.this, "Error fetching image.", Toast.LENGTH_SHORT).show();
                        }
                    },
                    0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                    error -> {
                        Toast.makeText(NickMain.this, "Error fetching image.", Toast.LENGTH_SHORT).show();
                    }
            );
            // Add the request to the RequestQueue
            queue.add(request);
        });

        Button NickSaveButton = findViewById(R.id.saveButton);
        NickSaveButton.setOnClickListener(clk ->{
            String url = "https://placekitten.com/" + widthEditText.getText().toString() + "/" + heightEditText.getText().toString();
            CatToFav(url);

        });

    }

    private void displayFav(){
        AsyncTask.execute(() ->{
            List<cats> savedCats = AppDatabase.getInstance(NickMain.this).catDAO().getAllCats();
            runOnUiThread(() ->{
                if (catAdapt == null) {
                    catAdapt = new catAdapter(savedCats);
                    // set the adapter on the ListView or RecyclerView here
                } else {
                    catAdapt.updatelist(savedCats);
                }
            });
        });
    }

    private void CatToFav(String catURL) {
        AsyncTask.execute(() -> {
            cats saveCat = new cats();
            saveCat.setCatURL(catURL);
            AppDatabase.getInstance(NickMain.this).catDAO().insert(saveCat);

            runOnUiThread(() -> {
                // Display a Toast message with the saved URL
                Toast.makeText(NickMain.this, "Cat image saved: " + catURL, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(NickMain.this);

        exitApp.setMessage(getString(R.string.goodbye))
                .setTitle(getString(R.string.leaving))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.Yes)),
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            finish();
                        })
                .setNegativeButton((getString(R.string.No)),
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                .create()
                .show();
    }

}
