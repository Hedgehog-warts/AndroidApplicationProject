package algonquin.cst2335.androidapplicationproject.rongData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidapplicationproject.RongCityInfo;

public class RongSecondViewModel extends ViewModel {
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>();
    public MutableLiveData<ArrayList<RongCityInfo>> messages = new MutableLiveData< >();
    public MutableLiveData<RongCityInfo> selectedMessage = new MutableLiveData<>();
}
