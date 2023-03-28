package algonquin.cst2335.androidapplicationproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.snackbar.Snackbar;

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
//                } else if (v.getId() == binding.nickButton.getId()) {
//                    pageClass= NickMain.class;
//                } else if (v.getId() == binding.rongButton.getId()) {
//                    pageClass= RongMain.class;
//                } else {
//                    pageClass= XingyunMain.class;
//                }
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
                builder.setMessage("1. Input a correct user name \n" +
                                "2. Input a correct password \n" +
                                "3. Click 'Login' button \n" +
                                "4. Confirm Login info \n" +
                                "5. Type in your desired city name \n" +
                                "6. Click 'Search' button \n" +
                                "7. Confirm city info \n" +
                                "8. Get weather info by click city name \n")
                        .setTitle("User Manual: ")

                        .setPositiveButton("Close", (dialog, cl) -> {
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
        variableBinding.userName.setText("Welcome back " + userName);

        //cityname saved using SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String cityName = prefs.getString("City Name", ""); //cityName =""
        variableBinding.editCity.setText(cityName);
        SharedPreferences.Editor editor = prefs.edit();

        variableBinding.searchButton.setOnClickListener(btn -> {
            //use apps on the webpage?
//            Intent search = new Intent(Intent.ACTION_DIAL);
//            String a = variableBinding.editCity.getText().toString();
//            search.setData(Uri.parse("City:" + a));
//            editor.putString("City Name", variableBinding.editCity.getText().toString()); //cityName =""
//            editor.apply();
//
//            startActivity(search);
        });

        variableBinding.checkBox.setOnCheckedChangeListener((cb, isChecked) -> {
//                          model.isChecked.postValue(isChecked);

            Toast.makeText(this, "You do like this city: " + isChecked , Toast.LENGTH_SHORT).show();
            // makeText returns a text, and show() to show this.
        });

        if (messageList == null) {
            model.messages.setValue(messageList = new ArrayList<>());
        }


        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView messageText;
            TextView timeText;

            public MyRowHolder(@NonNull View itemView) { //itewView will be the root of the layout, constraintLayouyt;
                super(itemView);
                messageText = itemView.findViewById(R.id.messageText);
                timeText = itemView.findViewById(R.id.timeText);

            }
        }

        variableBinding.searchButton.setOnClickListener(click -> {

            TextView cityname = findViewById(R.id.editCity);
//            AlertDialog.Builder builder = new AlertDialog.Builder(RongSecond.this);
//            builder.setMessage("Do you want to check weather for it?")
//                    .setTitle("Question:")
//                    .setNegativeButton("No", (dialog, cl) -> {
//
//                    })
//                    .setPositiveButton("Yes", (dialog, cl) -> {

                        Snackbar.make(cityname, "Do you want to check weather for " + cityname.getText() + " ?", +  Snackbar.LENGTH_LONG)
                                .setAction("Yes", clk -> {
                                    String msg = variableBinding.editCity.getText().toString();
                                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                                    String currentDateandTime = sdf.format(new Date());
                                    RongCityInfo rongCityInfo = new RongCityInfo(msg, currentDateandTime, true);
                                    messageList.add(rongCityInfo);
                                    // redraw the whole list , if item is 1, the position should be 0; good amination, less work to compute.
                                    myAdapter.notifyItemInserted(messageList.size() - 1);
                                    myAdapter.notifyDataSetChanged();
                                    // clear the previous text:
                                    variableBinding.editCity.setText("");

//                                    dialog.cancel(); // some code need to add here to link database URL
                                })
                                .show();

//                    })
//                    .create().show();


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
                holder.messageText.setText(rongCityInfo.getMessage());
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
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(RongSecond.this);

        exitApp.setMessage("Do you want to Logout?")
                .setTitle("See You!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        (DialogInterface.OnClickListener) (dialog, which) -> {

                            finish();
                        })
                .setNegativeButton("No",
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
