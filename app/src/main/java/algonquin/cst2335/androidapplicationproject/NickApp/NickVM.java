package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NickVM extends ViewModel {

    public MutableLiveData<ArrayList<String>> Pictures = new MutableLiveData<>();

}
