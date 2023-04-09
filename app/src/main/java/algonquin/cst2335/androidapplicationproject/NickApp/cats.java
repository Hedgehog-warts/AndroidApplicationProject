package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cats_t")
public class cats {

    @PrimaryKey
    @NonNull
    private String catURL;

    public cats(){}

    public cats(String catURL){
        this.catURL = catURL;
    }

    public String getCatURL(){
        return catURL;
    }
    public void setCatURL( String catURL){
        this.catURL = catURL;

    }

}
