package eslam.emad.bloodbank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.SharedPreferencesManger;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.ui.activities.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private static final String TAG = "Login TAG";
    View view;
    @BindView(R.id.fragment_login_phone_number_edit_text)
    EditText phoneNumberEditText;
    @BindView(R.id.fragment_login_password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.fragment_login_remember_me_checkBox)
    CheckBox rememberMeCheckBox;
    private String phoneString;
    private String passwordString;
    private boolean isChecked = false;
    ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (SharedPreferencesManger.getINSTANCE(getContext()).restoreBooleanValue("is_remembered")) {
            goToHomeActivity();
        }
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        viewModel.getOnLogin().observe(getViewLifecycleOwner(), new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                if (loginModel != null) {
                    if (loginModel.getStatus() == 1) {
                        SharedPreferencesManger.getINSTANCE(getContext()).saveBooleanValue("is_remembered", isChecked);
                        SharedPreferencesManger.getINSTANCE(getContext()).saveStringValue("api_key", loginModel.getLoginData().getApiToken());
                        //register notification token
                        goToHomeActivity();
                    }
                    Toast.makeText(getContext(), loginModel.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fragment_login_remember_me_checkBox)
    public void onFragmentLoginRememberMeCheckBoxClicked() {
        if (rememberMeCheckBox.isChecked()) {
            isChecked = true;
        }
        if (!rememberMeCheckBox.isChecked()) {
            isChecked = false;
        }
    }

    @OnClick(R.id.fragment_login_forget_password_tv)
    public void onFragmentLoginForgetPasswordTvClicked() {
        getParentFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                new ResetPasswordFragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.fragment_login_enter_btn)
    public void onFragmentLoginEnterBtnClicked() {
        phoneString = phoneNumberEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();
        checkData(phoneString, passwordString);
    }

    private void checkData(String phone, String password) {
        if (phone.length() != 11 || !phone.startsWith("01")) {
            Toast.makeText(getContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 4) {
            Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.setOnLogin(phone, password);
        }
    }

    @OnClick(R.id.fragment_login_not_registered_tv)
    public void onFragmentLoginNotRegisteredTvClicked() {
        RegisterFragment registerFragment = new RegisterFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                registerFragment).addToBackStack(null).commit();
    }
}