package algonquin.cst2335.androidapplicationproject.rongData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidapplicationproject.RongCityInfo;

/**
 * A ViewModel class that stores the data related to the RongSecondActivity.
 * It contains MutableLiveData objects to hold the state of a checkbox, an ArrayList of RongCityInfo objects,
 * and a single RongCityInfo object representing a selected message.
 * The isChecked MutableLiveData is used to hold the state of a checkbox.
 * The messages MutableLiveData holds an ArrayList of RongCityInfo objects that represent the messages
 * received by the user.
 * The selectedMessage MutableLiveData holds a single RongCityInfo object that represents the message
 * selected by the user from the list of messages.
 */
public class RongSecondViewModel extends ViewModel {
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>();
    public MutableLiveData<ArrayList<RongCityInfo>> messages = new MutableLiveData<>();
    public MutableLiveData<RongCityInfo> selectedMessage = new MutableLiveData<>();
}
