package algonquin.cst2335.androidapplicationproject.rongData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidapplicationproject.RongCityInfo;

/**
 * RongSecondViewModel class is a subclass of ViewModel
 * that represents the data and state for the second view in weatherStackAPP
 * It contains three MutableLiveData objects for handling boolean and city information data.
 *
 */
public class RongSecondViewModel extends ViewModel {
    /**
     * MutableLiveData isChecked object for handling boolean data representing the checked state of a checkbox.
     */
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>();

    /**
     * MutableLiveData messages object for handling a list of city information data.
     */
    public MutableLiveData<ArrayList<RongCityInfo>> messages = new MutableLiveData<>();

    /**
     * MutableLiveData selectedMessage object for handling a single city information data.
     */
    public MutableLiveData<RongCityInfo> selectedMessage = new MutableLiveData<>();
}
