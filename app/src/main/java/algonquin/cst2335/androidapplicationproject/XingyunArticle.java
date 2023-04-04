package algonquin.cst2335.androidapplicationproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class XingyunArticle {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="headline")
    protected String headline;

    @ColumnInfo(name="url")
    protected String url;

    @ColumnInfo(name="date")
    protected String date;

    public XingyunArticle(String headline, String url, String date)
    {
        this.headline = headline;
        this.url = url;
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() { return url; }

    public String getDate() {
        return date;
    }
}
