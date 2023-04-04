package algonquin.cst2335.androidapplicationproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class XingyunViewModel extends ViewModel {
    public MutableLiveData<ArrayList<XingyunArticle>> articles = new MutableLiveData<>();
    public MutableLiveData<XingyunArticle> selectedArticle = new MutableLiveData<>();

}
