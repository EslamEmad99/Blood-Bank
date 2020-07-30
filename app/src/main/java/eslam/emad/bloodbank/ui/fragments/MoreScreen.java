package eslam.emad.bloodbank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.data.SharedPreferencesManger;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.repository.Repository;
import eslam.emad.bloodbank.ui.activities.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreScreen extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_screen, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_more_screen_profile_tv)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new ProfileFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.fragment_more_screen_contact_us_tv)
    public void onView2Clicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new ContactUsFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.fragment_more_screen_about_us_tv)
    public void onView3Clicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new AboutUsFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.fragment_more_screen_rate_us_tv)
    public void onView4Clicked() {
        Toast.makeText(getContext(), "The application isn't yet on play store", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_more_screen__settings_tv)
    public void onView5Clicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new SettingsFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.fragment_more_screen_log_out_tv)
    public void onView6Clicked() {
        SharedPreferencesManger.getINSTANCE(getContext()).saveBooleanValue("is_remembered",false);
        Repository.getINSTANCE().onloginModel.setValue(null);
        //remove notification token
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
