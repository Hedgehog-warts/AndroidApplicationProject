package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import org.json.JSONException;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
//    protected RequestQueue queue = null;

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
            // Both inputs are valid, you can perform your desired action here
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_main);


        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        binding = ActivityNickMainBinding.inflate(getLayoutInflater());


        binding.searchButton.setOnClickListener(clk -> {
            EditText widthEditText = findViewById(R.id.widthEditText);
            EditText heightEditText = findViewById(R.id.heightEditText);
            validateInputs();
            String url = "https://placekitten.com/" + heightEditText.getText().toString() + "/" + widthEditText.getText().toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            // Get the image URL from the response object
                            String imageUrl = "https://placekitten.com/" + widthEditText.getText().toString() + "/" + heightEditText.getText().toString();

                            // Load the image into the ImageView using Glide library
                            Glide.with(NickMain.this).load(imageUrl).into(binding.catImageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(NickMain.this, "Error fetching image.", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(NickMain.this, "Error fetching image.", Toast.LENGTH_SHORT).show();
                    }
            );
            // Add the request to the RequestQueue
            queue.add(request);
        });


    }
}
