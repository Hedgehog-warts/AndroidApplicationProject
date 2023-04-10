package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** This is to hold thumbnails data
 * @author Doyoung Kim
 * @version 1.0
 */
public class DoyoungViewModel extends ViewModel {
    /** a field to store DoyoungThumbnail objects list */
    public MutableLiveData<ArrayList<DoyoungThumbnail>> thumbnails = new MutableLiveData<>();

    /** a field to store a DoyoungImgDetail object */
    public MutableLiveData<DoyoungImgDetail> selectedThumbnail = new MutableLiveData<>();

    /** a field to store DoyoungImgDetail objects list */
    public MutableLiveData<ArrayList<DoyoungImgDetail>> infoDetails = new MutableLiveData<>();

    /** a field to store a recyclerView object */
    public MutableLiveData<RecyclerView.Adapter> photoAdapter = new MutableLiveData<>();

    /** a field to store a current orientation */
    public MutableLiveData<Integer> orientation = new MutableLiveData<>();

    /** a field to store a DoyoungPhotoFragment */
    public MutableLiveData<DoyoungPhotoFragment> fragment = new MutableLiveData<>();
}
