package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class DoyoungViewModel extends ViewModel {
    public MutableLiveData<ArrayList<DoyoungThumbnail>> photos = new MutableLiveData<>();
    public MutableLiveData<DoyoungThumbnail> selectedThumbnail = new MutableLiveData<>();
}
