package algonquin.cst2335.androidapplicationproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityRongMainBinding;


public class RongMain extends AppCompatActivity {

    private static String TAG = "RongMain";
    //    private String username = null;
    private ActivityRongMainBinding binding;


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
                nextPage = new Intent(RongMain.this, pageClass);
                startActivity(nextPage);

                break;
            case R.id.nick:

                pageClass = NickMain.class;
                nextPage = new Intent(RongMain.this, pageClass);
                startActivity(nextPage);

                break;
            case R.id.xingyun:
                pageClass = XingyunMain.class;
                nextPage = new Intent(RongMain.this, pageClass);
                startActivity(nextPage);
                break;
            case R.id.help:

                AlertDialog.Builder builder = new AlertDialog.Builder(RongMain.this);
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

    //m2
    @Override // This starts the application , is the main function in Android
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //loads buttons /  test on screen / everything in the res folder will show on screen
        setContentView(R.layout.activity_rong_main);

        binding = ActivityRongMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // loads XML on screen;
        setSupportActionBar(binding.myToolbar);
        // emailAddress saved using SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String userName = prefs.getString("LoginName", ""); //userName =""
        binding.typeUserName.setText(userName);
        SharedPreferences.Editor editor = prefs.edit();


//        TextView tv = findViewById(R.id.textShow);

        binding.loginButton.setOnClickListener(clk -> {

            String password = binding.password.getText().toString();
            TextView username = findViewById(R.id.typeUserName);
            // looking for UpperCase, LowerCase, Number and Special character,
            if (checkPasswordComplexity(password)) {
                Snackbar.make(username, "Do you want to login in as " + username.getText() + " ?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", click -> {
                            Intent nextPage = new Intent(RongMain.this, RongSecond.class);
                            //pass some data:
                            nextPage.putExtra("userName", binding.typeUserName.getText().toString());
                            // actually make the transition, send the table to the secondActivity
                            editor.putString("LoginName", binding.typeUserName.getText().toString()); //emailAddress =""
                            editor.apply();
                            startActivity(nextPage);

                        })
                        .show();


            } else {

                Toast.makeText(this, "You shall not pass!", Toast.LENGTH_SHORT).show();
            }
        });
        Log.w(TAG, "In onCreate() - Loading Widgets");

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(RongMain.this);

        exitApp.setMessage("Do you want to leave Weather Stack APP?")
                .setTitle("Thanks!")
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
//            Toast.makeText(this, "they are missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
//            Toast.makeText(this, "They are missing a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
//            Toast.makeText(this, "They are missing a a number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
//            Toast.makeText(this, "They are missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        } else {
//            Toast.makeText(this, "Your password meets the requirements", Toast.LENGTH_SHORT).show();
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
