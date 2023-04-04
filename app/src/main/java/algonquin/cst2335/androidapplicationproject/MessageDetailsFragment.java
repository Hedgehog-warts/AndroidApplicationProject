package algonquin.cst2335.androidapplicationproject;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidapplicationproject.databinding.RongweatherDetailFragmentBinding;

/**
 * The MessageDetailsFragment class extends Fragment and displays
 * the details of a selected message in a weather app.
 * It contains a RongCityInfo object representing the selected message,
 * and uses data binding to set the text in the UI.
 */
public class MessageDetailsFragment extends Fragment {
    /**
     * The RongCityInfo object representing the selected message.
     */
    RongCityInfo selected;

    /**
     * Constructs a new MessageDetailsFragment object with the specified RongCityInfo object.
     *
     * @param m The RongCityInfo object representing the selected message.
     */
    public MessageDetailsFragment(RongCityInfo m) {
        selected = m;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RongweatherDetailFragmentBinding binding = RongweatherDetailFragmentBinding.inflate(inflater);

        binding.editCity.setText(selected.getCity());
        binding.timeDetail.setText(selected.timeSent);
        binding.TemperatureDetail.setText(selected.getTemperature());
        binding.description.setText(selected.getDescription());

        return binding.getRoot();
    }

}
