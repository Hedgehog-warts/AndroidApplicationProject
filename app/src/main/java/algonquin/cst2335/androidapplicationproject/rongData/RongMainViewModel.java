package algonquin.cst2335.androidapplicationproject.rongData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RongMainViewModel extends ViewModel {

    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>();
}
