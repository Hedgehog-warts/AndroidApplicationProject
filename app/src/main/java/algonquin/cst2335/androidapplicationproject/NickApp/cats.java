package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represents an entry of a cat URL in the "cats_t" table of the database
 */
@Entity(tableName = "cats_t")
public class cats {
    // The URL of the cat image
    @PrimaryKey
    @NonNull
    private String catURL;

    // Default constructor
    public cats(){}

    // Constructor with parameters
    public cats(String catURL){
        this.catURL = catURL;
    }

    // Gets the URL of the cat image
    public String getCatURL(){
        return catURL;
    }

    //Sets the URL of the cat image
    public void setCatURL( String catURL){
        this.catURL = catURL;
    }

}

