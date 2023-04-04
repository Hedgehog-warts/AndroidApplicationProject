package algonquin.cst2335.androidapplicationproject;

//@Entity
public class XingyunArticle {

//    @PrimaryKey (autoGenerate = true)
//    @ColumnInfo(name="headline")
    protected String headline;
    protected String url;

    public XingyunArticle(String headline, String url)
    {
        this.headline = headline;
        this.url = url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() { return url; }
}
