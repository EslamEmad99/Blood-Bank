package eslam.emad.bloodbank.repository;

import androidx.lifecycle.MutableLiveData;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository INSTANCE;
    public MutableLiveData<LoginModel> loginModel;

    private Repository(){
        loginModel= new MutableLiveData<>();
    }

    public static Repository getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public void setOnLogin(String phone, String password){
        ApiClient.getINSTANCE()
                .onLogin(phone, password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        loginModel.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        LoginModel login_obj = new LoginModel();
                        login_obj.setMsg("Error connection");
                        login_obj.setStatus(-1);
                        loginModel.setValue(login_obj);
                    }
                });
    }

    public MutableLiveData<LoginModel> getLoginModel(){
        return loginModel;
    }

}
