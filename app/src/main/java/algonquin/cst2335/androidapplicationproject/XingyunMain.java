package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityXingyunMainBinding;

/*
Requirement 9: This activity supports another language: French(fr) in Canada(CA)
*/
public class XingyunMain extends AppCompatActivity {
    // m2

    ActivityXingyunMainBinding binding;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setup_recycleView();
        toast_onCreate();
        snackbar_SearchBtn();
        alertDialog_helpBtn();
        useVolley();
    }

    void setup_recycleView() {

        /*
        Requirement 1: Each person’s project must have a RecyclerView somewhere to present items in a list.
        */
        binding = ActivityXingyunMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_xingyun_main);

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<NYTRowHolder>() {
            @NonNull
            @Override
            public NYTRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return  null;
            }

            @Override
            public void onBindViewHolder(@NonNull NYTRowHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    void toast_onCreate() {
        /*
        Requirement 4 part 1/3: Each activity must have at least 1 Toast.
        */
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.toast_onCreate);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void snackbar_SearchBtn() {
        /*
        Requirement 4 part 2/3: Each activity must have at least 1 Snackbar (1/2).
        */
        Button buttonSearchArticles = findViewById(R.id.buttonSearchArticles);
        buttonSearchArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "The database is offline!", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    void alertDialog_helpBtn() {
        /*
        Requirement 8: Each activity must have at least a help menu item
        that displays an AlertDialog with instructions for how to use the interface.
        */


        Button btn_help = findViewById(R.id.btn_help);
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

    void useVolley() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=news&api-key=uaRvwTEu6MJlscYrLYCUu245jQAsWfip ";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("Volley Response", "Response is: " + response);
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

    /*
    Requirement 4 part 3/3: Each activity must have at least 1 AlertDialog (1/2).
    */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(XingyunMain.this);

        exitApp.setMessage(getString(R.string.msg_confirm))
                .setTitle(getString(R.string.msg_exit))
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

