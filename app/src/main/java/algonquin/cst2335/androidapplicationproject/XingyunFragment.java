package algonquin.cst2335.androidapplicationproject;

import android.content.Intent;
import android.net.Uri;
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

        // This code creates an intent with the ACTION_VIEW action and a data URI containing the URL.
        // This intent will launch the user's default web browser to display the URL when the user clicks on the urlText TextView.
        binding.urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = selected.url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

}
