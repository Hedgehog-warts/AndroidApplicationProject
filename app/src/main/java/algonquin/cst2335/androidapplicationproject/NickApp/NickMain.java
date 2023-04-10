package algonquin.cst2335.androidapplicationproject.NickApp;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.RongMain;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityNickMainBinding;

// final
public class NickMain extends AppCompatActivity {

    ActivityNickMainBinding binding;
    CatDAO catDAO;
    NickVM nickVM;

    /**
     * Setting up the toolbar to act as the actionbar for this project
     * @param toolbar for the activity
     */
    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    /**
     * Inflate the options menu with the custom toolbar menu layout and display it
     * @param menu The menu to be inflated with the custom toolbar menu layout
     * @return true to display the menu, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nick_toolbar, menu);
        return true;
    }

    /**
     * This method is called when a menu item is selected it handles the selection of different menu items
     * and opens the corresponding activity or shows the help dialog
     * @param item The menu item that was selected
     * @return true if the event was handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent nextPage;
        Class pageClass;

        // switch statement to determine which menu item was selected
        switch (item.getItemId()) {
            // Set the pageClass variable to the DoyoungMain class
            case R.id.doyoung:
                pageClass= DoyoungMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            // Set the pageClass variable to the RongMain class
            case R.id.rong:
                pageClass= RongMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            // Set the pageClass variable to the XingyunMain class
            case R.id.xingyun:
                pageClass= XingyunMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.help:
                // Create a new alert dialog to display help information
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

    /**
     * Validates the width and height inputs entered by the user
     * Retrieves the width and height values from the corresponding EditText views, and stores them in SharedPreferences
     * Displays a Toast message prompting the user to enter width and height values if both are empty, or to enter width or height value if only one of them is empty
     */
    private void validateInputs() {
        EditText widthEditText = findViewById(R.id.widthEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);

        String widthStr = widthEditText.getText().toString().trim();
        String heightStr = heightEditText.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("width", widthStr);
        editor.putString("height", heightStr);
        editor.apply();

        if (widthStr.isEmpty() && heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter width and height values.", Toast.LENGTH_SHORT).show();
        } else if (widthStr.isEmpty()) {
            Toast.makeText(this, "Please enter width value.", Toast.LENGTH_SHORT).show();
        } else if (heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter height value.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets up the main activity and initializes UI components
     * Handles search and save button click events and retrieves/saves user input values
     * Sends a request to fetch a cat image based on the users input values and displays it in the UI
     * Allows the user to save their favorite cat images and displays them in the UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nick_main);
        nickVM = new ViewModelProvider(this).get(NickVM.class);

        // Create the Room database instance
        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cat_database")
                .build();
        catDAO = database.catDAO();

        // Set up the Toolbar
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        // Initialize views
        binding = ActivityNickMainBinding.inflate(getLayoutInflater());
        Button NicksSearchButton = findViewById(R.id.searchButtonNick);
        EditText widthEditText = findViewById(R.id.widthEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);

        // Retrieve the width and height values from SharedPreferences and set them in the corresponding EditText views
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String widthStr = sharedPreferences.getString("width", "");
        String heightStr = sharedPreferences.getString("height", "");
        widthEditText.setText(widthStr);
        heightEditText.setText(heightStr);

        // Set a listener on the Search button to validate the input and fetch the image from the URL using Volley
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
                        }
                    },
                    0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                    error -> {
                      //  Toast.makeText(NickMain.this, "Error fetching image.", Toast.LENGTH_SHORT).show();
                    }
            );
            // Add the request to the RequestQueue
            queue.add(request);
        });

        // Set a listener on the Save button to save the image to the favorites database and display it in the favorites list
        Button NickSaveButton = findViewById(R.id.saveButton);
        NickSaveButton.setOnClickListener(clk ->{
            String url = "https://placekitten.com/" + widthEditText.getText().toString() + "/" + heightEditText.getText().toString();
            if(!url.equals("https://placekitten.com//")) {
                saveCatToFav(url);
                displayFav(url);
                widthEditText.setText("");
                heightEditText.setText("");
            } else if (url.equals("https://placekitten.com//")){
                Toast.makeText(this,R.string.check, Toast.LENGTH_SHORT).show();
            }

        });

    }

    /**
     * Saves the cat image with the given URL to favorites
     * @param catURL the URL of the cat image to be saved
     */
    private void saveCatToFav(String catURL) {
        // I know I know using Async but I already used volley and this is just interacting with the database
        AsyncTask.execute(() -> {
            cats saveCat = new cats();
            saveCat.setCatURL(catURL);
            // Get an instance of the app's database and insert the new cat object
            AppDatabase.getInstance(NickMain.this).catDAO().insert(saveCat);

        });
    }

    private ArrayList<String> myDataList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "mySharedPrefs";
    private static final String SHARED_PREFS_KEY = "imageUrls";

    private void displayFav(String catURL) {
        myDataList.add(0, catURL);
        RecyclerView recyclerView = findViewById(R.id.savedImagesListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        catAdapter myAdapter = new catAdapter(myDataList, this);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        // Save the list of image URLs to SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(SHARED_PREFS_KEY, new HashSet<>(myDataList));
        editor.apply();
    }

    /**
     * Called when the activity is resumed
     * Retrieves the set of saved kitten image URLs from SharedPreferences and updates the UI with them
     * Clears and updates the RecyclerView adapter with the new set of image URLs
     */
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        Set<String> imageUrlSet = sharedPreferences.getStringSet(SHARED_PREFS_KEY, new HashSet<>());
        myDataList.clear();
        myDataList.addAll(imageUrlSet);
        RecyclerView recyclerView = findViewById(R.id.savedImagesListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        catAdapter myAdapter = new catAdapter(myDataList, this);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }


    /**
     * Overrides the default behavior of the back button on Android devices
     * Shows an AlertDialog asking the user if they want to exit the app
     * if the user selects Yes the app will be closed if the user selects No the dialog will be dismissed
     * @see AlertDialog.Builder
     * @see DialogInterface.OnClickListener
     */

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(NickMain.this);

        exitApp.setMessage(getString(R.string.goodbye))
                .setTitle(getString(R.string.leaving))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Yes),
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            finish();
                        })
                .setNegativeButton(getString(R.string.No),
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                .create()
                .show();
    }

}