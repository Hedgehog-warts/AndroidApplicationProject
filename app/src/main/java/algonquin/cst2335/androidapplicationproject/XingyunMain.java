package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityXingyunMainBinding;

/*
Requirement 9: This activity supports another language: French(fr) in Canada(CA)
*/
public class XingyunMain extends AppCompatActivity {
    // m2

    ActivityXingyunMainBinding binding;
    private RecyclerView.Adapter myAdapter;

    // Instantiate data lists
    List<String> headlines = new ArrayList<>();

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.xingyun_toolbar, menu);

        return true;
    }

    // this onOptionsItemSelected() is copied from Doyoung's code
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
            case R.id.doyoungApp:
                pageClass= DoyoungMain.class;
                nextPage = new Intent(this, pageClass);
                startActivity(nextPage);
                break;

        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup_recycleView();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        toast_onCreate();
        setup_searchBtn();
        alertDialog_helpBtn();
    }

    void setup_recycleView() {

        /*
        Requirement 1: Each person’s project must have a RecyclerView somewhere to present items in a list.
        */
        binding = ActivityXingyunMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<NYTRowHolder>() {

            @Override
            public NYTRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.detail_article_xyz, parent, false);
                return new NYTRowHolder(view, XingyunMain.this);
            }

            @Override
            public void onBindViewHolder(NYTRowHolder holder, int position) {
                String data = headlines.get(position);
                System.out.println("onBindViewHolder position is " + position);
                holder.headlineView.setText(data);
            }

            @Override
            public int getItemCount() {
                return headlines.size();
            }
        });
    }



    void toast_onCreate() {
        /*
        Requirement 4 part 1/3: Each activity must have at least 1 Toast.
        */
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.toast_onCreate_xyz);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void setup_searchBtn() {

        Button btn_search = findViewById(R.id.buttonSearchArticles);
        if (btn_search == null)  return;
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_searchBtn(v);
            }
        });

        EditText ets = findViewById(R.id.editTextSearchArticles);
        ets.setHint(getResources().getString(R.string.placeholder_search_articles_text));
    }

    void click_searchBtn(View v) {

        String inputText = binding.editTextSearchArticles.getText().toString();

        // if search file is empty, prompt an error
        if(inputText.equals("")) {
            System.out.println("search file is empty");
            snackbar_SearchBtn();
        } else {
            System.out.println("inputText: " + inputText);
            useVolley(inputText);
        }
    }

    void snackbar_SearchBtn() {
        /*
        Requirement 4 part 2/3: Each activity must have at least 1 Snackbar (1/2).
        */
        Button btn_search = findViewById(R.id.buttonSearchArticles);
        Snackbar.make(btn_search, "Search field is empty.", Snackbar.LENGTH_LONG).show();

    }

    void alertDialog_helpBtn() {
        /*
        Requirement 8: Each activity must have at least a help menu item
        that displays an AlertDialog with instructions for how to use the interface.
        */

        Button btn_help = findViewById(R.id.btn_help);
        if (btn_help == null)  return;
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_help();
            }
        });
    }

    void show_help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("1.Input the article tile in the search bar. \n" +
                        "2.Press the \'SEARCH\' button.\n\n" +
                        "Press the \'HELP\' button to show this instruction again.")
                .setTitle("Instruction")
                .setPositiveButton("Understand", (dialog, cl) -> {})
                .create().show();
    }

    void useVolley(String searchInput) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Set up the API URL and parameters
        String baseUrl = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        String query = searchInput;
        String apiKey = "uaRvwTEu6MJlscYrLYCUu245jQAsWfip";

        // Construct the full URL with the parameters
        String url = String.format("%s?q=%s&api-key=%s&page=0&sort=newest&fl=web_url,headline,pub_date,snippet", baseUrl, query, apiKey);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("Volley Response", "Response is: " + response);
                    try {
                        JSONObject responseJson = new JSONObject(response);
                        JSONArray docs = responseJson.getJSONObject("response").getJSONArray("docs");

                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject article = docs.getJSONObject(i);
                            String headline = article.getJSONObject("headline").getString("main");
                            System.out.println("Adding headline#" + headlines.size() + ": " + headline);
                            headlines.add(headline);

                        }

                        // Notify the adapter that data has been updated
                        myAdapter.notifyDataSetChanged();
                        showSearchResult();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.e("Volley Error", "That didn't work!", error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void showSearchResult() {
//        System.out.println("headlines.size() = " + headlines.size());
//        for (int i = 0; i < headlines.size(); i++) {
//            System.out.println("This is the NO." + i + " article.");
//        }

        int count = headlines.size();
        String message = String.format("Number of headlines: %d", count);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        int rowCount = myAdapter.getItemCount();
        System.out.println("rowCount = " + rowCount);
    }


    /*
    Requirement 4 part 3/3: Each activity must have at least 1 AlertDialog (1/2).
    */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(XingyunMain.this);

        exitApp.setMessage(getString(R.string.msg_confirm_xyz))
                .setTitle(getString(R.string.msg_exit_xyz))
                .setCancelable(false)
                .setPositiveButton("Confirm",
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            finish();
                        })
                .setNegativeButton("Cancel",
                        (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                .create()
                .show();
    }
}

