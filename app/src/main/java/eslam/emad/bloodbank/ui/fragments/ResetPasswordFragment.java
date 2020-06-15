package eslam.emad.bloodbank.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.resetpassword.ResetPasswordModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ResetPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.fragment_reset_password_phone_et)
    EditText fragmentResetPasswordPhoneEt;
    String phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_reset_password_enter_button)
    public void onViewClicked() {
        phoneNumber = fragmentResetPasswordPhoneEt.getText().toString();
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("01")) {
            Toast.makeText(getContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
        } else {
            ApiClient.getINSTANCE().onResetPassword(phoneNumber).enqueue(new Callback<ResetPasswordModel>() {
                @Override
                public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    if (response.body().getStatus() == 1) {

                        saveDataInSharedPreferences(
                                response.body().getResetPasswordData().getPinCodeForTest(),
                                phoneNumber);

                        goToNewPasswordFragment();
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                    Toast.makeText(getContext(), "ERROR connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToNewPasswordFragment() {
        NewPasswordFragment newPasswordFragment = new NewPasswordFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                newPasswordFragment).addToBackStack(null).commit();
    }

    private void saveDataInSharedPreferences(Integer pinCode, String phone) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferencesOfResetPassword", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pin_code", pinCode);
        editor.putString("phone", phone);
        editor.apply();
    }
}
