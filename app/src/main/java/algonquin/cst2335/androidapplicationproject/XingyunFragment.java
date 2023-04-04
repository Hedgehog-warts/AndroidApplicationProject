package algonquin.cst2335.androidapplicationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityXingyunMainBinding;

public class XingyunFragment extends Fragment {

//    ChatMessage selected;

    public XingyunFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ActivityXingyunMainBinding binding = ActivityXingyunMainBinding.inflate(inflater);

//        binding.messageText.setText(selected.message);
//        binding.timeText.setText(selected.timeSent);
//        binding.databaseText.setText("Id = " + selected.id);
//        binding.sendOrReceiveText.setText("Is Sent: " + selected.isSentButton);

        return binding.getRoot();
    }

}
