package algonquin.cst2335.androidapplicationproject.RongApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.NickApp.NickMain;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityRongSecondBinding;
import algonquin.cst2335.androidapplicationproject.databinding.RongcityinfoSearchBinding;

/**
 * RongSecond class displays a list of saved weather information.
 * This is the second activity page for the weatherStack App.
 * This class extends AppCompatActivity,
 * which is a base class for activities that use the support library action bar features.
 */
public class RongSecond extends AppCompatActivity {
    /**
     * TAG is a string that identifies the activity in the Android log.
     */
    static String TAG = "RongSecond";
    /**
     * variableBinding is an instance of the ActivityRongSecondBinding class, which is generated
     * from the XML layout file. It contains references to all the views in the layout.
     */
    ActivityRongSecondBinding variableBinding;
    /**
     * model is an instance of the RongSecondViewModel class, which stores the state of the activity.
     */
    RongSecondViewModel model;
    /**
     * messageList is an ArrayList that stores RongCityInfo objects retrieved from the API.
     */
    ArrayList<RongCityInfo> messageList;
    /**
     * myAdapter is an instance of the RecyclerView.Adapter class, which manages the list view.
     */
    RecyclerView.Adapter myAdapter;
    /**
     * cityName is a string that stores the name of the city whose weather information is being
     * retrieved.
     */
    String cityName;
    /*
     * queue is an instance of the RequestQueue class, which handles HTTP requests to the API.
     */
    RequestQueue queue = null;
    /**
     * image is a Bitmap object that stores the weather icon retrieved from the API.
     */
    Bitmap image;
    /**
     * mDAO is an instance of the RongCityInfoDAO interface, which provides access to the app's
     * database.
     */
    RongCityInfoDAO mDAO;
    /**
     * position is an integer that stores the position of the currently selected item in the list view.
     */
    int position;


    /**
     * Inflates the menu items for this activity.
     *
     * @param menu The options menu in which the items are placed.
     * @return True to display the menu, false to not display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_weather_rong, menu);

        return true;
    }

    /**
     * this method handle action bar item clicks.
     *
     * @param item The menu item clicked.
     * @return True if action was performed successfully, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
        Intent nextPage = null;
        Class pageClass = null;

        switch (item.getItemId()) {
            case R.id.doyoung:

                pageClass = DoyoungMain.class;

                nextPage = new Intent(RongSecond.this, pageClass);
                startActivity(nextPage);

                break;
            case R.id.nick:

                pageClass = NickMain.class;
                nextPage = new Intent(RongSecond.this, pageClass);
                startActivity(nextPage);

                break;
            case R.id.xingyun:
                pageClass = XingyunMain.class;
                nextPage = new Intent(RongSecond.this, pageClass);
                startActivity(nextPage);
                break;

            case R.id.deleteWeather:
                //put your ChatMessage deletion code here. If you select this item, you should show the alert dialog
                //asking if the user wants to delete this message.

                AlertDialog.Builder deletedBuilder = new AlertDialog.Builder(RongSecond.this);
                deletedBuilder.setTitle(getString(R.string.question))
                        .setMessage(getString(R.string.delete))
                        .setNegativeButton(getString(R.string.no), (dialog, cl) -> {

                        })
                        .setPositiveButton(getString(R.string.yes), (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            RongCityInfo m = messageList.get(position);
                            thread.execute(() ->
                            {
                                mDAO.deleteMessage(m);
                                messageList.remove(position);
                            });
                            myAdapter.notifyItemRemoved(position);
                            runOnUiThread(() -> {

                                Snackbar.make(variableBinding.editCity, getString(R.string.deleteConfirm), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.undo), click -> {
                                            Executor thread_2 = Executors.newSingleThreadExecutor();
                                            thread_2.execute(() -> {
                                                mDAO.insertMessage(m); //  insert back to database
                                                messageList.add(position, m); // add back to ArrayList
                                                runOnUiThread(() ->
                                                        myAdapter.notifyItemInserted(position));
                                            });

                                        })
                                        .show();
                            });

                        })
                        .create().show();
                model.selectedMessage.setValue(null);
                break;
            case R.id.help:

                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(RongSecond.this);
                helpBuilder.setMessage(getString(R.string.helpText1) + "\n" +
                                getString(R.string.helpText2) + "\n" +
                                getString(R.string.helpText3) + "\n"
                                + getString(R.string.helpText4) + "\n" +
                                getString(R.string.helpText5) + "\n" +
                                getString(R.string.helpText6) + "\n" +
                                getString(R.string.helpText7) + "\n" +
                                getString(R.string.helpText8) + "\n")
                        .setTitle(getString(R.string.helpTitle) + "\n")

                        .setPositiveButton(getString(R.string.close), (dialog, cl) -> {
                            dialog.cancel();
                        })
                        .create()
                        .show();
                break;

        }
        return true;
    }

    /**
     * Saves the name of the last searched city to the SharedPreferences file "MyData".
     *
     * @param cityName the name of the city to be saved
     */
    // This method gets the searched city from the SharedPreferences.
    private void saveLastSearchedCity(String cityName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city", cityName);
        editor.apply();
    }

