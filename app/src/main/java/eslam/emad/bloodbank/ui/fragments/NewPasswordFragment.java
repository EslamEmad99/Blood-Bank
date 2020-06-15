package eslam.emad.bloodbank.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.newPassword.NewPasswordModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NewPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.fragment_new_password_check_code_et)
    EditText fragmentNewPasswordCheckCodeEt;
    @BindView(R.id.fragment_new_password_new_password_et)
    EditText fragmentNewPasswordNewPasswordEt;
    @BindView(R.id.fragment_new_password_new_password_again_et)
    EditText fragmentNewPasswordNewPasswordAgainEt;
    @BindView(R.id.checkboxrememberme)
    AppCompatCheckBox checkboxrememberme;
    private String password;
    private String passwordConfirmation;
    private Integer pinCode;
    private String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_password, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences pref = getContext().getSharedPreferences("sharedPreferencesOfResetPassword", MODE_PRIVATE);
        pinCode = pref.getInt("pin_code", 0);
        phone = pref.getString("phone", "");

        fragmentNewPasswordCheckCodeEt.setText(String.valueOf(pinCode));

        return view;
    }


    private void goToLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                loginFragment).addToBackStack(null).commit();
    }

    @OnClick(R.id.checkboxrememberme)
    public void onCheckboxremembermeClicked() {
        if (!checkboxrememberme.isChecked()) {
            fragmentNewPasswordNewPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            fragmentNewPasswordNewPasswordAgainEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (checkboxrememberme.isChecked()) {
            fragmentNewPasswordNewPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            fragmentNewPasswordNewPasswordAgainEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.fragment_new_password_enter_btn)
    public void onFragmentNewPasswordEnterBtnClicked() {
        password = fragmentNewPasswordNewPasswordEt.getText().toString();
        passwordConfirmation = fragmentNewPasswordNewPasswordAgainEt.getText().toString();

        if (!password.equals(passwordConfirmation)) {
            Toast.makeText(getContext(), "Not the Same password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 4) {
            Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
        } else {
            ApiClient.getINSTANCE().onNewPassword(password, passwordConfirmation, pinCode, phone).enqueue(new Callback<NewPasswordModel>() {
                @Override
                public void onResponse(Call<NewPasswordModel> call, Response<NewPasswordModel> response) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    if (response.body().getStatus() == 1) {
                        goToLoginFragment();
                    }
                }

                @Override
                public void onFailure(Call<NewPasswordModel> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}