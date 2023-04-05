package algonquin.cst2335.androidapplicationproject.XingyunApp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * The data model class that preserve data when the application is doing some
 * changes
 */
public class XingyunViewModel extends ViewModel {
    /**
     * The saved articles list
     */
    public MutableLiveData<ArrayList<XingyunArticle>> articles = new MutableLiveData<>();

    /**
     * The saved currently selected article
     */
    public MutableLiveData<XingyunArticle> selectedArticle = new MutableLiveData<>();

}
