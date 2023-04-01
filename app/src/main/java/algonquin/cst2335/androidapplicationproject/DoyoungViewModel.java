package algonquin.cst2335.androidapplicationproject;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class DoyoungViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Bitmap>> photos = new MutableLiveData<>();
}