    /**
     * This method loads the last searched city name from shared preferences.
     *
     * @return The last searched city name as a string, or an empty string if no city has been searched before.
     */
    private String loadLastSearchedCity() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        return sharedPreferences.getString("city", "");
    }

    /**
     * This method is called when the activity is created.
     * It initializes the views, retrieves data from the database, and sets up the adapter for the RecyclerView.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "second create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_second);
        //ActivitySecondBinding
        model = new ViewModelProvider(this).get(RongSecondViewModel.class);
        messageList = model.messages.getValue();// empty ArrayList
        // get information from a database;
        RongCityInfoDatabase db = Room.databaseBuilder(getApplicationContext(), RongCityInfoDatabase.class, "RongCityInfoDatabase").build();
        mDAO = db.cmDAO();
        if (messageList == null) {
            model.messages.setValue(messageList = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {             // on a second thread
                messageList.addAll(mDAO.getAllMessages()); //add data list from database
                runOnUiThread(() ->
                        // going back on the main thread after loading info from database;
                        variableBinding.recycleView.setAdapter(myAdapter));
            });
        }
        variableBinding = ActivityRongSecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //+Binding
        setSupportActionBar(variableBinding.myToolbar2);

        //go back:
        //how do we get the table?
        //send data to first page: "datatable" should be the same as "nextPage"
        Intent rongMainPage = getIntent();
        String userName = rongMainPage.getStringExtra("userName");
        variableBinding.userName.setText(getString(R.string.welcome) + " " + userName);
        variableBinding.saveCity.setOnClickListener(cb -> {

            if (variableBinding.editCity.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.cityCheck), Toast.LENGTH_LONG).show();
            } else {

                String city = variableBinding.editCity.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());
                String temperatureText = variableBinding.temp.getText().toString();
                String desp = variableBinding.description.getText().toString();
                RongCityInfo rongCityInfo = new RongCityInfo(city, temperatureText, desp, currentDateandTime);
                messageList.add(rongCityInfo);

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    long id = mDAO.insertMessage(rongCityInfo); // add it to database
                    rongCityInfo.id = id; // database is saying what the new id is;
                });
                Log.w("database", String.valueOf(rongCityInfo));
                // redraw
                // the whole list , if item is 1, the position should be 0; good amination, less work to compute.
                myAdapter.notifyItemInserted(messageList.size() - 1);
                //messageList.add(msg);
                myAdapter.notifyDataSetChanged();
                // clear the previous text:
                variableBinding.editCity.setText("");
                Toast.makeText(getApplicationContext(), getString(R.string.weatherSave), Toast.LENGTH_LONG).show();

            }
        });

        // fetch the weather info from weatherstack webiste.
        queue = Volley.newRequestQueue(this);
        variableBinding.searchButton.setOnClickListener(click -> {
            if (variableBinding.editCity.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.cityCheck), Toast.LENGTH_LONG).show();
            }
            // save the data by clicking the save city button
            else {
                cityName = variableBinding.editCity.getText().toString();
                String stringURL = null;
                try {

                    stringURL = new StringBuilder()
                            .append("http://api.weatherstack.com/current?")
                            .append("&access_key=51ab3cbdab7489cd08c3385168931c59&query=")
                            .append(URLEncoder.encode(cityName, "UTF-8"))
                            .toString();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//            this goes in the button click handler:
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL,
                        null, (response) -> {

                    try {
                        JSONObject request1 = response.getJSONObject("request");
                        JSONObject locationObject = response.getJSONObject("location");
                        JSONObject currentObject = response.getJSONObject("current");
                        JSONArray iconsArray = currentObject.getJSONArray("weather_icons");
                        JSONArray descriptionsArray = currentObject.getJSONArray("weather_descriptions");
                        int current = currentObject.getInt("temperature");

                        // Get the first item in the arrays as strings
                        String iconUrl = iconsArray.getString(0);
                        String description = descriptionsArray.getString(0);

                        Glide.with(this).load(iconUrl).into(variableBinding.icon);
                        TextView descriptionTextView = findViewById(R.id.description);
                        runOnUiThread(() -> {
                            variableBinding.temp.setText("Current temp is " + current + "°C");
                            variableBinding.temp.setVisibility(View.VISIBLE);
                            variableBinding.icon.setVisibility(View.VISIBLE);
                            descriptionTextView.setText(description);
                            variableBinding.description.setVisibility(View.VISIBLE);
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                        (error) -> {
                        });
                queue.add(request);
            }
            // this to save the last searched city
            saveLastSearchedCity(variableBinding.editCity.getText().toString());

        });

        //This method observes the selectedMessage LiveData in the RongSecondViewModel
        // and handles changes to its value.
        model.selectedMessage.observe(this, (newMessageValue) -> {

            if (newMessageValue != null) {
                MessageDetailsFragment weatherFragment = new MessageDetailsFragment(newMessageValue);
                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragmentLocation, weatherFragment).commit();
            } else {
                getSupportFragmentManager().popBackStack();

            }
        });

        /**
         * This class represents a row holder for the RecyclerView in the RongSecondActivity.
         * It extends the RecyclerView.ViewHolder class and contains
         * TextViews for city, time, temperature, and description.
         */
        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView cityText;
            TextView timeText;
            TextView tempText;
            TextView despText;

            /**
             * Constructor for the MyRowHolder class.
             * @param itemView The root View of the layout, which is a ConstraintLayout.
             */
            public MyRowHolder(@NonNull View itemView) { //itewView will be the root of the layout, constraintLayouyt;
                super(itemView);
                itemView.setOnClickListener(clk -> {
                    position = getAbsoluteAdapterPosition();
                    RongCityInfo selected = messageList.get(position);
                    model.selectedMessage.postValue(selected);
                });
                cityText = itemView.findViewById(R.id.cityText);
                tempText = itemView.findViewById(R.id.tempText);
                timeText = itemView.findViewById(R.id.timeText);
                despText = itemView.findViewById(R.id.despText);
            }
        }
        //This method initializes the RecyclerView and sets up an adapter to handle displaying the data.
        // It sets the layout manager
        variableBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        variableBinding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override // which XNL layout for this set_message.xml
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                if (viewType == 0) {
                // this always inflates sent_message.xml, comes from below:
                RongcityinfoSearchBinding binding = RongcityinfoSearchBinding.inflate(getLayoutInflater(),
                        parent, false);

                return new MyRowHolder(binding.getRoot());
            }

            /**
             * Binds the data for a single row in the RecyclerView.
             * Sets the text for each TextView in the row
             * based on the corresponding RongCityInfo object in the messageList at the given position.
             * @param holder The MyRowHolder object representing the row being bound.
             * @param position The position of the row in the messageList.
             */
            @Override // what are the textView set to for row position?
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                RongCityInfo rongCityInfo = messageList.get(position);// which String goes in this row
                holder.cityText.setText(rongCityInfo.getCity());
                holder.tempText.setText(rongCityInfo.getTemperature());
                holder.timeText.setText(rongCityInfo.getTimeSent());
                holder.despText.setText(rongCityInfo.getDescription());
            }

            @Override
            public int getItemCount() { //how big is this list?
                return messageList.size();
            }

            @Override  // which layout to load at row position
            public int getItemViewType(int position) {
                // you can return anything to represent a layout.
                RongCityInfo rongCityInfo = messageList.get(position);
                return 0;
            }
        });
        // this function showing the loaded city on the screen
        String loadSearchedCity = loadLastSearchedCity();
        if (!loadSearchedCity.isEmpty()) {
            variableBinding.editCity.setText(loadSearchedCity);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() second");
    }
}
