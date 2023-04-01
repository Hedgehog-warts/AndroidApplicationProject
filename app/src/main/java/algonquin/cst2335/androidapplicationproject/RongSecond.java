package algonquin.cst2335.androidapplicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityRongSecondBinding;

import algonquin.cst2335.androidapplicationproject.databinding.RongcityinfoSearchBinding;
import rongData.RongSecondViewModel;

public class RongSecond extends AppCompatActivity {

    private static String TAG = "RongSecond";
    ActivityRongSecondBinding variableBinding;
    private RongSecondViewModel model;
    ArrayList<RongCityInfo> messageList;
    private RecyclerView.Adapter myAdapter;

    String cityName;
    protected RequestQueue queue = null;
   Bitmap image;

    private String iconName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_weather_rong, menu);

        return true;
    }

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
            case R.id.help:

                AlertDialog.Builder builder = new AlertDialog.Builder(RongSecond.this);
                builder.setMessage(getString(R.string.helpText1) +"\n" +
                                getString(R.string.helpText2) + "\n" +
                                getString(R.string.helpText3) + "\n"
                                + getString(R.string.helpText4)+ "\n" +
                                getString(R.string.helpText5) + "\n" +
                                getString(R.string.helpText6) + "\n" +
                                getString(R.string.helpText7) + "\n" +
                                getString(R.string.helpText8) +"\n")
                        .setTitle(getString(R.string.helpTitle) +"\n")

                        .setPositiveButton( getString(R.string.close), (dialog, cl) -> {
                            dialog.cancel();
                        })
                        .create()
                        .show();
                break;

        }
        return true;
    }

    // This method gets the searched city from the SharedPreferences.
    private void saveLastSearchedCity(String cityName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city", cityName);
        editor.apply();
    }

    /**
     * This method loads the last searched city from the SharedPreferences.
     *
     * @return Returns the last searched city's name or an empty string if not found.
     */
    private String loadLastSearchedCity() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        return sharedPreferences.getString("city", "");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "second create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_second);

        //ActivitySecondBinding
        model = new ViewModelProvider(this).get(RongSecondViewModel.class);
        variableBinding = ActivityRongSecondBinding.inflate(getLayoutInflater());
        messageList = model.messages.getValue();
        setContentView(variableBinding.getRoot()); //+Binding
        setSupportActionBar(variableBinding.myToolbar2);

        //go back:
        //how do we get the table?
        //send data to first page: "datatable" should be the same as "nextPage"
        Intent rongMainPage = getIntent();
        String userName = rongMainPage.getStringExtra("userName");
        variableBinding.userName.setText(getString(R.string.welcome) + " " + userName);
//
//        //cityname saved using SharedPreferences
//        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        String cityName = prefs.getString("City Name", ""); //cityName =""
//        variableBinding.editCity.setText(cityName);
//        SharedPreferences.Editor editor = prefs.edit();


        variableBinding.saveCity.setOnClickListener(cb -> {
//                          model.isChecked.postValue(isChecked);
            String checkedString = "";

            boolean isChecked = true;
            if (isChecked) {
                checkedString = getString(R.string.yes);
            } else {
                checkedString = getString(R.string.no);
            }
            Toast.makeText(this, getString(R.string.likeCity) + " " + checkedString, Toast.LENGTH_SHORT).show();
            // makeText returns a text, and show() to show this.
        });

        if (messageList == null) {
            model.messages.setValue(messageList = new ArrayList<>());
        }


        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView cityText;
            TextView timeText;
            TextView tempText;
            TextView despText;

            public MyRowHolder(@NonNull View itemView) { //itewView will be the root of the layout, constraintLayouyt;
                super(itemView);
                cityText = itemView.findViewById(R.id.cityText);
                tempText = itemView.findViewById(R.id.tempText);
                despText =  itemView.findViewById(R.id.despText);
                timeText = itemView.findViewById(R.id.timeText);



            }
        }
        queue = Volley.newRequestQueue(this);
        variableBinding.searchButton.setOnClickListener(click -> {

           // save the data by clicking the save city button
            cityName = variableBinding.editCity.getText().toString();
            String stringURL = null;
            try {
//
                stringURL = new StringBuilder()
                        .append("https://api.openweathermap.org/data/2.5/weather?q=")
                        .append(URLEncoder.encode(cityName, "UTF-8"))
                        .append("&appid=7e943c97096a9784391a981c4d878b22&units=metric").toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//
//            this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL,
                    null, (response) -> {
                try {
                    JSONObject coord = response.getJSONObject("coord");
                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject position0 = weatherArray.getJSONObject(0);
                    String description = position0.getString("description");
                    iconName = position0.getString("icon");
                    JSONObject mainObject = response.getJSONObject("main");
                    double current = mainObject.getDouble("temp");
                    double min = mainObject.getDouble("temp_min");
                    double max = mainObject.getDouble("temp_max");
                    int humidity = mainObject.getInt("humidity");
                    runOnUiThread(() -> {
                        variableBinding.temp.setText(cityName + " current temp: " + current + "°C");
                        variableBinding.temp.setVisibility(View.VISIBLE);
                        variableBinding.icon.setImageBitmap(image);
                        variableBinding.icon.setVisibility(View.VISIBLE);
                        variableBinding.description.setText(description);
                        variableBinding.description.setVisibility(View.VISIBLE);
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String pathname = getFilesDir() + "/" + iconName + ".png";
                    Log.w("MainActivity", pathname);
                    File file = new File(pathname);
                    Log.w("MainActivity", file.toString());
                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(pathname);

                    } else {
                        String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
                        Log.w("MainActivity", imageUrl.toString());
                        ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                // Do something with loaded bitmap...
//
                                try {
                                    image = bitmap;
                                    image.compress(Bitmap.CompressFormat.PNG, 100,
                                            RongSecond.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                    variableBinding.icon.setImageBitmap(image);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }// end of onResponse
                        }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                            Toast.makeText(RongSecond.this, "" + error, Toast.LENGTH_SHORT).show();
                        });
                        queue.add(imgReq);
                    }// end of else
                }// end of try
                catch (Exception e) {
                    e.printStackTrace();
                }
            },
                    (error) -> {
                    });
            queue.add(request);
            // this to save the last searched city
            saveLastSearchedCity(variableBinding.editCity.getText().toString());

        });


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
//                }
            }

            @Override // what are the textView set to for row position?
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                RongCityInfo rongCityInfo = messageList.get(position);// which String goes in this row
                holder.cityText.setText(rongCityInfo.getCity());
                holder.tempText.setText(rongCityInfo.getTemperature());
                holder.despText.setText(rongCityInfo.getDescription());
                holder.timeText.setText(rongCityInfo.getTimeSent());

            }

            @Override
            public int getItemCount() { //how big is this list?
                return messageList.size();
            }

            @Override  // which layout to load at row position
            public int getItemViewType(int position) {
                // you can return anything to represent a layout.
                RongCityInfo rongCityInfo = messageList.get(position);
//                if (rongCityInfo.isSentButton())
                return 0;
//                else
//                    return 1;

            }

        });
        // this function showing the loaded city on the screen
        String loadSearchedCity = loadLastSearchedCity();
        if (!loadSearchedCity.isEmpty()) {
            variableBinding.editCity.setText(loadSearchedCity);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(RongSecond.this);

        exitApp.setMessage(getString(R.string.leave))
                .setTitle(getString(R.string.thanks))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        (DialogInterface.OnClickListener) (dialog, which) -> {

                            finish();
                        })
                .setNegativeButton(getString(R.string.no),
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                .create()
                .show();
    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.w(TAG, "onPause() second");
    }
}
