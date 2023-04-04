package algonquin.cst2335.androidapplicationproject;

//@Entity
public class XingyunArticle {

//    @PrimaryKey (autoGenerate = true)
//    @ColumnInfo(name="headline")
    protected String headline;
    protected String url;
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
