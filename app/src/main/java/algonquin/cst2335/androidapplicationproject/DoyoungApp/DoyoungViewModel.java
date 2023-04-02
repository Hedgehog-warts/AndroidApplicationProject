package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/** This is to hold thumbnails data
 * @author Doyoung Kim
 * @version 1.0
 */
public class DoyoungViewModel extends ViewModel {
    public MutableLiveData<ArrayList<DoyoungThumbnail>> photos = new MutableLiveData<>();
    public MutableLiveData<DoyoungImgDetail> selectedThumbnail = new MutableLiveData<>();
}
