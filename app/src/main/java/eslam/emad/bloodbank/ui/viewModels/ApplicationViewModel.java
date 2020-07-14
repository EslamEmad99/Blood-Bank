package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.repository.Repository;

public class ApplicationViewModel extends ViewModel {

    private MutableLiveData<LoginModel> loginModel;

    public ApplicationViewModel() {
        loginModel = new MutableLiveData<>();
        loginModel = Repository.getINSTANCE().getLoginModel();
    }

    public void setOnLogin(String phone, String password) {
        Repository.getINSTANCE().setOnLogin(phone, password);
    }

    public MutableLiveData<LoginModel> getOnLogin() {
        return loginModel;
    }
}
