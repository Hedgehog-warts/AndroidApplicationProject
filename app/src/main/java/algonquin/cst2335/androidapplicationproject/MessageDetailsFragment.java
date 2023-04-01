package algonquin.cst2335.androidapplicationproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import algonquin.cst2335.androidapplicationproject.databinding.RongweatherDetailFragmentBinding;

public class MessageDetailsFragment extends Fragment {

    RongCityInfo selected;


    public MessageDetailsFragment(RongCityInfo m) {
        selected = m;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RongweatherDetailFragmentBinding binding = RongweatherDetailFragmentBinding.inflate(inflater);

        binding.cityDetail.setText(selected.getCity());
        binding.timeDetail.setText(selected.timeSent);
        binding.TemperatureDetail.setText(selected.getTemperature());
        binding.databaseText.setText("Id = " + selected.id);

        return binding.getRoot();
    }


}
