package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/** This is to hold thumbnails data
 * @author Doyoung Kim
 * @version 1.0
 */
public class DoyoungViewModel extends ViewModel {
    /** a field to store DoyoungThumbnail objects list */
    public MutableLiveData<ArrayList<DoyoungThumbnail>> photos = new MutableLiveData<>();

    /** a field to store a DoyoungImgDetail object */
    public MutableLiveData<DoyoungImgDetail> selectedThumbnail = new MutableLiveData<>();
}
