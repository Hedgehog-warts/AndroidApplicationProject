package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import algonquin.cst2335.androidapplicationproject.R;
import algonquin.cst2335.androidapplicationproject.databinding.DoyoungDetailFragmentBinding;

public class DoyoungPhotoFragment extends Fragment {

    DoyoungThumbnail selected;

    public DoyoungPhotoFragment(DoyoungThumbnail m) {
        selected = m;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.kdyToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.doyoung_toolbar, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DoyoungDetailFragmentBinding binding = DoyoungDetailFragmentBinding.inflate(inflater);

//        binding.messageText.setText(selected.message);
//        binding.timeText.setText(selected.timeSent);
//        binding.databaseText.setText("ID = " + selected.id);

        return binding.getRoot();
    }

}
