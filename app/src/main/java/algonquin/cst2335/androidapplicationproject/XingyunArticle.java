package algonquin.cst2335.androidapplicationproject;

//@Entity
public class XingyunArticle {

//    @PrimaryKey (autoGenerate = true)
//    @ColumnInfo(name="headline")
    protected String headline;

    public XingyunArticle(String headline)
    {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }
}
