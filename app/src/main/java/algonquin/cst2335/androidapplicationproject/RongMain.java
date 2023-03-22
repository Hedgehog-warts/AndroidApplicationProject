package algonquin.cst2335.androidapplicationproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityRongMainBinding;


public class RongMain extends AppCompatActivity {

    private static String TAG = "RongMain";

    private ActivityRongMainBinding binding;

    //m1
    @Override // This starts the application , is the main function in Android
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //loads buttons /  test on screen / everything in the res folder will show on screen
        setContentView(R.layout.activity_rong_main);

        binding = ActivityRongMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // loads XML on screen;

        // emailAddress saved using SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String userName = prefs.getString("LoginName", ""); //userName =""
        binding.myEditText.setText(userName);
        SharedPreferences.Editor editor = prefs.edit();

//        binding.loginButton.setOnClickListener(clk -> {
//
//        });

        TextView tv = findViewById(R.id.textShow);

        binding.loginButton.setOnClickListener(clk -> {

            String password = binding.myEditText2.getText().toString();

            // looking for UpperCase, LowerCase, Number and Special character,
            if (checkPasswordComplexity(password)) {
                tv.setText("Your password is correct");

                AlertDialog.Builder builder = new AlertDialog.Builder(RongMain.this);
                builder.setMessage("Do you want to login as " + binding.myEditText.getText())
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {

                            Snackbar.make(binding.textShow, "You login in as " + binding.myEditText, Snackbar.LENGTH_LONG)

                                    .show();

                        })
                        .create().show();

                // no longer visible
                super.onStop();

                Log.w(TAG, "onStop() - The application is no longer visible");

                //1) Where you are, 2) Which activity do you want next

                Intent nextPage = new Intent(RongMain.this, RongSecond.class);
                //pass some data:
                nextPage.putExtra("userName", binding.myEditText.getText().toString());
                // actually make the transition, send the table to the secondActivity
                editor.putString("LoginName", binding.myEditText.getText().toString()); //emailAddress =""
                editor.apply();
                startActivity(nextPage);

            } else {
                tv.setText("You shall not pass!");
            }


        });
        Log.w(TAG, "In onCreate() - Loading Widgets");

    }


    /**
     * This function is to check if the password is complex enough.
     *
     * @param pw The String object that we are checking
     * @return Return true if pw has UpperCase, LowerCase, Number, Special character, otherwise, false.
     */
    boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {

                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }

        }

        if (!foundUpperCase) {
            Toast.makeText(this, "they are missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "They are missing a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "They are missing a a number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "They are missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "Your password meets the requirements", Toast.LENGTH_SHORT).show();
            return true; //only get here if they're all true
        }
    }

    /**
     * Checks if a given character is a special character.
     *
     * @param c the character to be checked for special character status
     * @return true if the given character is a special character, false otherwise
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '?':
            case '*':
            case '$':
            case '^':
            case '@':
            case '!':
            case '%':
            case '&':
                return true;
            default:
                return false;
        }
    }


    @Override  // 2)   Activity is now visible
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart() - The application is now visible on screen");
    }

    @Override //  3)  Now responds to touch input
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() - The application is now responding to user input");
    }

    @Override //no longer listening to touches
    protected void onPause() {
        super.onPause();

        Log.w(TAG, "onPause()- The application no longer responds to user input");
    }


    @Override  // memory is garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.w("TAG", "onDestroy() - Any memory used by the application is freed");

    }

}
