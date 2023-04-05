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

import algonquin.cst2335.androidapplicationproject.DoyoungApp.DoyoungMain;
import algonquin.cst2335.androidapplicationproject.NickApp.NickMain;
import algonquin.cst2335.androidapplicationproject.XingyunApp.XingyunMain;
import algonquin.cst2335.androidapplicationproject.databinding.ActivityRongMainBinding;

/**
 * RongMain is the main activity of the application that handles user login,
 * navigation and exiting the app.
 * This class extends AppCompatActivity,
 * which is a base class for activities that use the support library action bar features.
 */
public class RongMain extends AppCompatActivity {
    /**
     *  TAG is a String constant used for debugging purposes.
     */
    private static String TAG = "RongMain";
     /**
     * binding is an object of the ActivityRongMainBinding class
     * which is used to inflate the activity's layout and bind views to variables.
     */
    private ActivityRongMainBinding binding;

    /**
     * Override method for creating options menu.
     * Inflates the menu_weather_rong.xml file to display the options menu.
     *
     * @param menu The menu object that will hold the options menu items.
     * @return true to display the options menu, false to not display it.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_weather_rong, menu);
        return true;
    }

    /**
     * override onOptionsItemSelected method;
     * This method is called when an item in the options menu is selected.
     * It starts a new activity based on the selected item.
     *
     * @param item The selected menu item.
     * @return Returns true if the item was successfully handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //super.onOptionsItemSelected(item);
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
                builder.setMessage(getString(R.string.helpText1) + "\n" +
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
     * This method is called when the activity is first created.
     * It initializes the UI components, sets up the toolbar and sets the shared preferences.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
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

        // to check constrain of password when call setOnClickListener
        binding.loginButton.setOnClickListener(clk -> {
            if (binding.typeUserName.getText().toString().equals("") || binding.password.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.notNull), Toast.LENGTH_LONG).show();
            } else {
                String password = binding.password.getText().toString();
                TextView username = findViewById(R.id.typeUserName);
                // looking for UpperCase, LowerCase, Number and Special character,
                if (checkPasswordComplexity(password)) {
                    Snackbar.make(username, getString(R.string.login) + " " + username.getText() + " ?", Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.yes), click -> {
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

                    Toast.makeText(this, getString(R.string.notPass), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.w(TAG, "In onCreate() - Loading Widgets");

    }

    /**
     * Overrides the method that is called when the user presses the back button on the device.
     * Shows an alert dialog asking the user if they want to leave the application.
     * If the user chooses to leave, the activity is finished and the application is closed.
     * If the user chooses not to leave, the dialog is cancelled and the user remains on the current activity.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitApp = new AlertDialog.Builder(RongMain.this);

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

            return false;
        } else if (!foundLowerCase) {

            return false;
        } else if (!foundNumber) {

            return false;
        } else if (!foundSpecial) {

            return false;
        } else {

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

}
