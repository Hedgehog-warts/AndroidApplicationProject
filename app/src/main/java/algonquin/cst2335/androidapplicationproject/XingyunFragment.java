package algonquin.cst2335.androidapplicationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidapplicationproject.databinding.ActivityXingyunMainBinding;
import algonquin.cst2335.androidapplicationproject.databinding.XingyunDetailFragmentBinding;

public class XingyunFragment extends Fragment {

    XingyunArticle selected;

    public XingyunFragment(XingyunArticle selected) {
        this.selected = selected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        XingyunDetailFragmentBinding binding = XingyunDetailFragmentBinding.inflate(inflater);

        binding.headlineText.setText(selected.headline);
        binding.urlText.setText(selected.url);
        binding.dateText.setText(selected.date);
//        binding.messageText.setText(selected.message);
//        binding.timeText.setText(selected.timeSent);
//        binding.databaseText.setText("Id = " + selected.id);
//        binding.sendOrReceiveText.setText("Is Sent: " + selected.isSentButton);

        return binding.getRoot();
    }

}
