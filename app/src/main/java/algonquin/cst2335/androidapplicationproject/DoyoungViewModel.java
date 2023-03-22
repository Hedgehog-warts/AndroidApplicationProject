package algonquin.cst2335.androidapplicationproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class DoyoungViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> messages = new MutableLiveData<>();
}
