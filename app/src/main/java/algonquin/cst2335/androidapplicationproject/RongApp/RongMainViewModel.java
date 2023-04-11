package algonquin.cst2335.androidapplicationproject.RongApp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * RongMainViewModel class represents a ViewModel for the main activity.
 * It contains a LiveData variable isChecked that can be observed for changes.
 */
public class RongMainViewModel extends ViewModel {

    /**
     * isChecked is a LiveData variable that represents whether a certain condition is checked or not.
     * It can be observed for changes by other components.
     */
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>();
}
