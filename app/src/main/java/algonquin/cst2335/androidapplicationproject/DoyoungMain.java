package algonquin.cst2335.androidapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

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

    private ArrayList<String> photos = new ArrayList<>();

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
        setContentView(R.layout.activity_doyoung_main);

        dataModel = new ViewModelProvider(this).get(DoyoungViewModel.class);
        binding = ActivityDoyoungMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        SharedPreferences sharedDate = getSharedPreferences("Date",MODE_PRIVATE);
        SharedPreferences.Editor editDate = sharedDate.edit();

        binding.date.setText(sharedDate.getString("solDate","0"));

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

        binding.searchBtn.setOnClickListener(clk -> {
            String date = binding.date.getText().toString();
            String apiKey = "buaI6tozzO0yekYiJB1lOjDlurYXRHfxpeXodJni";
            String url = String.format(
                    "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?" +
                            "sol=%s&" +
                            "api_key=%s",date, apiKey);

            photos.add("data 1");
            photos.add("data 2");
            photos.add("data 3");
            photos.add("data 4");


            RecyclerView rv = binding.photoRecycler;
            Snackbar.make(rv, getString(R.string.kdy_foundPhoto), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.kdy_undo), undoclk ->{
                        Context context = getApplicationContext();
                        CharSequence text = getString(R.string.kdy_cancelled);
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                    }).show();
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
                String obj = photos.get(position);
                Log.w("MainActivity",obj);
                holder.photoTextView.setText(obj);
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    class photoHolder extends RecyclerView.ViewHolder {
        TextView photoTextView;
        public photoHolder(View itemView) {
            super(itemView);
            photoTextView = itemView.findViewById(R.id.photoData);
        }
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder exitApp = new AlertDialog.Builder(DoyoungMain.this);
//
//        exitApp.setMessage("Do you want to exit ?")
//                .setTitle("Thank You!")
//                .setCancelable(false)
//                .setPositiveButton("Yes",
//                        (DialogInterface.OnClickListener) (dialog, which) -> {
//                            finish();
//                        })
//                .setNegativeButton("No",
//                        (DialogInterface.OnClickListener) (dialog, which) -> {
//                            dialog.cancel();
//                        })
//                .create()
//                .show();
//    }
}