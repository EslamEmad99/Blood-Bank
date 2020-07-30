package eslam.emad.bloodbank.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.SharedPreferencesManger;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.resetpassword.ResetPasswordModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ResetPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.fragment_reset_password_phone_et)
    EditText fragmentResetPasswordPhoneEt;
    String phoneNumber;
    private ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        viewModel.getOnResetPassword().observe(getViewLifecycleOwner(), new Observer<ResetPasswordModel>() {
            @Override
            public void onChanged(ResetPasswordModel resetPasswordModel) {
                if (resetPasswordModel != null) {
                    Toast.makeText(getContext(), resetPasswordModel.getMsg(), Toast.LENGTH_LONG).show();
                    if (resetPasswordModel.getStatus() == 1) {
                        SharedPreferencesManger.getINSTANCE(getContext()).saveIntegerValue("pin_code", resetPasswordModel.getResetPasswordData().getPinCodeForTest());
                        SharedPreferencesManger.getINSTANCE(getContext()).saveStringValue("phone", phoneNumber);
                        goToNewPasswordFragment();
                    }
                }
            }
        });
        return view;
    }

    @OnClick(R.id.fragment_reset_password_enter_button)
    public void onViewClicked() {
        phoneNumber = fragmentResetPasswordPhoneEt.getText().toString();
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("01")) {
            Toast.makeText(getContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.setOnResetPassword(phoneNumber);
        }
    }

    private void goToNewPasswordFragment() {
        NewPasswordFragment newPasswordFragment = new NewPasswordFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                newPasswordFragment).addToBackStack(null).commit();
    }
}