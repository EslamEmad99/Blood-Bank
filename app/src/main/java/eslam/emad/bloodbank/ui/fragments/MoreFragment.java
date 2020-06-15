package eslam.emad.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.emad.bloodbank.R;

public class MoreFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new MoreScreen()).addToBackStack(null).commit();
        return view;
    }
}